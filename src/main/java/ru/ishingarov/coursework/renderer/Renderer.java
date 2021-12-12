package ru.ishingarov.coursework.renderer;

import ch.qos.logback.core.CoreConstants;
import org.springframework.boot.Banner;

import javax.imageio.ImageIO;
import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.SingularMatrixException;
import javax.vecmath.Vector3d;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Renderer {

    public static final int width = 1200;
    public static final int height = 1200;

    public static BufferedImage image = null;
    public static double[][] zbuffer = null;

    ArrayList<Model> models;

    public void addModel(Model model) {
        models.add(model);
    }

    public Renderer() {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    double[][] LookAt = {
            {Math.sqrt(3.)/Math.sqrt(6.),   0,                 -Math.sqrt(3.)/Math.sqrt(6.), 0},
            {-3/Math.sqrt(54.),             6/Math.sqrt(54.),  -3/Math.sqrt(54.),            0},
            {Math.sqrt(3.)/3,               Math.sqrt(3.)/3 ,   Math.sqrt(3.)/3,             0},
            {0,                             0,                  0,                           1}
    };

    double[][] ViewPort = {
            {width/2.,    0.,        0.,    width/2.},
            {0.      ,  -height/2.,  0,     height/2.},
            {0.,          0.,        1.,    1.},
            {0.,          0.,        0.,    1.}

    };

    double[][] M =  matrix_product(ViewPort, LookAt);
//    double[][] M = ViewPort;
    private double[][] matrix_product(double[][] A, double[][] B) {
        if (A.length==0 || A[0].length != B.length)
            throw new IllegalStateException("invalid dimensions");

        double[][] matrix = new double[A.length][B[0].length];
        for (int i=0; i<A.length; i++) {
            for (int j=0; j<B[0].length; j++) {
                double sum = 0;
                for (int k=0; k<A[i].length; k++)
                    sum += A[i][k]*B[k][j];
                matrix[i][j] = sum;
            }
        }
        return matrix;
    }

    public Vector3d barycentric_coords(double x0, double y0, double x1, double y1, double x2, double y2, double x, double y) {
        Matrix3d A = new Matrix3d(x0, x1, x2, y0, y1, y2, 1., 1, 1.);
        Matrix3d B = new Matrix3d();
        B.setColumn(0, x, y, 1.);
        try {
            A.invert();
            A.mul(B);
            A.transpose();
            Vector3d res = new Vector3d();
            A.getRow(0, res);
            return res;
        } catch (SingularMatrixException e) {
            return new Vector3d(-1, 1, 1);
        }
//        return matrix_transpose(matrix_product(matrix_inverse(A), b))[0];
    }

    public void render(String filein, String fileout) throws IOException {
        // Считываем файл, получаем объект модели
//        String filename = "null";
        Model model = new Model(filein);

        // Инициализируем буффер
        zbuffer = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                zbuffer[i][j] = -1.;
            }
        }

        GouraudShader shader = new GouraudShader();
        for (int i = 0; i < model.getFaceN(); i++) {
            Vector3d[] screen = new Vector3d[3];
            for (int j = 0; j < 3; j++) {
                screen[j] = shader.vertex(model.vertex(i, j), model.normal(i, j), j, M);
            }
            drawFace(screen, shader);
        }

        ImageIO.write(image, "png", new File(fileout));
    }

    private void drawFace(Vector3d[] screen, GouraudShader shader) {
        int bbminx = width - 1;
        int bbminy = height - 1;
        int bbmaxx = 0;
        int bbmaxy = 0;
        for (int v = 0; v < 3; v++) {
            bbminx = max(0, min(bbminx, (int)screen[v].x));
            bbminy = max(0, min(bbminy, (int)screen[v].y));
            bbmaxx = min(width - 1,  max(bbmaxx, (int)screen[v].x));
            bbmaxy = min(height - 1, max(bbmaxy, (int)screen[v].y));
        }

//        Color color = new Color(255, 0, 0);
//        int color;
        for (int px = bbminx; px <= bbmaxx; px++) {
            for (int py = bbminy; py <= bbmaxy; py++) {
                Vector3d point = barycentric_coords(screen[0].x, screen[0].y, screen[1].x, screen[1].y, screen[2].x, screen[2].y, px, py);

                if (point.x < -0.01 || point.y < -0.01 || point.z < -0.01) continue;

                double z = screen[0].z * point.x + screen[1].z * point.y + screen[2].z * point.z;
                // Предположим что можно без 4й размерности.
                double w = point.x + point.y + point.z;

//                int depth = max(0, min(255, (int)(z / w + .5)));
                if (zbuffer[px][py]>z) continue; // discard the fragment if it lies behind the z-buffer
                zbuffer[px][py] = z;

                double intensity = shader.fragment(point);
                Color color = new Color((int) intensity, 0, (int) intensity);
                image.setRGB(px, py, color.getRGB());
//                double intensitySmooth = (coord[0]*intensity0 + coord[1]*intensity1 +coord[2]*intensity2);  //interpolates the colors at the 3 vertices
//                intensitySmooth = min(255, max(0,180*intensitySmooth));
//                int color = new Color((int)intensitySmooth, 0, 0).getRGB();
//                image.setRGB(px, py, color);
            }
        }
    }
}




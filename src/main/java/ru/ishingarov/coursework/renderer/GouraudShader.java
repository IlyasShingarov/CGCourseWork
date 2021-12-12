package ru.ishingarov.coursework.renderer;

import javax.vecmath.Vector3d;
import javax.vecmath.Matrix3d;
import java.awt.*;

public class GouraudShader {
    public Vector3d varyingIntensity = new Vector3d(0,0,0);
    public Vector3d lightDir = new Vector3d(0.5, 0, 1);

    public GouraudShader() {
        lightDir.normalize();
    }
    // Получает вертекс и нормаль потому что ну лень мне прокидывать сюда модель
    public Vector3d vertex(int iface, int nthvert) {
        // В чём идея
        // Берем вертекс, докидываем единичку
        // Преобразоываваем в экранные координаты -- т.е. применяем все матрицы преобразования
        // Считаем интенсивность для каждой вершины
            // В каждую координату записываем
                // max(0, normal * light_dir)
        // Получается возвращаем её
        return null;
    }

    public Vector3d vertex(Vector3d v, Vector3d n, int nthvert, double[][] M) {
//        double w = M[3][0]*n.x + M[3][1]*n.y + M[3][2]*n.z + M[3][3];
//        Vector3d newNorm = new Vector3d(
//                (M[0][0]*n.x + M[0][1]*n.y + M[0][2]*n.z + M[0][3])/w,
//                (M[1][0]*n.x + M[1][1]*n.y + M[1][2]*n.z + M[1][3])/w,
//                (M[2][0]*n.x + M[2][1]*n.y + M[2][2]*n.z + M[2][3])/w
//        );
//        System.out.println("v_n " + nthvert + "\nn " + n + "\n");
        switch (nthvert){
            case 0:
                varyingIntensity.x = Math.max(0., n.dot(lightDir));
                break;
            case 1:
                varyingIntensity.y = Math.max(0., n.dot(lightDir));
                break;
            case 2:
                varyingIntensity.z = Math.max(0., n.dot(lightDir));
                break;
        }
        double w = M[3][0]*v.x + M[3][1]*v.y + M[3][2]*v.z + M[3][3];
        Vector3d res = new Vector3d(
                (M[0][0]*v.x + M[0][1]*v.y + M[0][2]*v.z + M[0][3])/w,
                (M[1][0]*v.x + M[1][1]*v.y + M[1][2]*v.z + M[1][3])/w,
                (M[2][0]*v.x + M[2][1]*v.y + M[2][2]*v.z + M[2][3])/w
                );

        return res;
    }

    public double fragment(Vector3d bar) {
       double intensitySmooth = (
               bar.x * varyingIntensity.x +
               bar.y * varyingIntensity.y +
               bar.z * varyingIntensity.z);
        intensitySmooth = Math.min(255, Math.max(0,255*intensitySmooth));

       return intensitySmooth;
   }
}


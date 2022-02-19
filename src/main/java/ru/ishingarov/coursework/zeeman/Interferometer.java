package ru.ishingarov.coursework.zeeman;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class Interferometer {
    public static final int width = 1200;
    public static final int height = 1200;

    public static BufferedImage image = null;
    public static Graphics2D graphics = null;

    private final double focalLength = 40 * Math.pow(10, -2);
    private final double lensInterval = 0.8 * Math.pow(10, -3);

    public Interferometer() {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
    }

    public void drawCircle(double radius, Color color) {
        graphics.setColor(color);
        graphics.drawOval(
                600 - (int) radius,
                600 - (int) radius,
                2 * (int) radius,
                2 * (int) radius
        );
    }

    public double calculateRadius(double lambda, int k) {
        return focalLength * Math.sqrt(2 * (lambda * k) / (2 * lensInterval));
    }

    public void drawInterfImage(LightSource light, Double induction, int iters, int polaroid, String fileout) throws IOException {
        graphics.setColor(new Color(0));
        graphics.fillRect(0,0, width, height);
        double delta = light.ZeemanShift(induction);

        for (int k = 1; k <= iters ; k++) {
            drawCircle(calculateRadius(light.getWavelength() + delta, k) * 20000, Color.green);
            if (polaroid == 0) {
                drawCircle(calculateRadius(light.getWavelength(), k) * 20000, Color.red);
            }
            drawCircle(calculateRadius(light.getWavelength() - delta, k) * 20000, Color.green);
        }

        ImageIO.write(image, "png", new File(fileout));
    }
}

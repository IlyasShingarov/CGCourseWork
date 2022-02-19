package ru.ishingarov.coursework;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ishingarov.coursework.renderer.Model;
import ru.ishingarov.coursework.renderer.Renderer;
import ru.ishingarov.coursework.zeeman.Interferometer;
import ru.ishingarov.coursework.zeeman.LightSource;

import javax.vecmath.Matrix4d;
import java.io.IOException;

@SpringBootApplication
public class CourseWorkApplication {

    public static String IMAGE_DIR;

    public static void main(String[] args) {
        IMAGE_DIR = "/static/";
        SpringApplication.run(CourseWorkApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        Interferometer interf = new Interferometer();
//        LightSource light = new LightSource(650E-9, 0);
//        interf.drawInterfImage(light, 20, "src/main/resources/out/interf.png");



//        Renderer r = new Renderer();
////        r.addModel("src/main/resources/models/test.obj");
//        Model m = new Model("src/main/resources/models/main_model.obj");
//        m.setRotation(m.Rotate(Math.toRadians(15), 0, 0));
//        r.addModel(m);
//
//        System.out.println("RENDER START");
//        r.render("src/main/resources/out/ntt.png");
//        System.out.println("RENDER END");
//    }
}

package ru.ishingarov.coursework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ishingarov.coursework.renderer.Model;
import ru.ishingarov.coursework.renderer.Renderer;

import java.io.IOException;

@SpringBootApplication
public class CourseWorkApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CourseWorkApplication.class, args);
        Renderer head = new Renderer();
        head.render("src/main/resources/models/african_head.obj", "src/main/resources/african_head.png");
//        head.render("src/main/resources/models/smooth.obj", "src/main/resources/smooth.png");
//        head.render("src/main/resources/models/suz.obj", "src/main/resources/suz.png");

    }

}

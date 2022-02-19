package ru.ishingarov.coursework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ishingarov.coursework.renderer.Model;
import ru.ishingarov.coursework.renderer.RenderService;
import ru.ishingarov.coursework.renderer.Renderer;
import ru.ishingarov.coursework.zeeman.Interferometer;
import ru.ishingarov.coursework.zeeman.LightSource;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RenderController {

//    private final RenderService renderService;

    private final Renderer renderer;

    private final Interferometer interferometer;

//    @Value("${out.path}")
    @Value("C:/test/")
    private String outpath;

    private int id = 0;


//    private final Model mainModel = new Model("src/main/resources/models/main_model.obj");

//    @GetMapping("/meh/{alpha}")
//    public ResponseEntity<byte[]> getImage(@PathVariable("alpha") double alpha) throws IOException {
//
//        log.info("blya");
//
//        byte[] imageInByte = renderService.createImage(Math.toRadians(alpha), Math.toRadians(alpha), Math.toRadians(alpha));
//
//        Model m = new Model("src/main/resources/models/main_model.obj");
//        m.setRotation(m.Rotate(r,0,0));
//        renderer.addModel(m);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"image.png\"")
//                        .contentType(MediaType.IMAGE_PNG)
//                        .body(imageInByte);
//    }

//    @GetMapping(path = "/model")
//    public ResponseEntity<byte[]> getImage(@RequestParam Map<String, String> rotationParams) throws IOException {
//
//        log.info("blya");
//
//        byte[] imageInByte = renderService.createImage(
//                Math.toRadians(Double.parseDouble(rotationParams.get("rx"))),
//                Math.toRadians(Double.parseDouble(rotationParams.get("ry"))),
//                Math.toRadians(Double.parseDouble(rotationParams.get("rz")))
//                );
//
//        Model m = new Model("src/main/resources/models/main_model.obj");
//        m.setRotation(m.Rotate(r,0,0));
//        renderer.addModel(m);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"image.png\"")
//                        .contentType(MediaType.IMAGE_PNG)
//                        .body(imageInByte);
//    }
    @GetMapping("/Zeeman")
    public UUID getZeemanImage(@RequestParam Map<String, String> zeemanParams) {
        System.out.println("Blya");
        UUID uuid = UUID.randomUUID();
        LightSource lightSource = new LightSource(Double.parseDouble(zeemanParams.get("lambda")) * 1E-9, 0);
//        interferometer.drawInterfImage(lightSource, 10, Integer.parseInt(zeemanParams.get("polaroid")), "src/main/resources/static/out/interf_" + uuid + ".png");
        return uuid;
    }

    @GetMapping("/interferometer")
    public UUID getInterferometerImage(@RequestParam Map<String, String> zeemanParams) throws IOException {
        log.info("interferometer request handle start");
        UUID uuid = UUID.randomUUID();
        log.info(zeemanParams.toString());
        LightSource lightSource = new LightSource(Double.parseDouble(zeemanParams.get("lambda")) * 1E-9, 0);
        interferometer.drawInterfImage(
                lightSource,
                Double.parseDouble(zeemanParams.get("induction")),
                10,
                Integer.parseInt(zeemanParams.get("polaroid")),
                outpath + "interf_" + uuid + ".png");

        log.info("interferometer request handle end");

        return uuid;
    }
    private int i = 0;
    @GetMapping("/render")
    public UUID getModelImage(@RequestParam Map<String, String> renderParams) throws IOException {
        log.info("render request handle start");

        if (i < 25) {
            i++;
        } else {
            i = 0;
            FileUtils.cleanDirectory(new File(outpath));
        }

        UUID uuid = UUID.randomUUID();
        renderer.getModel(0).setRotation(Model.Rotate(
                Math.toRadians(Double.parseDouble(renderParams.get("x_angle"))),
                Math.toRadians(Double.parseDouble(renderParams.get("y_angle"))),
                Math.toRadians(Double.parseDouble(renderParams.get("z_angle"))))
        );

        renderer.render(outpath + "model_" + uuid + ".png");

        log.info("render request handle end");

        return uuid;
    }

}

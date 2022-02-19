package ru.ishingarov.coursework.renderer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class RenderService {

//    @Value("${image.width}")
//    private int width;
//
//    @Value("${image.height}")
//    private int height;
//
////    private final Renderer renderer;
//
//    private final Model mainModel = new Model("src/main/resources/models/main_model.obj");
//
//    public RenderService(Renderer renderer) throws IOException {
//        this.renderer = renderer;
//        renderer.addModel(mainModel);
//    }
//
//    public byte[] createImage(double rx, double ry, double rz) throws IOException {
//        mainModel.setRotation(mainModel.Rotate(rx, ry, rz));
//        return renderer.render();
//    }

}

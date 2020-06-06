/*
package net.wwc.shopapi.controller;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Vertex;
import net.wwc.shopapi.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


@RestController
@RequestMapping("/scanApi")

public class GoogleApiLensController {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;

    @Autowired
    private Util util;


    @RequestMapping("/getLabelDetection")
    public String getLabelDetection(@RequestParam("file") MultipartFile file) {
        String fileName = util.storeFile(file);
        String path = "D:\\eLocalShopping\\WWC\\uploads\\";
        Resource resource = resourceLoader.getResource("file:" + path + fileName);
        AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(resource, Feature.Type.LABEL_DETECTION);
        return response.getLabelAnnotationsList().toString();
    }

    @RequestMapping("/getTextDetection")
    public String getTextDetection(@RequestParam("file") MultipartFile file) {

        String fileName = util.storeFile(file);
        String path = "D:\\eLocalShopping\\WWC\\uploads\\";
        Resource resource = resourceLoader.getResource("file:" + path + fileName);
        AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
                resource, Feature.Type.DOCUMENT_TEXT_DETECTION);

        return response.getTextAnnotationsList().toString();
    }

    @RequestMapping("/getLandmarkDetection")
    public String getLandmarkDetection(@RequestParam("file") MultipartFile file) {
        String fileName = util.storeFile(file);
        String path = "D:\\eLocalShopping\\WWC\\uploads\\";
        Resource resource = resourceLoader.getResource("file:" + path + fileName);
       // Resource imageResource = this.resourceLoader.getResource("file:src/main/resources/landmark.jpeg");
        AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
                resource, Feature.Type.LANDMARK_DETECTION);

        return response.getLandmarkAnnotationsList().toString();
    }

    */
/*@RequestMapping("/getFaceDetection")
    public String getFaceDetection(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = util.storeFile(file);
        String path = "C:\\Users\\Shweta\\WWC-Latest\\uploads\\";
        Resource resource = resourceLoader.getResource("file:" + path + fileName);
       // Resource imageResource = this.resourceLoader.getResource("file:src/main/resources/faces.jpeg");
        Resource outputImageResource = this.resourceLoader.getResource("classpath:output.jpg");
        AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
                resource, Feature.Type.FACE_DETECTION);

        writeWithFaces(resource.getFile().toPath(), outputImageResource.getFile().toPath(), response.getFaceAnnotationsList());

        return response.getFaceAnnotationsList().toString();
    }*//*



    */
/**
     * Reads image {@code inputPath} and writes {@code outputPath} with {@code faces} outlined.
     *//*

    private static void writeWithFaces(Path inputPath, Path outputPath, List<FaceAnnotation> faces)
            throws IOException {
        BufferedImage img = ImageIO.read(inputPath.toFile());
        annotateWithFaces(img, faces);
        ImageIO.write(img, "jpg", outputPath.toFile());
    }

    */
/**
     * Annotates an image {@code img} with a polygon around each face in {@code faces}.
     *//*

    public static void annotateWithFaces(BufferedImage img, List<FaceAnnotation> faces) {
        for (FaceAnnotation face : faces) {
            annotateWithFace(img, face);
        }
    }

    */
/*  *//*
*/
/**
     * Annotates an image {@code img} with a polygon defined by {@code face}.
     *//*

    private static void annotateWithFace(BufferedImage img, FaceAnnotation face) {
        Graphics2D gfx = img.createGraphics();
        Polygon poly = new Polygon();
        for (Vertex vertex : face.getFdBoundingPoly().getVerticesList()) {
            poly.addPoint(vertex.getX(), vertex.getY());
        }
        gfx.setStroke(new BasicStroke(5));
        gfx.setColor(new Color(0x00ff00));
        gfx.draw(poly);
    }

}



*/

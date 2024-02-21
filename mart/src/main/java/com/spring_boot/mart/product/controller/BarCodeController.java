package com.spring_boot.mart.product.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BarCodeController {

    // private static final String BARCODE_FOLDER = "src/main/resources/barcodes/";

    // @PostMapping("/generateBarcode")
    // public ResponseEntity<String> generateBarcode(@RequestParam("content") String content,
    //         @RequestParam("format") BarcodeFormat format,
    //         @RequestParam(value = "filename", required = false) String filename) {
    //     int width = 200;
    //     int height = 100;
    //     try {
    //         BitMatrix bitMatrix = new MultiFormatWriter().encode(content, format, width, height);
    //         BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

    //         String generatedFilename;

    //         // Use the provided filename if available, otherwise generate a unique one
    //         if (filename != null && !filename.isEmpty()) {
    //             generatedFilename = filename;
    //         } else {
    //             generatedFilename = UUID.randomUUID().toString() + ".png";
    //         }

    //         // Ensure the filename has the correct extension
    //         if (!generatedFilename.toLowerCase().endsWith(".png")) {
    //             generatedFilename += ".png";
    //         }

    //         String filePath = BARCODE_FOLDER + generatedFilename;

    //         // Save the image to the specified folder
    //         File outputFile = new File(filePath);
    //         ImageIO.write(image, "png", outputFile);

    //         return ResponseEntity.ok().body(generatedFilename); // Return the filename to the client
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.badRequest().body(null);
    //     }
    // }

    @PostMapping("/scanBarcode")
    public ResponseEntity<String> scanBarcode(@RequestParam("image") MultipartFile imageFile) {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageFile.getBytes()));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);

            return ResponseEntity.ok().body(result.getText());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}

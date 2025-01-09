package com.project.file_compression.service;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class PDFCompressor {

    // Method to compress the PDF
    public byte[] compressPDF(File inputFile) throws IOException {
        PDDocument document = PDDocument.load(inputFile);
        ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();

        // Optimize images in the PDF
        for (PDPage page : document.getPages()) {
            PDResources resources = page.getResources();
            for (COSName xObjectName : resources.getXObjectNames()) {
                PDXObject xObject = resources.getXObject(xObjectName);
                if (xObject instanceof PDImageXObject) {
                    PDImageXObject image = (PDImageXObject) xObject;

                    // Downscale image resolution
                    BufferedImage bufferedImage = image.getImage();
                    BufferedImage scaledImage = new BufferedImage(
                            bufferedImage.getWidth() / 2,
                            bufferedImage.getHeight() / 2,
                            BufferedImage.TYPE_INT_RGB
                    );

                    Graphics2D g2d = scaledImage.createGraphics();
                    g2d.drawImage(bufferedImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);
                    g2d.dispose();

                    // Replace the original image with the compressed image
                    PDImageXObject compressedImage = LosslessFactory.createFromImage(document, scaledImage);
                    resources.put(xObjectName, compressedImage);
                }
            }
        }

        // Strip unnecessary elements like metadata
//        document.getDocumentCatalog().getMetadata().setFilter(null);
//        document.setAllSecurityToBeRemoved(true);

        document.save(compressedStream);
        document.close();

        return compressedStream.toByteArray();
    }

}

package com.ge.healthcare.pixelate;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Application {

    private static final int REDUCTION = 5;

    public static void main(String args[]) throws IOException {
        File file = new File(Application.class.getClassLoader().getResource("input.jpg").getFile());
        BufferedImage sourceImage = ImageIO.read(file);
        int sourceWidth = sourceImage.getWidth();
        int sourceHeight = sourceImage.getHeight();

        BufferedImage pixelImage = new BufferedImage(sourceWidth / REDUCTION, sourceHeight / REDUCTION, TYPE_INT_RGB);
        for (int x = 0; x < pixelImage.getWidth(); x++) {
            for (int y = 0; y < pixelImage.getHeight(); y++) {

                int convertedColor = 0;

                for (int xSource = x * REDUCTION; xSource < (x + 1) * REDUCTION && xSource < sourceWidth; xSource++) {
                    for (int ySource = y * REDUCTION; ySource < (y + 1) * REDUCTION
                            && ySource < sourceHeight; ySource++) {
                        int color = sourceImage.getRGB(xSource, ySource);
                        int red = (color & 0x00ff0000) >> 16;
                        int green = (color & 0x0000ff00) >> 8;
                        int blue = color & 0x000000ff;
                        convertedColor = red << 16 | green << 8 | blue;
                    }
                }

                pixelImage.setRGB(x, y, convertedColor);
            }
        }

        ImageIO.write(pixelImage, "jpg", new File("output.jpg"));
    }
}

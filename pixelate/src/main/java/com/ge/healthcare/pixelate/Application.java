package com.ge.healthcare.pixelate;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Application {

  private static int REDUCTION = 5;

  public static void main(String args[]) throws IOException {
    BufferedImage sourceImage = readSourceImage(args);
    if (args.length > 1) {
      try {
        REDUCTION = Integer.valueOf(args[1]);
      } catch (NumberFormatException e) {
        //ignored
      }
    }

    BufferedImage targetImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), TYPE_INT_RGB);
    for (int xTarget = 0; xTarget < targetImage.getWidth(); xTarget++) {
      for (int yTarget = 0; yTarget < targetImage.getHeight(); yTarget++) {

        targetImage.setRGB(xTarget, yTarget, calculateColor(sourceImage, xTarget, yTarget));
      }
    }

    ImageIO.write(targetImage, "jpg", new File("output.jpg"));
  }

  private static BufferedImage readSourceImage(String[] args) throws IOException {
    BufferedImage sourceImage;
    if (args.length > 0 && args[0].length() > 0) {
      sourceImage = ImageIO.read(new File(args[0]));
    } else {
      InputStream in = Application.class.getResourceAsStream("/input.jpg");
      sourceImage = ImageIO.read(in);
    }
    return sourceImage;
  }

  private static int calculateColor(final BufferedImage sourceImage, int xTarget, int yTarget) {
    int convertedColor = 0;
    xTarget /= REDUCTION;
    yTarget /= REDUCTION;

    int sourceWidth = sourceImage.getWidth();
    int sourceHeight = sourceImage.getHeight();
    for (int xSource = xTarget * REDUCTION; xSource < (xTarget + 1) * REDUCTION && xSource < sourceWidth; xSource++) {
      for (int ySource = yTarget * REDUCTION; ySource < (yTarget + 1) * REDUCTION && ySource < sourceHeight;
          ySource++) {
        int color = sourceImage.getRGB(xSource, ySource);
        int red = (color & 0x00ff0000) >> 16;
        int green = (color & 0x0000ff00) >> 8;
        int blue = color & 0x000000ff;
        convertedColor = red << 16 | green << 8 | blue;
      }
    }
    return convertedColor;
  }
}

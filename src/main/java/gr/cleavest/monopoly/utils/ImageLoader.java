package gr.cleavest.monopoly.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Cleavest on 2/3/2025
 */
public class ImageLoader {

    public static BufferedImage loadImage(String path) {

        BufferedImage image = null;
        try {
            InputStream is = ImageLoader.class.getClassLoader().getResourceAsStream(path);
            if (is != null) {
                return ImageIO.read(is);
            } else {
                System.out.println("Δεν βρέθηκε η εικόνα στο path " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static BufferedImage rotate(BufferedImage img, int angle)
    {

        // Getting Dimensions of image
        int width = img.getWidth();
        int height = img.getHeight();

        // Creating a new buffered image
        BufferedImage newImage = new BufferedImage(
                img.getWidth(), img.getHeight(), img.getType());

        // creating Graphics in buffered image
        Graphics2D g2 = newImage.createGraphics();

        // Rotating image by degrees using toradians()
        // method
        // and setting new dimension t it
        g2.rotate(Math.toRadians(angle), width / 2,
                height / 2);
        g2.drawImage(img, null, 0, 0);

        // Return rotated buffer image
        return newImage;
    }
}

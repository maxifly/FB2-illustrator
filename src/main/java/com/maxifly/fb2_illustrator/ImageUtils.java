package com.maxifly.fb2_illustrator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Maximus on 08.02.2017.
 */
public class ImageUtils {

    /**
     * Загрузка изображения из файла
     *
     * @param file
     * @return
     * @throws MyException
     */
    public static Image loadImage(File file) throws MyException {
        try {
            return ImageIO.read(file);
        } catch (IOException ex) {
            throw new MyException("Error when load image " + file.getAbsolutePath(), ex);
        }
    }

    public static void writeImage(RenderedImage image, File file) throws MyException {
        try {
            ImageIO.write(image, "jpg", file);
        } catch (IOException ex) {
            throw new MyException("Error when write image to file: " + file.getAbsolutePath(), ex);
        }
    }

    /**
     * Масштабирование изображения
     *
     * @param originalImage
     * @param scaledWidth
     * @param scaledHeight
     * @param preserveAlpha
     * @return
     */
    public static RenderedImage createResizedCopy(Image originalImage,
                                          int scaledWidth, int scaledHeight,
                                          boolean preserveAlpha) {
        int origH = originalImage.getHeight(null);
        int origW = originalImage.getWidth(null);

        if (origH <= scaledHeight && origW <= scaledWidth) {
            System.out.println("Not need resize");
            return null;
        }

        // Считаем, насколько надо уменьшить
        float hProportion = (float)origH / (float)scaledHeight;
        float wProportion = (float)origW / (float)scaledWidth;

        float proportion = (hProportion > wProportion) ? hProportion : wProportion;

        int newWidth = (int) (origW / proportion) + 1;
        int newHeight = (int) (origH / proportion) + 1;

        newWidth = (newWidth > scaledWidth) ? newWidth - 1 : newWidth;
        newHeight = (newHeight > scaledHeight) ? newHeight - 1 : newHeight;


        System.out.println("resizing... " + origW + " x " + origH + " to " + newWidth + " x " + newHeight);

        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(newWidth, newHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return scaledBI;
    }



}

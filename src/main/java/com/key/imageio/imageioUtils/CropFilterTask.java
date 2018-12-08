package com.key.imageio.imageioUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;

/**
 * 切割图像Task
 *
 * @author tujia
 */
public class CropFilterTask implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(CropFilterTask.class);
    private final static String FILE_FORMAT = "JPEG";

    // 切割图像宽度
    private int width;
    // 切割图像长度
    private int height;
    // 坐标x,左边距
    private int x;
    // 坐标y,上边距
    private int y;
    // 原图像对象
    private Image image;
    // 文件保存路径
    private String resultFileName;

    public CropFilterTask(Image image, int width, int height, int x, int y, String destFileName) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.image = image;
        this.resultFileName = destFileName;
    }

    @Override
    public void run() {
        try {
            ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
            Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制切割后的图
            g.drawImage(img, 0, 0, null);
            g.dispose();
            ImageIO.write(tag, FILE_FORMAT, new File(resultFileName));
        } catch (Exception e) {
            logger.error("切割图片异常:" + x + y, e);
        }
    }

}

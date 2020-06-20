package com.key.imageio.imageioUtils;

import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.key.imageio.imageioUtils.Constants.FILE_FORMAT;
import static com.key.imageio.imageioUtils.Constants.ImageFileType.IMAGE_TYPE_BMP;
import static com.key.imageio.imageioUtils.Constants.ImageFileType.IMAGE_TYPE_GIF;
import static com.key.imageio.imageioUtils.Constants.ImageFileType.IMAGE_TYPE_JPG;

public class ImageUtils {

    public static String FILE_SOURCE_DIR = "C:\\Users\\tujia\\Desktop\\tmp\\testpic\\back\\";
    public static String FILE_SOURCE_DIR2 = "C:\\Users\\tujia\\Desktop\\tmp\\testpic\\imageio\\";
    public static String FILE_DEST_DIR = "C:\\Users\\tujia\\Desktop\\tmp\\testpic\\imageio_dest\\";
    public static String FILE_DEST_DIR2 = "C:\\Users\\tujia\\Desktop\\tmp\\testpic\\imageio_dest\\crop\\";

    /**
     * 注册tif的imageio类(web项目中必须，测试工具类中非必须)
     */
    static {
        IIORegistry registry = IIORegistry.getDefaultInstance();
        registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriterSpi());
        registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi());
    }

    public static void main(String[] args) throws IOException {
        String fileName = FILE_SOURCE_DIR + "b1.jpg";
        String fileName2 = FILE_SOURCE_DIR + "test1.jpeg";
        // 1-缩放图像：
        // 方法一：按比例缩放
//        ImageUtils.scale(fileName, FILE_DEST_DIR + "b1_scale.jpg", 2, true);
        // 方法二：按高度和宽度比例缩放
//        ImageUtils.scale2(fileName, FILE_DEST_DIR + "b1_scale2.jpg", 299, 500, false);
        // 方法三：不按照比例，指定大小进行缩放
//        ImageUtils.scale3(fileName, FILE_DEST_DIR + "b1_scale3.jpg", 400, 600, false);
        // 颜色填充
//        ImageUtils.placeHolder(fileName2, FILE_DEST_DIR + "test1_placeholder_red.jpg", 0, 529, 500, 5, Color.RED);
        // 颜色填充，多颜色填充
//        BufferedImage bi = ImageUtils.placeHolder(fileName, 0, 50, 500, 5, Color.RED);
//        bi = ImageUtils.placeHolder(bi, 0, 150, bi.getWidth(), 5, Color.GREEN);
//        bi = ImageUtils.placeHolder(bi, 0, 250, bi.getWidth(), 5, Color.BLUE);
//        ImageIO.write(bi, FILE_FORMAT, new File(FILE_DEST_DIR + "b1_placeholder_multi.jpg"));
//
        // 2-切割图像：
        // 方法一：按指定起点坐标和宽高切割
//        ImageUtils.cut(fileName, FILE_DEST_DIR + "b1_cut.jpg", 0, 0, 400, 200);
        // 方法二：指定切片的行数和列数
        long startTime = System.currentTimeMillis();
//        ImageUtils.cutByNums(fileName, FILE_DEST_DIR, 2, 2);
        // 方法三：指定切片的宽度和高度
//        ImageUtils.cutBySize(fileName, FILE_DEST_DIR2, 267, 150);

        // 3-图像类型转换：
        // 常用格式转换
//        ImageUtils.convert(fileName, IMAGE_TYPE_GIF, FILE_DEST_DIR + "b1_convert1.gif");
//        ImageUtils.convert(FILE_SOURCE_DIR +"1.tif", IMAGE_TYPE_BMP, FILE_DEST_DIR + "test_1_convert.bmp");
//
//        // 4-彩色转黑白,获取灰度图像
//        ImageUtils.gray(FILE_SOURCE_DIR + "test1.jpeg", FILE_DEST_DIR + "test1_gray.jpg");

//        // 5-给图片添加文字水印：
//        // 方法一：
        ImageUtils.waterMarkText("www.keytujia.top", fileName, FILE_DEST_DIR + "b1_shuiyin.jpeg", "宋体", Font.BOLD, Color.white, 30, Constants.Position.BOTTOM_RIGHT, 0.6f);
//        // 方法二：
//        ImageUtils.waterMarkText("www.keytujia.top", fileName2, FILE_DEST_DIR + "test1_shuiyin_2.jpeg", "宋体", 36, Color.white, 30, 200, 250, 0.5f);
//
//        // 6-给图片添加图片水印：
        ImageUtils.waterMarkImage(FILE_SOURCE_DIR + "shuiyin2.png", fileName2, FILE_DEST_DIR + "test1_watermark.jpg", Constants.Position.CENTER_LEFT, 0.5f);
        long endTime = System.currentTimeMillis();
        System.out.println("执行时间：" + (endTime - startTime) + "ms");
    }

    /**
     * 填充颜色
     *
     * @param srcImageFileName 源图像文件地址
     * @param result           图像保存地址
     * @param x                上边距
     * @param y                左边距
     * @param width            填充宽度
     * @param height           填充长度
     * @param color            填充颜色
     */
    public static void placeHolder(String srcImageFileName, String result, int x, int y, int width, int height, Color color) {
        try {
            BufferedImage bi = ImageIO.read(new File(srcImageFileName));
            Graphics2D g = bi.createGraphics();
            if (color == null) {
                color = Color.white;
            }
            g.setColor(color);
            //填充范围，x,y表示距离顶部和左边位移，width,height表示范围，四个值定义了一个矩形范围
            g.fillRect(x, y, width, height);
            g.dispose();
            ImageIO.write(bi, FILE_FORMAT, new File(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 填充颜色
     *
     * @param srcImageFileName 源图像文件地址
     * @param x                上边距
     * @param y                左边距
     * @param width            填充宽度
     * @param height           填充长度
     * @param color            填充颜色，参考Color
     * @return BufferedImage
     */
    public static BufferedImage placeHolder(String srcImageFileName, int x, int y, int width, int height, Color color) {
        try {
            BufferedImage bi = ImageIO.read(new File(srcImageFileName));
            Graphics2D g = bi.createGraphics();
            if (color == null) {
                color = Color.white;
            }
            g.setColor(color);
            //填充范围，x,y表示距离顶部和左边位移，width,height表示范围，四个值定义了一个矩形范围
            g.fillRect(x, y, width, height);
            g.dispose();
            return bi;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 填充颜色
     *
     * @param bi     源图像对象
     * @param x      上边距
     * @param y      左边距
     * @param width  填充宽度
     * @param height 填充长度
     * @param color  填充颜色
     * @return BufferedImage
     */
    public static BufferedImage placeHolder(BufferedImage bi, int x, int y, int width, int height, Color color) {
        try {
            Graphics2D g = bi.createGraphics();
            if (color == null) {
                color = Color.white;
            }
            g.setColor(color);
            //填充范围，x,y表示距离顶部和左边位移，width,height表示范围，四个值定义了一个矩形范围
            g.fillRect(x, y, width, height);
            g.dispose();
            return bi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缩放图像（按比例缩放）
     *
     * @param srcImageFile 源图像文件地址
     * @param result       缩放后的图像地址
     * @param scale        缩放比例
     * @param flag         缩放选择:true 放大; false 缩小;
     */
    public final static void scale(String srcImageFile, String result, int scale, boolean flag) {
        try {
            // 读入文件
            BufferedImage src = ImageIO.read(new File(srcImageFile));
            // 得到源图长和宽
            int width = src.getWidth();
            int height = src.getHeight();
            if (flag) {
                // 放大
                width = width * scale;
                height = height * scale;
            } else {
                // 缩小
                width = width / scale;
                height = height / scale;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            // 输出到文件流
            ImageIO.write(tag, FILE_FORMAT, new File(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缩放图像（按高度和宽度缩放）
     *
     * @param srcImageFile 源图像文件地址
     * @param result       缩放后的图像地址
     * @param height       缩放后的高度
     * @param width        缩放后的宽度
     * @param placeholder  比例不对时是否需要补白：true为补白; false为不补白;
     */
    public final static void scale2(String srcImageFile, String result, int height, int width, boolean placeholder) {
        try {
            // 缩放比例
            double ratio;
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            System.out.println(bi.getWidth());
            System.out.println(bi.getHeight());
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (placeholder) {
                //补白
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                //填充范围，x,y表示距离顶部和左边位移，width,height表示范围，四个值定义了一个矩形范围
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, FILE_FORMAT, new File(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缩放图像（按高度和宽度缩放）
     *
     * @param srcImageFile 源图像文件地址
     * @param result       缩放后的图像地址
     * @param height       缩放后的高度
     * @param width        缩放后的宽度
     * @param placeholder  比例不对时是否需要补白：true为补白; false为不补白;
     */
    public final static void scale3(String srcImageFile, String result, int height, int width, boolean placeholder) {
        try {
            // 缩放比例
            double ratio_height = 1;
            double ratio_width = 1;
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            // 计算比例
            ratio_height = (new Integer(height)).doubleValue() / bi.getHeight();
            ratio_width = (new Integer(width)).doubleValue() / bi.getWidth();
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio_width, ratio_height), null);
            Image itemp = op.filter(bi, null);
            if (placeholder) {
                //补白
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, FILE_FORMAT, new File(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像切割(按指定起点坐标和宽高切割)
     *
     * @param srcImageFile 源图像地址
     * @param result       切片后的图像地址
     * @param x            目标切片起点坐标X
     * @param y            目标切片起点坐标Y
     * @param width        目标切片宽度
     * @param height       目标切片高度
     */
    public final static void cut(String srcImageFile, String result, int x, int y, int width, int height) {
        try {
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            // 源图宽度
            int srcWidth = bi.getWidth();
            // 源图高度
            int srcHeight = bi.getHeight();
            if (srcWidth > 0 && srcHeight > 0) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                // 绘制切割后的图
                g.drawImage(img, 0, 0, width, height, null);
                g.dispose();
                ImageIO.write(tag, FILE_FORMAT, new File(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像切割（指定切片的行数和列数）
     *
     * @param srcImageFile 源图像地址
     * @param descDir      切片目标文件夹
     * @param rows         目标切片行数
     * @param cols         目标切片列数
     */
    public final static void cutByNums(String srcImageFile, String descDir, int rows, int cols) {
        try {
            if (rows <= 0 || rows > Constants.MAX_ROWS)
                rows = Constants.DEFAULT_ROWS;
            if (cols <= 0 || cols > Constants.MAX_COLS)
                cols = Constants.DEFAULT_COLS;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            // 源图宽度和高度
            int srcWidth = bi.getWidth();
            int srcHeight = bi.getHeight();
            if (srcWidth > 0 && srcHeight > 0) {
                Image img;
                ImageFilter cropFilter;
                // 每张切片的宽度和高度
                int destWidth;
                int destHeight;
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 计算切片的宽度和高度
                if (srcWidth % cols == 0) {
                    destWidth = srcWidth / cols;
                } else {
                    destWidth = (int) Math.floor(srcWidth / cols) + 1;
                }
                if (srcHeight % rows == 0) {
                    destHeight = srcHeight / rows;
                } else {
                    destHeight = (int) Math.floor(srcHeight / rows) + 1;
                }
                // 循环建立切片
//                for (int i = 0; i < rows; i++) {
//                    for (int j = 0; j < cols; j++) {
//                        // 四个参数分别为图像起点坐标和宽高
//                        // 即: CropImageFilter(int x,int y,int width,int height)
//                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
//                        img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
//                        BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
//                        Graphics g = tag.getGraphics();
//                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
//                        g.dispose();
//                        // 输出为文件
//                        ImageIO.write(tag, FILE_FORMAT, new File(descDir + "_r" + i + "_c" + j + ".jpg"));
//                    }
//                }
                // 多线程实现切割
                ExecutorService cropExecutorService = Executors.newFixedThreadPool(rows * cols);
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        CropFilterTask cropFilterTask = new CropFilterTask(image, destWidth, destHeight, j * destWidth, i * destHeight, descDir + "_r" + i + "_c" + j + ".jpg");
                        cropExecutorService.submit(cropFilterTask);
                    }
                }
                cropExecutorService.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像切割（指定切片的宽度和高度）
     *
     * @param srcImageFile 源图像地址
     * @param descDir      切片目标文件夹
     * @param destWidth    目标切片宽度。默认200
     * @param destHeight   目标切片高度。默认150
     */
    public final static void cutBySize(String srcImageFile, String descDir, int destWidth, int destHeight) {
        try {
            if (destWidth <= 0)
                destWidth = Constants.DEFAULT_WIDTH;
            if (destHeight <= 0)
                destHeight = Constants.DEFAULT_HEIGHT;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            // 源图宽度和高度
            int srcWidth = bi.getWidth();
            int srcHeight = bi.getHeight();
            if (srcWidth > destWidth && srcHeight > destHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 切片横向数量
                int cols = 0;
                // 切片纵向数量
                int rows = 0;
                // 计算切片的横向和纵向数量
                if (srcWidth % destWidth == 0) {
                    cols = srcWidth / destWidth;
                } else {
                    cols = (int) Math.floor(srcWidth / destWidth) + 1;
                }
                if (srcHeight % destHeight == 0) {
                    rows = srcHeight / destHeight;
                } else {
                    rows = (int) Math.floor(srcHeight / destHeight) + 1;
                }
                ExecutorService cropExecutorService = Executors.newFixedThreadPool(rows * cols);
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        CropFilterTask cropFilterTask = new CropFilterTask(image, destWidth, destHeight, j * destWidth, i * destHeight, descDir + "_r" + i + "_c" + j + ".jpg");
                        cropExecutorService.submit(cropFilterTask);
                    }
                }
                cropExecutorService.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像类型转换，支持：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     *
     * @param srcImageFile  源图像地址
     * @param formatName    包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param destImageFile 目标图像地址
     */
    public final static void convert(String srcImageFile, String formatName, String destImageFile) {
        try {
            File f = new File(srcImageFile);
            f.canRead();
            f.canWrite();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, formatName, new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 彩色转为黑白
     *
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     */
    public final static void gray(String srcImageFile, String destImageFile) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile));
//            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, FILE_FORMAT, new File(destImageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加文字水印，指定位置
     *
     * @param pressText     水印文字
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName      水印的字体名称
     * @param fontStyle     水印的字体样式
     * @param color         水印的字体颜色
     * @param fontSize      水印的字体大小
     * @param position      水印位置：左上~ 右下
     * @param alpha         透明度：[0.0, 1.0] （包含边界值）
     */
    public final static void waterMarkText(String pressText, String srcImageFile, String destImageFile, String fontName, int fontStyle, Color color, int fontSize, String position, float alpha) {
        try {
            Font font = new Font(fontName, fontStyle, fontSize);
            waterMarkText(pressText, srcImageFile, destImageFile, font, color, position, alpha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加文字水印，指定位置
     *
     * @param pressText     水印文字
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param font          水印的字体对象
     * @param color         水印的字体颜色
     * @param position      水印位置：左上~ 右下
     * @param alpha         透明度：[0.0, 1.0] （包含边界值）
     */
    public final static void waterMarkText(String pressText, String srcImageFile, String destImageFile, Font font, Color color, String position, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            // 获取水印文字长度和宽度的像素值
            FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
            int textWidth = fontMetrics.stringWidth(pressText);
            int textHeight = fontMetrics.getAscent();

            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(font);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            int[] result = judgeTextPosition(position, textWidth, textHeight, image);
            // 绘制水印
            if (result != null && result.length >= 2) {
                g.drawString(pressText, result[0], result[1]);
            }
            g.dispose();
            ImageIO.write(image, FILE_FORMAT, new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加文字水印，指定具体坐标
     *
     * @param pressText     水印文字
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName      字体名称
     * @param fontStyle     字体样式
     * @param color         字体颜色
     * @param fontSize      字体大小
     * @param x             修正值
     * @param y             修正值
     * @param alpha         透明度：[0.0, 1.0] （包含边界值）
     */
    public final static void waterMarkText(String pressText, String srcImageFile, String destImageFile, String fontName, int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write(image, FILE_FORMAT, new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加图片水印
     *
     * @param markImageFile 水印图片路径
     * @param srcImageFile  源图像路径
     * @param destImageFile 目标图像路径
     * @param position      水印位置
     * @param alpha         透明度：[0.0, 1.0] （包含边界值）
     */
    public final static void waterMarkImage(String markImageFile, String srcImageFile, String destImageFile, String position, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            // 水印文件
            Image markImage = ImageIO.read(new File(markImageFile));
            int markWidth = markImage.getWidth(null);
            int markHeight = markImage.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            int[] result = judgeImagePosition(position, width, height, markWidth, markHeight);
            if (result != null && result.length >= 2) {
                g.drawImage(markImage, result[0], result[1], markWidth, markHeight, null);
            }
            g.dispose();
            ImageIO.write(image, FILE_FORMAT, new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算水印位置
     * 默认距离边缘10pi
     *
     * @param graphics2D 2D图像对象
     * @param text       水印文字
     * @param position   绘制水印位置
     * @param textWidth  文字像素宽度
     * @param textHeight 文字像素高度
     * @param image      原图对象
     * @return
     */
    public final static Graphics2D judgeTextPosition(Graphics2D graphics2D, String text, String position, int textWidth, int textHeight, Image image) {
        if (graphics2D == null) {
            return null;
        }
        if (image == null) {
            return null;
        }
        int[] result = judgeTextPosition(position, textWidth, textHeight, image);
        if (result == null || result.length < 2) {
            return null;
        }
        graphics2D.drawString(text, result[0], result[1]);
        return graphics2D;
    }

    public static int[] judgeTextPosition(String position, int markWidth, int markHeight, Image image) {
        if (image == null) {
            return null;
        }
        // 原图像宽度和高度
        int srcWidth = image.getWidth(null);
        int srcHeight = image.getHeight(null);
        return judgeTextPosition(position, srcWidth, srcHeight, markWidth, markHeight);
    }

    /**
     * 计算水印文字添加位置
     *
     * @param position   位置
     * @param srcWidth   原图宽度
     * @param srcHeight  原图高度
     * @param markWidth  水印文字像素宽度
     * @param markHeight 水印文字像素高度
     * @return
     */
    public static int[] judgeTextPosition(String position, int srcWidth, int srcHeight, int markWidth, int markHeight) {
        int[] result = new int[2];
        // 坐标
        int x = Constants.DEFAULT_PIXEL;
        int y = Constants.DEFAULT_HIEGHT_PIXEL + markHeight;

        // 依据位置计算坐标 x 和 y
        switch (position) {
            case (Constants.Position.TOP_LEFT):
                x = Constants.DEFAULT_PIXEL;
                y = Constants.DEFAULT_HIEGHT_PIXEL + markHeight;
                break;
            case (Constants.Position.TOP_CENTER):
                x = (srcWidth - markWidth) / 2;
                break;
            case (Constants.Position.TOP_RIGHT):
                x = (srcWidth - markWidth - Constants.DEFAULT_PIXEL);
                break;
            case (Constants.Position.CENTER_LEFT):
                y = srcHeight / 2;
                break;
            case (Constants.Position.CENTER):
                x = (srcWidth - markWidth) / 2;
                y = srcHeight / 2;
                break;
            case (Constants.Position.CENTER_RIGHT):
                x = (srcWidth - markWidth - Constants.DEFAULT_PIXEL);
                y = srcHeight / 2;
                break;
            case (Constants.Position.BOTTOM_LEFT):
                y = (srcHeight - Constants.DEFAULT_PIXEL - Constants.DEFAULT_HIEGHT_PIXEL);
                break;
            case (Constants.Position.BOTTOM_CENTER):
                x = (srcWidth - markWidth) / 2;
                y = (srcHeight - Constants.DEFAULT_PIXEL - Constants.DEFAULT_HIEGHT_PIXEL);
                break;
            case (Constants.Position.BOTTOM_RIGHT):
                x = (srcWidth - markWidth - Constants.DEFAULT_PIXEL);
                y = (srcHeight - Constants.DEFAULT_PIXEL - Constants.DEFAULT_HIEGHT_PIXEL);
                break;
            default:
                x = Constants.DEFAULT_PIXEL;
                y = markHeight + Constants.DEFAULT_HIEGHT_PIXEL;
                break;
        }

        result[0] = x;
        result[1] = y;
        return result;
    }

    /**
     * 计算水印图片添加位置
     *
     * @param position   位置
     * @param srcWidth   原图宽度
     * @param srcHeight  原图高度
     * @param markWidth  水印文字像素宽度
     * @param markHeight 水印文字像素高度
     * @return
     */
    public static int[] judgeImagePosition(String position, int srcWidth, int srcHeight, int markWidth, int markHeight) {
        int[] result = new int[2];
        // 坐标
        int x = Constants.DEFAULT_PIXEL;
        int y = Constants.DEFAULT_HIEGHT_PIXEL;

        // 依据位置计算坐标 x 和 y
        switch (position) {
            case (Constants.Position.TOP_LEFT):
                x = Constants.DEFAULT_PIXEL;
                y = Constants.DEFAULT_HIEGHT_PIXEL;
                break;
            case (Constants.Position.TOP_CENTER):
                x = (srcWidth - markWidth) / 2;
                break;
            case (Constants.Position.TOP_RIGHT):
                x = (srcWidth - markWidth - Constants.DEFAULT_PIXEL);
                break;
            case (Constants.Position.CENTER_LEFT):
                y = (srcHeight - markHeight) / 2;
                break;
            case (Constants.Position.CENTER):
                x = (srcWidth - markWidth) / 2;
                y = (srcHeight - markHeight) / 2;
                break;
            case (Constants.Position.CENTER_RIGHT):
                x = (srcWidth - markWidth - Constants.DEFAULT_PIXEL);
                y = (srcHeight - markHeight) / 2;
                break;
            case (Constants.Position.BOTTOM_LEFT):
                y = (srcHeight - markHeight - Constants.DEFAULT_PIXEL);
                break;
            case (Constants.Position.BOTTOM_CENTER):
                x = (srcWidth - markWidth) / 2;
                y = (srcHeight - markHeight - Constants.DEFAULT_PIXEL);
                break;
            case (Constants.Position.BOTTOM_RIGHT):
                x = (srcWidth - markWidth - Constants.DEFAULT_PIXEL);
                y = (srcHeight - markHeight - Constants.DEFAULT_PIXEL);
                break;
            default:
                x = Constants.DEFAULT_PIXEL;
                y = markHeight + Constants.DEFAULT_HIEGHT_PIXEL;
                break;
        }

        result[0] = x;
        result[1] = y;
        return result;
    }


    /**
     * 计算text的长度（一个中文算两个字符）
     *
     * @param text
     * @return
     */
    public final static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }
}


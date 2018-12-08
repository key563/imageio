package com.key.imageio.imageioUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 图片格式判断和转换工具类
 *
 * @author tujia
 */
public class ImageTransUtils {
    public static Logger logger = LoggerFactory.getLogger(ImageTransUtils.class);

    /**
     * 注册tif的imageio类(web项目中必须，测试工具类中非必须)
     */
    static {
        IIORegistry registry = IIORegistry.getDefaultInstance();
        registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriterSpi());
        registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi());
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param filePath 文件路径
     * @return 文件头信息
     */
    public static String getFileType(String filePath) {
        String headerType = getFileHeader(filePath);
        if (Constants.ImageFileHeader.JPG_OR_JPEG.equals(headerType)) {
            return Constants.ImageFileType.IMAGE_TYPE_JPG;
        } else if (Constants.ImageFileHeader.PNG.equals(headerType)) {
            return Constants.ImageFileType.IMAGE_TYPE_PNG;
        } else if (Constants.ImageFileHeader.GIF.equals(headerType)) {
            return Constants.ImageFileType.IMAGE_TYPE_GIF;
        } else if (Constants.ImageFileHeader.TIF.equals(headerType)) {
            return Constants.ImageFileType.IMAGE_TYPE_TIFF;
        } else if (Constants.ImageFileHeader.BMP.equals(headerType)) {
            return Constants.ImageFileType.IMAGE_TYPE_BMP;
        }
        return "";
    }

    /**
     * 获取文件头信息
     *
     * @param filePath 文件路径
     * @return 文件头信息
     */
    public static String getFileHeader(String filePath) {
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            // 取文件流的前4个字节，为图片文件头描述字节
            byte[] b = new byte[4];
            /**
             * int read() 从此输入流中读取一个数据字节。int read(byte[] b) 从此输入流中将最多 b.length
             * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len)
             * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
             */
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * 返回文件头byte数组的十六进制字符串
     *
     * @param src 文件的byte数组
     * @return 文件头信息
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            // 以十六进制无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    /**
     * 图片格式转换：默认转换为jpg/jpeg
     *
     * @param srcfileName 源文件路径
     * @param dstFileName 转换后文件保存路径
     * @return
     */
    public static void imageTransfer(String srcfileName, String dstFileName, String errorTmpDir) {
        try {
            File srcFile = new File(srcfileName);
            if (srcFile.exists()) {
                imageTransfer(srcFile, dstFileName, errorTmpDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片格式转换：默认转换为jpg/jpeg
     *
     * @param srcFile     源文件对象
     * @param dstFileName 转换后文件保存路径
     * @return
     */
    public static void imageTransfer(File srcFile, String dstFileName, String errorTempDir) throws IOException {
        imageTransfer(srcFile, dstFileName, errorTempDir, Constants.FILE_FORMAT);
    }

    /**
     * 图片格式转换：默认转换为jpg/jpeg
     *
     * @param srcFile      源文件对象
     * @param dstFileName  转换后文件保存路径
     * @param errorTempDir 转换异常图片存放目录
     * @param fileFormat   图像转换格式
     * @return
     */
    public static void imageTransfer(File srcFile, String dstFileName, String errorTempDir, String fileFormat) throws IOException {
        if (fileFormat == null || "".equals(fileFormat)) {
            fileFormat = Constants.FILE_FORMAT;
        }
        try {
            ImageInputStream input = ImageIO.createImageInputStream(srcFile);
            // 获得image阅读器，阅读对象为文件转换的流
            ImageReader reader = ImageIO.getImageReaders(input).next();
            reader.setInput(input);
            // 获取图片帧数
            int count = reader.getNumImages(true);
            // 获取最后一帧图像
            int numOfPage = judgeNumPages(count);
            BufferedImage image = reader.read(numOfPage, null);
            File f = new File(dstFileName);
            try {
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 写入图片，并指定图片格式
            ImageIO.write(image, fileFormat, f);
            reader.dispose();
            input.close();
        } catch (IIOException e) {
            logger.error("图片转换异常:" + e.getMessage());
            e.printStackTrace();
            // 将转换失败的图片存放至临时文件夹
            FileCopyUtils.copy(srcFile, new File(errorTempDir + srcFile.getName()));
        } catch (IOException e) {
            logger.error("图片转换异常:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 判断帧数，默认返回最后一帧图像
     *
     * @param count
     * @return
     */
    public static int judgeNumPages(int count) {
        //如果图片个数大于1，则取最后一张图片，如果等于1，则取第一张
        if (count > 1) {
            return count - 1;
        } else {
            return 0;
        }
    }

    /**
     * 图片格式转换（这里将所有格式转换为jpg/jpeg格式）
     *
     * @param srcFile     源文件对象
     * @param dstFileName 转换后文件保存路径
     * @return
     */
    public static void imageTransfer2(File srcFile, String dstFileName) {
        try {
            BufferedImage bi = ImageIO.read(srcFile);
            ImageIO.write(bi, Constants.FILE_FORMAT, new File(dstFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证图片格式，如果不为jpg/jpeg/png则转换格式
     *
     * @param srcFileName
     * @param dstFileName
     * @return
     */
    public static String checkAndTransfer(String srcFileName, String dstFileName, String errorTmpDir) {
        String fileType = getFileType(srcFileName);
        if (!Constants.ImageFileType.IMAGE_TYPE_PNG.equals(fileType) && !Constants.ImageFileType.IMAGE_TYPE_JPG.equals(fileType)) {
            imageTransfer(srcFileName, dstFileName, errorTmpDir);
        }
        return fileType;
    }

    /**
     * 图像格式判断工具类
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\tujia\\Desktop\\tmp\\上海交行";
        String outFileName = "C:\\Users\\tujia\\Desktop\\tmp\\imageTransfer\\transfer2\\";
        File dir = new File(fileName);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File file : files) {
                    String fileType = getFileType(file.getAbsolutePath());
                    System.out.println("文件名：" + file.getName() + "--文件类型：" + fileType);
                    if (!"jpg".equals(fileType) && !"png".equals(fileType)) {
                        // 转换格式
                        String dstFileName = outFileName + file.getName().substring(0, file.getName().lastIndexOf(".")) + "_transfer" + ".jpg";
                        imageTransfer(file, dstFileName, "C:\\Users\\tujia\\Desktop\\tmp\\imageTransfer\\test3\\");
                    }
                }
            }
        }
    }

}

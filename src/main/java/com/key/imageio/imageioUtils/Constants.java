package com.key.imageio.imageioUtils;

/**
 * 静态常量类
 */
public class Constants {

    public static int DEFAULT_WIDTH = 200;  // 默认图像宽度
    public static int DEFAULT_HEIGHT = 150; // 默认图像高度
    public static int DEFAULT_COLS = 2; // 默认列数
    public static int DEFAULT_ROWS = 2; // 默认行数
    public static int MAX_COLS = 20;    // 最大列数
    public static int MAX_ROWS = 20;    // 最大行数

    public static String FILE_FORMAT = "JPEG";
    public static int DEFAULT_PIXEL = 10;   // 默认宽度
    public static int DEFAULT_HIEGHT_PIXEL = 5;   // 默认高度

    /**
     * 图片格式文件头
     */
    public static final class ImageFileHeader {
        public static final String JPG_OR_JPEG = "FFD8FFE0"; // jpg,jpeg
        public static final String PNG = "89504E47"; // png
        public static final String GIF = "47494638"; // gif
        public static final String TIF = "49492A00"; // tiff
        public static final String BMP = "424DAE8D"; // bmp
    }

    /**
     * 图片格式
     */
    public static final class ImageFileType {
        public static String IMAGE_TYPE_GIF = "gif";  // 图形交换格式
        public static String IMAGE_TYPE_JPG = "jpg";  // 联合照片专家组
        public static String IMAGE_TYPE_JPEG = "jpeg";  // 联合照片专家组
        public static String IMAGE_TYPE_BMP = "bmp";  // 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
        public static String IMAGE_TYPE_PNG = "png";  // 可移植网络图形
        public static String IMAGE_TYPE_PSD = "psd";  // Photoshop的专用格式Photoshop
        public static String IMAGE_TYPE_TIFF = "tif";  // Photoshop的专用格式Photoshop
    }

    /**
     * 位置，上中下共九个位置
     */
    public static final class Position {
        public static final String TOP_LEFT = "1";  // 左上
        public static final String TOP_CENTER = "2";    // 上中
        public static final String TOP_RIGHT = "3"; // 右上
        public static final String CENTER_LEFT = "4";   // 左中
        public static final String CENTER = "5";    // 中
        public static final String CENTER_RIGHT = "6";  // 右中
        public static final String BOTTOM_LEFT = "7";   // 左下
        public static final String BOTTOM_CENTER = "8"; // 下中
        public static final String BOTTOM_RIGHT = "9";  // 右下
    }

}

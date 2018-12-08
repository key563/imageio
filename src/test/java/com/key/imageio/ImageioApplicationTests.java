package com.key.imageio;

import com.key.imageio.imageioUtils.ImageTransUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageioApplicationTests {

    @Test
    public void contextLoads() {
    }

    public static void main(String[] args) {
        System.out.println("test");
        new ImageioApplicationTests().method();
    }

    public void method(){
        Thread t = new Thread("thread1");
        t.start();
    }

    @Test
    public void ImageFileTypeTest() throws IOException {
        String fileName = "C:\\Users\\tujia\\Desktop\\tmp\\testpic\\temp";
        String outFileName = "C:\\Users\\tujia\\Desktop\\tmp\\imageTransfer\\transfer2\\";
        File dir = new File(fileName);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File file : files) {
                    String fileType = ImageTransUtils.getFileType(file.getAbsolutePath());
                    System.out.println("文件名：" + file.getName() + "--文件类型：" + fileType);
//                    if (!"jpg".equals(fileType) && !"png".equals(fileType)) {
//                        // 转换格式
//                        String dstFileName = outFileName + file.getName().substring(0, file.getName().lastIndexOf(".")) + "_transfer" + ".jpg";
//                        ImageTransUtils.imageTransfer(file, dstFileName, "C:\\Users\\tujia\\Desktop\\tmp\\imageTransfer\\test3\\");
//                    }
                }
            }
        }
    }
}

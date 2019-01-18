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


}

package com.key.imageio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author tujia
 * @since 2020/6/20 13:47
 */
public class SocketChannelTest {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(new InetSocketAddress("localhost", 8888));

        // 发送请求数据
        ByteBuffer buffer = ByteBuffer.wrap("1234567890".getBytes());
        socketChannel.write(buffer);

        // 读取响应
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int num;
        while ((num = socketChannel.read(readBuffer)) > 0) {
            // 切换为读模式
            readBuffer.flip();

            byte[] bytes = new byte[num];
            readBuffer.get(bytes);
            String result = new String(bytes, "UTF-8");
            System.out.println("返回值：" + result);
        }
    }
}

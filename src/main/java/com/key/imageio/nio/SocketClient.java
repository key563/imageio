package com.key.imageio.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

/**
 * @author tujia
 * @since 2020/6/20 14:45
 */
public class SocketClient {

    // 监听端口
    private static final int PORT = 8888;

    private void asyncMode() throws Exception {

        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();

        Future<?> future = client.connect(new InetSocketAddress("localhost", PORT));

        // 阻塞下，等待连接成功
        future.get();

        Attachment att = new Attachment();
        att.setClient(client);
        att.setReadMode(false);
        att.setBuffer(ByteBuffer.allocate(2048));

        byte[] data = "test for client".getBytes();
        att.getBuffer().put(data);
        att.getBuffer().flip();

        // 异步发送数据到服务端
        client.write(att.getBuffer(), att, new ClientChannelHandler());

        // 等待5s再退出，以保证有时间处理数据
        Thread.sleep(5000);
    }

    public static void main(String[] args) throws Exception {
        SocketClient client = new SocketClient();
        client.asyncMode();
    }
}

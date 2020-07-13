package com.key.imageio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author tujia
 * @since 2020/6/20 13:37
 */
public class SocketServerTest {

    // 监听端口
    private static final int PORT = 8888;

    /**
     * 异步方式AsynchronousServerSocketChannel
     *
     * @throws IOException
     */
    public void asyncMode() throws IOException {
        // 实例化，并监听端口-与同步方式一致
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
        // 自己定义一个 Attachment 类，用于传递一些信息
        Attachment attachment = new Attachment();
        attachment.setServer(server);

        server.accept(attachment, new CompletionHandler<AsynchronousSocketChannel, Attachment>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Attachment attachment) {
                try {
                    SocketAddress clientAddr = client.getRemoteAddress();
                    System.out.println("收到新的连接：" + clientAddr);

                    // 重新调用accept方法等待新的连接进来
                    attachment.getServer().accept(attachment, this);

                    Attachment att = new Attachment();
                    att.setServer(server);
                    att.setClient(client);
                    att.setReadMode(true);
                    att.setBuffer(ByteBuffer.allocate(1024));

                    client.read(att.getBuffer(), att, new ChannelHandler());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Attachment attachment) {
                System.out.println("accept failed");
            }
        });

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步方式
     *
     * @throws IOException
     */
    public void syncMode() throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel server = ServerSocketChannel.open();

        server.socket().bind(new InetSocketAddress(PORT));

        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    // 有已经接受到新的服务端的连接
                    SocketChannel socketChannel = server.accept();

                    // 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    // 将新的连接注册到Selector中，监听OP_READ事件，等待数据传输
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    // 有数据可读
                    // 获取注册并监听了OP_READ时间的socketChannel
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int num = socketChannel.read(buffer);

                    if (num > 0) {
                        System.out.println("收到数据：" + new String(buffer.array()).trim());
                        // 返回给客户端数据
                        ByteBuffer writeBuffer = ByteBuffer.wrap("返回给客户端的数据...".getBytes());
                        socketChannel.write(writeBuffer);
                    } else if (num == -1) {
                        // 连接已经关闭
                        socketChannel.close();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        SocketServerTest server = new SocketServerTest();
        // 同步方式
//        server.syncMode();
        // 异步方式
        server.asyncMode();
    }
}

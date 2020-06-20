package com.key.imageio.nio;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;

/**
 * @author tujia
 * @since 2020/6/20 14:00
 */
public class ChannelHandler implements CompletionHandler<Integer, Attachment> {

    @Override
    public void completed(Integer result, Attachment attachment) {
        if (attachment.isReadMode()) {
            // 读取来自客户端的数据
            ByteBuffer buffer = attachment.getBuffer();
            buffer.flip();
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes);

            String msg = new String(buffer.array()).trim();
            System.out.println("收到来自客户端的数据：" + msg);

            // 响应客户端请求，返回数据
            buffer.clear();

            buffer.put("Response from server!".getBytes((Charset.forName("UTF-8"))));
            attachment.setReadMode(false);
            // 切换读写模式
            buffer.flip();
            // 写数据到客户端也是异步的，使用的是AsynchronousSocketChannel
            attachment.getClient().write(buffer, attachment, this);

        } else {
            // 写数据结束后的处理，这里测试直接断开连接
            try {
                attachment.getClient().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {
        System.out.println("连接断开");
    }
}

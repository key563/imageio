package com.key.imageio.nio;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;

/**
 * @author tujia
 * @since 2020/6/20 14:40
 */
public class ClientChannelHandler implements CompletionHandler<Integer, Attachment> {
    @Override
    public void completed(Integer result, Attachment attachment) {

        ByteBuffer buffer = attachment.getBuffer();

        if (attachment.isReadMode()) {
            buffer.flip();

            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes);

            String msg = new String(bytes, Charset.forName("UTF-8"));
            System.out.println("收到来自服务端的响应数据：" + msg);

            try {
                attachment.getClient().close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            attachment.setReadMode(true);
            buffer.clear();
            attachment.getClient().read(buffer, attachment, this);

        }

    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {
        System.out.println("服务器无响应");
    }
}

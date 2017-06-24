package com.abitty.transport.socket;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.SocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * SOCKET 通信封装
 * 代码示例
 * <pre>
 *   SyncSocketSender syncSocketSender = new SyncSocketSender();
 *   syncSocketSender.connect("localhost",15999);
 *
 *   String data = syncSocketSender.send("dfsdfsdfdsfsd".getBytes(), "utf-8");
 *   // do something else
 *   syncSocketSender.disconnect();
 * </pre>
 * User: wenlong.du
 * Date: 2014/8/29
 * Time: 10:42
 */
public class SyncSocketSender extends SocketClient {

    private static Logger logger = LoggerFactory.getLogger(SyncSocketSender.class);

    protected String charset;

    /**
     * @param data 发送数据
     * @return
     */
    public byte[] send(byte[] data) {
        try {
            IOUtils.write(data, _output_);

            return IOUtils.toByteArray(_input_);
        } catch (IOException e) {
            logger.error("发送数据过程中出现异常", e);
        }
        return null;
    }

    /**
     * @param data 发送数据
     * @param charset 响应编码
     * @return
     */
    public String send(byte[] data, String charset) {
        try {
            IOUtils.write(data, _output_);

            return IOUtils.toString(_input_, charset);
        } catch (IOException e) {
            logger.error("发送数据过程中出现异常", e);
        }
        return null;
    }
}

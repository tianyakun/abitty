package com.abitty.transport.socket;

import com.abitty.transport.future.SocketFuture;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author litingpeng
 */
public class AsyncSocketConnection<T> extends SimpleChannelUpstreamHandler{

    private int port;

    private String host;

    private Channel channel;

    private SocketFuture<T> socketFuture;

    private final AtomicBoolean closed = new AtomicBoolean(false);

    public AsyncSocketConnection(String host, int port, int readTimeout, TimeUnit timeUnit){
        this.host = host;
        this.port = port;
        this.socketFuture = new SocketFuture<T>(readTimeout, timeUnit);
    }

    public SocketFuture write(ChannelBuffer message){
        if(channel != null){
            channel.write(message);
        }
        return socketFuture;
    }

    public synchronized void close(boolean isCancel) {
        if (!closed.get() && channel != null) {
            closed.set(true);
            channel.close();
            if(isCancel){
                socketFuture.cancel(false);
            }
        }
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        if (!closed.get() && channel != null) {
            close(true);
        }
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        channel = ctx.getChannel();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        if (!closed.get() && channel != null) {
            close(true);
        }
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        socketFuture.setResultAndAwake(((ChannelBuffer)e.getMessage()).array());
        close(false);
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }
}

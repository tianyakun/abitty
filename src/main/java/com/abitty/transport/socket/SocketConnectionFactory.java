package com.abitty.transport.socket;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author litingpeng
 */
public class SocketConnectionFactory {

    private int port;

    private String host;

    private int timeout;

    private ClientBootstrap clientBootstrap;

    public SocketConnectionFactory(String host, int port, int timeout, ClientBootstrap clientBootstrap) {
        this.port = port;
        this.host = host;
        this.timeout = timeout;
        this.clientBootstrap = clientBootstrap;
    }

    public AsyncSocketConnection create() {
        try{
            AsyncSocketConnection asyncSocketConnection = new AsyncSocketConnection(host, port, timeout, TimeUnit.MILLISECONDS);

            ChannelPipeline pipeline = Channels.pipeline(asyncSocketConnection);
            Channel channel = clientBootstrap.getFactory().newChannel(pipeline);

            ChannelFuture future = channel.connect(new InetSocketAddress(host, port));
            future.await();

            if (!future.isSuccess()) {
                throw future.getCause();
            }

            return asyncSocketConnection;
        }catch(Throwable e){
            throw new RuntimeException("SocketConnectionFactory.create出现异常", e);
        }
    }

}

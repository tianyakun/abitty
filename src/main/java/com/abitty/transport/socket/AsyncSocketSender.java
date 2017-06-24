package com.abitty.transport.socket;

import com.abitty.transport.future.SocketFuture;
import com.abitty.transport.util.NamedThreadFactory;
import com.google.common.base.Preconditions;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.socket.ClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author litingpeng
 */
public class AsyncSocketSender {

    private String name;

    private ClientBootstrap clientBootstrap;

    private SocketConnectionFactory socketConnectionFactory;

    public AsyncSocketSender(String name, String host, int port, int timeout){
        Preconditions.checkNotNull(name, "AsyncSocketSender创建时name不能为空");
        Preconditions.checkNotNull(host, "AsyncSocketSender创建时host不能为空");

        ExecutorService connectors = Executors.newFixedThreadPool(1, new NamedThreadFactory("PG-AsyncSoc-" + name));
        ExecutorService workers = Executors.newFixedThreadPool(SocketConstants.DEFAULT_MAX_TOTAL, new NamedThreadFactory("PG-AsyncSoc-" + name));
        ClientSocketChannelFactory clientSocketChannelFactory = new NioClientSocketChannelFactory(connectors, workers);

        setName(name);

        InetSocketAddress address = new InetSocketAddress(host, port);

        clientBootstrap = new ClientBootstrap(clientSocketChannelFactory);
        clientBootstrap.setOption("remoteAddress", address);
        clientBootstrap.setOption("connectTimeoutMillis", TimeUnit.SECONDS.toMillis(SocketConstants.DEFAULT_CONNECT_TIMEOUT));

        socketConnectionFactory = new SocketConnectionFactory(host, port, timeout, clientBootstrap);

        addShutdownHook();
    }

    /**
     * @param data 发送数据
     * @return
     */
    public SocketFuture<byte[]> send(byte[] data) {
        AsyncSocketConnection asyncSocketConnection = socketConnectionFactory.create();
        return asyncSocketConnection.write(ChannelBuffers.wrappedBuffer(data));
    }

    private void setName(String name) {
        this.name = name;
    }

    private void addShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run() {
                clientBootstrap.releaseExternalResources();
            }
        });
    }

    public String getName() {
        return name;
    }
}

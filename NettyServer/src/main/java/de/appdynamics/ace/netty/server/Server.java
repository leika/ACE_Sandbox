package de.appdynamics.ace.netty.server;


import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by stefan.marx on 24.01.14.
 */
public class Server {

    private final int _port;

    public Server(int port) {

        _port = port;


    }


    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 9991;
        }
        new Server(port).run();
    }

    private void run() {
        ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        ServerBootstrap bs = new ServerBootstrap(factory);

        bs.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new EchoHandler());
            }
        });

        bs.setOption("child.tcpNoDelay",true);
        bs.setOption("child.keepAlive",true);

        bs.bind(new InetSocketAddress(_port));

    }
}

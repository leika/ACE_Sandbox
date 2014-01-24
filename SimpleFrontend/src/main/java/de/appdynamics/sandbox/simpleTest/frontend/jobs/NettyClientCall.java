package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import de.appdynamics.ace.framework.jobs.Job;
import de.appdynamics.ace.netty.server.EchoHandler;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by stefan.marx on 24.01.14.
 */
public class NettyClientCall extends Job {
    @Override
    protected String callJob() {

        ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        ClientBootstrap bs = new ClientBootstrap(factory);

        bs.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new EchoClientHandler());
            }
        });

        bs.setOption("tcpNoDelay",true);
        bs.setOption("keepAlive",true);

      ChannelFuture fut =   bs.connect(new InetSocketAddress("localhost",9991));

        fut.awaitUninterruptibly();
        if (!fut.isSuccess()) {
            fut.getCause().printStackTrace();
        }
        factory.releaseExternalResources();

        return "OK";
    }

    @Override
    public String getName() {
        return "Netty Client Call";
    }

    private class EchoClientHandler extends SimpleChannelHandler {

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            System.out.println("MR:"+e.getMessage());
        }

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            e.getChannel().write("STEFAN");

            ChannelBuffer b = ChannelBuffers.buffer(23);


        }
    }
}

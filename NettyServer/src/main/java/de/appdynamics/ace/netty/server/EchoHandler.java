package de.appdynamics.ace.netty.server;

import org.jboss.netty.channel.*;

/**
 * Created by stefan.marx on 24.01.14.
 */
public class EchoHandler extends SimpleChannelHandler{
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Channel ch = e.getChannel();
        ch.write(e.getMessage());
    }
}

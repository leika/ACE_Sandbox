package de.appdynamics.ace.tcp.server;

import com.appdynamics.ace.util.cli.api.api.CommandlineExecution;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 06.11.13
 * Time: 00:21
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) {

        CommandlineExecution cle = new CommandlineExecution("TCPBackend");

        cle.addCommand(new StartBackendServer() );
         cle.execute(args);

    }
}

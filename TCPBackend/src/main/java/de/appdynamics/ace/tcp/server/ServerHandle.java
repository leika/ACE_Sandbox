package de.appdynamics.ace.tcp.server;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 06.11.13
 * Time: 00:22
 * To change this template use File | Settings | File Templates.
 */
public class ServerHandle {
    private final int _port;

    public ServerHandle(int i) {
        _port = i;
    }

    public void start() {

        System.out.println("START!!! listening on Socket "+_port);
        new SocketThread().start();

    }

    private class SocketThread extends Thread {


        private final ThreadPoolExecutor _executor;

        private SocketThread() {
            BlockingQueue<Runnable> queue = new LinkedBlockingDeque<Runnable>();

            _executor = new ThreadPoolExecutor(5,10,120, TimeUnit.MINUTES,queue);

        }

        @Override
        public void run() {

            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(_port);
            } catch (IOException e) {
                return;
            }

            while (true) {
                try {

                Socket clientSocket = serverSocket.accept();

                 _executor.execute(new ChannelCommand(clientSocket));
                } catch (Exception ex) {ex.printStackTrace();}
            }


        }
    }
}

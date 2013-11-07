package de.appdynamics.ace.tcp.server;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 06.11.13
 * Time: 00:21
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        new ServerHandle(8989).start();
        while(true) {}
    }
}

package de.appdynamics.ace.rmi.server;

import com.appdynamics.ace.util.cli.api.api.CommandlineExecution;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by stefan.marx on 27.11.14.
 */
public class Main {

        public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {

            System.out.println("Open Socket at 1099");
            LocateRegistry.createRegistry(1099);
            EchoBusinessImpl h = new EchoBusinessImpl();

            Naming.bind("server.EchoBusiness", h);


            System.out.println("RMI Ready.");

        }

}

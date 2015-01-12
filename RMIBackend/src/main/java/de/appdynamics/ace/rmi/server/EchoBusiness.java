package de.appdynamics.ace.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by stefan.marx on 27.11.14.
 */
public interface EchoBusiness extends Remote{
    String hello() throws RemoteException;
}

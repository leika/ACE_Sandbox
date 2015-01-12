package de.appdynamics.ace.rmi.server;

import de.appdynamics.ace.rmi.server.EchoBusiness;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by stefan.marx on 27.11.14.
 */
public class EchoBusinessImpl extends UnicastRemoteObject implements EchoBusiness{

    protected EchoBusinessImpl() throws RemoteException {
    }

    @Override
    public String hello() throws RemoteException {
        RMIBusinessCalltree ct = new RMIBusinessCalltree(5,78,16);
        ct.callMyBusiness();
        System.out.println("Executed ...");
        return "done ";
    }
}

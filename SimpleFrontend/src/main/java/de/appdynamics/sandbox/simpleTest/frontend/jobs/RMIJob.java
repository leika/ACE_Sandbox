package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import de.appdynamics.ace.framework.jobs.Job;
import de.appdynamics.ace.rmi.server.EchoBusiness;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by stefan.marx on 27.11.14.
 */
public class RMIJob extends Job {
    private final int _port;

    public RMIJob(int i) {
        super();
        _port = i;
    }

    @Override
    protected String callJob() throws Throwable {
        Registry registry = LocateRegistry.getRegistry("localhost",1099);
        EchoBusiness b = (EchoBusiness) registry.lookup("server.EchoBusiness");


        return b.hello();

    }

    @Override
    public String getName() {
        return "Call RMI on "+_port;
    }
}

package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import de.appdynamics.ace.framework.jobs.Job;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 15.11.13
 * Time: 09:11
 * To change this template use File | Settings | File Templates.
 */
public class TCPRedirect extends TCPClientSimple {
    public TCPRedirect(String localhost, int i, String backend, int i1) {
        super(localhost,i,backend,i1);
    }

    @Override
    public String getName() {
        return "TCPRedirect";
    }
}

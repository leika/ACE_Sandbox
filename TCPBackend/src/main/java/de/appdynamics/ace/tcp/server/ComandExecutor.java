package de.appdynamics.ace.tcp.server;

import de.appdynamics.ace.sandbox.tcpBackend.api.Command;
import de.appdynamics.ace.sandbox.tcpBackend.api.Result;
import de.appdynamics.ace.sandbox.tcpBackend.api.ResultCode;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 06.11.13
 * Time: 10:54
 * To change this template use File | Settings | File Templates.
 */
public class ComandExecutor {
    public static Result executeCommand(Command c) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return callBusinessLogic(c);
    }

    private static Result callBusinessLogic(Command c) {
        try {
            Thread.sleep(23);
            if (c.getCmd().equals("BACKEND")) {
                new BackendConnector ("localhost",c.getArgs().get("targetPort")).callBackend(c);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return new Result(ResultCode.OK,"AllOK "+c.getCmd());
    }
}

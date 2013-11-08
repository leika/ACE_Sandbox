package de.appdynamics.sandbox.simpleTest.frontend;

import com.appdynamics.ace.util.cli.api.api.CommandlineExecution;
import de.appdynamics.ace.framework.jobs.JobDescription;
import de.appdynamics.ace.framework.jobs.JobResult;
import de.appdynamics.ace.framework.jobs.ProgressCallback;
import de.appdynamics.ace.framework.jobs.RandomJobExecutor;
import de.appdynamics.sandbox.simpleTest.frontend.jobs.SimpleBusinessJob;
import de.appdynamics.sandbox.simpleTest.frontend.jobs.TCPClientSimple;
import de.appdynamics.sandbox.simpleTest.frontend.jobs.WebserviceHelloWorld;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 12:57
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        CommandlineExecution cle = new CommandlineExecution("Sandbox");
        SandboxExecution c ;
        cle.addCommand(c = new SandboxExecution("StartNormalSandbox"));
        try {
            c.addJob(new SimpleBusinessJob("MySimpleBusinessJob",12,80,20));
            c.addJob(new SimpleBusinessJob("MyComplexTask",12,280,20)).setWeight(7);
            c.addJob(new SimpleBusinessJob("CRM-FindUser", 12, 280, 20));
            c.addJob(new SimpleBusinessJob("CRM-Report",12,280,20));
            c.addJob(new TCPClientSimple()).setWeight(100);
            c.addJob(new WebserviceHelloWorld()).setWeight(15);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        cle.execute(args);

    }


}

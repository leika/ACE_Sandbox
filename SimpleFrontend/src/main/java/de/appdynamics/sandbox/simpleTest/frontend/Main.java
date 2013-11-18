package de.appdynamics.sandbox.simpleTest.frontend;

import com.appdynamics.ace.util.cli.api.api.CommandlineExecution;
import de.appdynamics.sandbox.simpleTest.frontend.jobs.*;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 12:57
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        try {

            CommandlineExecution cle = new CommandlineExecution("Sandbox");
            cle.setHelpVerboseEnabled(false);

            SandboxExecution c ;


            cle.addCommand(c = new SandboxExecution("StartAll"));
            addBasicLoad(c);
            addTCPFullLoad(c);
            addWebserviceLoad(c);



            cle.addCommand(c = new SandboxExecution("StartSimple"));
            addBasicLoad(c);

            cle.addCommand(c = new SandboxExecution("StartWeb"));
            addBasicLoad(c);
            addWebserviceLoad(c);

            cle.addCommand(c = new SandboxExecution("StartTCP"));
            addBasicLoad(c);
            addTCPFullLoad(c);


            cle.addCommand(c = new SandboxExecution("StartTCPSimple"));
            addBasicLoad(c);
            addTCPSimpleLoad(c);





            cle.execute(args);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }




    }

    private static void addWebserviceLoad(SandboxExecution c) throws Exception {
        c.addJob(new WebserviceHelloWorld()).setWeight(15);
    }

    private static void addTCPSimpleLoad(SandboxExecution c) throws Exception {
        c.addJob(new TCPClientSimple("localhost", 8989)).setWeight(100);
        c.addJob(new TCPClientSimple("localhost",8999)).setWeight(100);

    }

    private static void addTCPFullLoad(SandboxExecution c) throws Exception {
        addTCPSimpleLoad(c);
        c.addJob(new TCPRedirect("localhost",8999,"BACKEND",8989)).setWeight(100);
    }

    private static void addBasicLoad(SandboxExecution c) throws Exception {
        c.addJob(new SimpleBusinessJob("MySimpleBusinessJob",12,80,20));

        c.addJob(new GoogleBackendCall("FindGoogleBooks","ebooks","de"));
        c.addJob(new GoogleBackendCall("FindGoogleFacts","Master","de"));
        c.addJob(new GoogleBackendCall("FindGoogleFacts","Master","en"));
        c.addJob(new GoogleBackendCall("FindGoogleFacts","Master","com"));
        c.addJob(new GoogleBackendCall("FindGoogleFacts","Master","fr"));

        c.addJob(new SimpleBusinessJob("MyComplexTask",12,280,20)).setWeight(7);
        c.addJob(new SimpleBusinessJob("CRM-FindUser", 12, 280, 20));
        c.addJob(new SimpleBusinessJob("CRM-Report",12,280,20));
    }


    private static void addGoogleLoad(SandboxExecution c) throws Exception {
        c.addJob(new GoogleBackendCall("FindGoogleBooks","ebooks","de"));
        c.addJob(new GoogleBackendCall("FindGoogleFacts","Master","de"));
        c.addJob(new GoogleBackendCall("FindGoogleFacts","Master","en"));
        c.addJob(new GoogleBackendCall("FindGoogleFacts","Master","com"));
        c.addJob(new GoogleBackendCall("FindGoogleFacts","Master","fr"));
    }

}

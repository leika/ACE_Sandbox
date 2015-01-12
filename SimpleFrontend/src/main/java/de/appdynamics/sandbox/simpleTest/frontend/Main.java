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
            addAsyncLoad(c);
            addAPILoad(c);



            cle.addCommand(c = new SandboxExecution("StartSimple"));
            addBasicLoad(c);

            cle.addCommand(c = new SandboxExecution("StartComplexGetter"));
            addXMLGetterLoad(c);

            cle.addCommand(c = new SandboxExecution("StartWeb"));
            addBasicLoad(c);
            addWebserviceLoad(c);


            cle.addCommand(c = new SandboxExecution("StartNetty"));
            addBasicLoad(c);
            addNettyLoad(c);

            cle.addCommand(c = new SandboxExecution("StartWebJetty"));
            addBasicLoad(c);
            addJettyWebLoad(c);

            cle.addCommand(c = new SandboxExecution("StartTCP"));
            addBasicLoad(c);
            addTCPFullLoad(c);


            cle.addCommand(c = new SandboxExecution("StartTCPSimple"));
            addBasicLoad(c);
            addTCPSimpleLoad(c);

            cle.addCommand(c = new SandboxExecution("StartAsync"));
            addBasicLoad(c);
            addAsyncLoad(c);

            cle.addCommand(c = new SandboxExecution("StartApiTest"));
            addBasicLoad(c);
            addAPILoad(c);

            cle.addCommand(c = new SandboxExecution("StartExceptionTest"));
            addBasicLoad(c);
            addExceptionLoad(c);

            cle.addCommand(c = new SandboxExecution("StartBTExplosion"));
            addBTExplosion(c);

            cle.addCommand(c = new SandboxExecution("StartRMISimple"));
            addRMISimple(c);




            cle.execute(args);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }




    }

    private static void addRMISimple(SandboxExecution c) throws Exception {
        //addBasicLoad(c);
        c.addJob(new RMIJob(9099));



    }

    private static void addXMLGetterLoad(SandboxExecution c) throws Exception {
        c.addJob(new SimpleGetterJob("<?xml version='1.0' encoding='UTF-8' ?>\n" +
                "<data type = 'update' xmlns:xu='http://www.satelliteinfo.co.uk/feed/update'  xmlns:hrdg='http://www.satelliteinfo.co.uk/feed/master/hrdg'  xmlns='http://www.satelliteinfo.co.uk/feed/master/hrdg' id= 'UKDGES4G4U88' name= 'event' mnem= 'ES' timestamp= '1415192288107' date= '2014-11-05' group='SDS' category= 'DG' source= 'sportsData' country= 'UK' sportcode= 'BP' route= '4D41494E' version= '1.2.15' >\n" +
                "<xu:ups-at select=\"/hrdg:data/hrdg:meeting[@code='4G4U8']/hrdg:event[@id='2101661']\" >\n" +
                "\n" +
                "<xu:at name='progressCode'>H</xu:at>\n" +
                "<xu:at name='pmsg'>HARE RUNNING</xu:at>\n" +
                "</xu:ups-at>\n" +
                "</data>",12,80,20));
        c.addJob(new SimpleGetterJob( "<?xml version='1.0' encoding='UTF-8' ?>\n" +
                "<data type = 'update' xmlns:xu='http://www.satelliteinfo.co.uk/feed/update'  xmlns:hrdg='http://www.satelliteinfo.co.uk/feed/master/hrdg'  xmlns='http://www.satelliteinfo.co.uk/feed/master/hrdg' id= 'AUHRRS4G4W2' name= 'result' mnem= 'RS' timestamp= '1415184910355' date= '2014-11-05' group='SDS' category= 'HR' source= 'sportsData' country= 'AU' sportcode= 'LA' route= '454D50' version= '1.2.15' >\n" +
                "    <xu:ups-at select=\"/hrdg:data/hrdg:meeting[@code='4G4W2']/hrdg:event[@id='2101582']/hrdg:result[@id='3527485']\" >\n" +
                "        <xu:at name='message'>OFF</xu:at>\n" +
                "        <xu:at name='statuscode'>O</xu:at>\n" +
                "    </xu:ups-at>\n" +
                "</data>",12,80,20));


    }

    private static void addExceptionLoad(SandboxExecution c) throws Exception {
        c.addJob(new BTExceptionJob("Exception 1"));
        c.addJob(new BTExceptionJob("Exception 2"));


    }


    private static void addBTExplosion(SandboxExecution c) throws Exception {

        c.addJob(new BTExplodeBusinessJob("BTExplode1", 12, 280, 20, new RandomNamePrefix (10)));
        c.addJob(new BTExplodeBusinessJob("BTExplode2", 12, 280, 20, new RandomNamePrefix (10)));


    }

    private static void addAPILoad(SandboxExecution c) throws Exception {
        c.addJob( new APIJob("MyAPITransaction",true));
        c.addJob( new APIJob("MyAPITransaction2",true));
        c.addJob( new APIJob("MyAPITransaction3",true));
        c.addJob( new APIJob("MyAPITransaction4",true));
        c.addJob( new APIJob("MyNewNonAPITransaction",false));


    }

    private static void addNettyLoad(SandboxExecution c) throws Exception {
        c.addJob(new NettyClientCall()).setWeight(15);

    }

    private static void addJettyWebLoad(SandboxExecution c) throws Exception {
        c.addJob(new JettyWebserviceWorld()).setWeight(15);

    }

    private static void addAsyncLoad(SandboxExecution c) throws Exception {
        c.addJob(new AsyncCallback("SimpleAsync",5,30,45,true));
        c.addJob(new AsyncCallback("SimpleAsyncDirectReturn",5,50,150,false,true));
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

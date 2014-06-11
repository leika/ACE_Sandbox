package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import com.appdynamics.apm.appagent.api.AgentDelegate;
import com.appdynamics.apm.appagent.api.ITransactionDemarcator;
import de.appdynamics.ace.framework.jobs.Job;

/**
 * Created by stefan.marx on 04.06.14.
 */
public class APIJob extends Job {
    private final String _transactionName;

    private static ITransactionDemarcator transDelegate = AgentDelegate.getTransactionDemarcator();
    private final boolean _ic;


    public APIJob(String transactionName, boolean includeThread) {
        super();
        _transactionName = transactionName;
        _ic = includeThread;
    }

    @Override
    protected String callJob() {
        try {
            Thread.sleep(587);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return (_ic)?executeMyJob():executeMySimpleJob();

    }

    private String executeMySimpleJob() {

        BusinessCalltree t = new BusinessCalltree(6, 30, 10);
        t.callMyBusiness();


        return "done";
    }

    private String executeMyJob() {



        String id =transDelegate.beginOriginatingTransactionAndAddCurrentThread(_transactionName,null);
        BusinessCalltree t = new BusinessCalltree(6, 30, 10);
        t.callMyBusiness();

        transDelegate.endOriginatingTransactionAndRemoveCurrentThread();

        return "done";
    }

    @Override
    public String getName() {
        return "APICall :"+_transactionName;
    }
}

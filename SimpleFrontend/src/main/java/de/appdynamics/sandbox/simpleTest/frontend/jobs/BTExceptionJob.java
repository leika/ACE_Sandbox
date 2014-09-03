package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import de.appdynamics.ace.framework.jobs.Job;
import de.appdynamics.ace.framework.jobs.JobResult;

import java.util.Random;

/**
 * Created by stefan.marx on 22.07.14.
 */
public class BTExceptionJob extends Job {
    private String _name;
    private Random _rnd = new Random();

    public BTExceptionJob(String s) {
                        _name = s;

    }

    public JobResult call()  {
        try {
            _localResult.set(new JobResult());
            _localResult.get().addMessage(callJob());
            _localResult.get().stopTimer();
        } finally {
            JobResult result = _localResult.get();
            _localResult.remove();
            return result;
        }

    }

    @Override
    protected String callJob() throws Throwable {
        callExceptionThrower();
        return "DONE";
    }

    private void callExceptionThrower() throws Throwable {
        int i = _rnd.nextInt(5);

        switch (i) {
            case 0:
                throw new MySimpleException("Super Message");

            case 1:
                throw new MySecondSimpleException("Super Message include some more info");

            case 2:
                throw new MyComplexException("Super Complex Message");

            case 3:
                throw new MySimpleException("Important Message");

            case 4:
                throw new MyComplexException("Complex Message",new MySimpleException("deep Message"));


        }
    }



    @Override
    public String getName() {
        return _name;
    }
}

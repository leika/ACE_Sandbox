package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 20.11.13
 * Time: 11:52
 * To change this template use File | Settings | File Templates.
 */
public class AsyncCallback extends AsyncSimple {

    private boolean _callAgentAPI;

    public AsyncCallback(String name, int jobCount, int minDelay, int maxDelay, boolean wait) {
        super(name, jobCount, minDelay, maxDelay, wait);
    }

    public AsyncCallback(String name, int jobCount, int minDelay, int maxDelay, boolean wait, boolean callAgentAPI) {
        this(name, jobCount, minDelay, maxDelay, wait);
        setCallAgentAPI(callAgentAPI);
    }


    @Override
    public String getName() {
        return "AsyncCallback_"+getAsynchName();
    }

    @Override
    protected String callJob() {
        Callback cb = new Callback();

        for (int i = 0; i < getJobCount(); i++) {
            Future f;
            Callable task = new AsyncTask(getDelayTime());
            f = executeAsyncTask(new CallbackWrapper(task,cb));
        }


        try {
            if (isWait())  cb.waitOnReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "READY!";
    }

    public void setCallAgentAPI(boolean callAgentAPI) {
        _callAgentAPI = callAgentAPI;
    }

    public boolean isCallAgentAPI() {
        return _callAgentAPI;
    }

    private class Callback {

        private int _tracker = 0;
        private boolean _finished = false;
        private Object _synch = new Object();

        public synchronized void visit() {
            _tracker++;
            _finished = false;
        }

        public synchronized void leave() {
            _tracker--;
            if (_tracker <= 0) {
                _finished = true;
               synchronized (_synch) {
                   _synch.notifyAll();
               }
            }
        }


        public void waitOnReady() throws InterruptedException {
            synchronized (_synch) {
                _synch.wait();
            }
        }
    }

    private class CallbackWrapper implements Callable {
        private final Callable _task;
        private final Callback _cb;

        public CallbackWrapper(Callable task, Callback cb) {
            _task = task;
            _cb = cb;
            _cb.visit();
        }

        @Override
        public Object call() throws Exception {
            Object result = null;
            try {
                result = _task.call();
            } finally {
              _cb.leave();
              return result;
            }
        }
    }
}

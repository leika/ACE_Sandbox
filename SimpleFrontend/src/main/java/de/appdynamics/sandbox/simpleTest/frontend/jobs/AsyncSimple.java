package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import de.appdynamics.ace.framework.jobs.Job;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 19.11.13
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class AsyncSimple extends Job {
    private String _asynchName;
    private int _jobCount;
    private int _delayMin;
    private int _delayVarianz;

    private Random _rnd = new Random();

    private static ThreadPoolExecutor _exec = new ThreadPoolExecutor(4,8,10000l, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(100));
    private boolean _wait;
    static {

    }

    public AsyncSimple(String name, int jobCount, int minDelay, int maxDelay, boolean wait) {
        setAsynchName(name);
        setJobCount(jobCount);
        setDelayMin(minDelay);
        setDelayVarianz(maxDelay - minDelay);
        setWait(wait);

    }

    @Override
    protected String callJob() {
        ArrayList<Future> listOfCalls = new ArrayList<Future>();

        for (int i = 0; i < getJobCount(); i++) {
           Future f;
            Callable task = new AsyncTask(getDelayTime());
            f = executeAsyncTask(task);
        }

        if (isWait() ) waitForCompletion(listOfCalls);

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "READY!";
    }

    protected Future executeAsyncTask(Callable task) {
        return _exec.submit(task);
    }

    private void waitForCompletion(ArrayList<Future> listOfCalls) {
        boolean ready = false;
        while (!ready) {
            ready = true;
            for(Future f: listOfCalls) {
                if (!(f.isCancelled() || f.isDone())) {
                    ready = false;
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected long getDelayTime() {
        return getDelayMin()+(_rnd.nextInt(getDelayVarianz()));
    }

    @Override
    public String getName() {
        return "Async_"+getAsynchName();
    }

    public void setAsynchName(String asynchName) {
        _asynchName = asynchName;
    }

    public String getAsynchName() {
        return _asynchName;
    }

    public void setJobCount(int jobCount) {
        _jobCount = jobCount;
    }

    public int getJobCount() {
        return _jobCount;
    }

    public void setDelayMin(int delayMin) {
        _delayMin = delayMin;
    }

    public int getDelayMin() {
        return _delayMin;
    }

    public void setDelayVarianz(int delayVarianz) {
        _delayVarianz = delayVarianz;
    }

    public int getDelayVarianz() {
        return (_delayVarianz<0)?0:_delayVarianz;
    }

    public void setWait(boolean wait) {
        _wait = wait;
    }

    public boolean isWait() {
        return _wait;
    }

    private static class ExecThreadFactory implements ThreadFactory {
        private int _threadNum = 1;


        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread();
            t.setName("ExecThread_"+(_threadNum++));
            System.out.println("Created Thread :"+t.getName());
            t.start();
            return t;
        }
    }

    protected class AsyncTask implements Callable<String> {
        private final long _dt;

        public AsyncTask(long delayTime) {
            _dt = delayTime;


        }

        @Override
        public String call() throws Exception {
            Thread.sleep(_dt);
            BusinessCalltree ct = new BusinessCalltree(3,10,4);
            ct.callMyBusiness();
            return null;
        }
    }
}

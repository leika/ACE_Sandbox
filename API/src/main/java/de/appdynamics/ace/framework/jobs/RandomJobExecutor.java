package de.appdynamics.ace.framework.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 */
public class RandomJobExecutor implements JobExecutor {

    private final ArrayList<JobDescription> _jobs;
    private final long _delay;
    private  long _varianz = 0l;
    private  int _numThreads = 1;
    private int _maxWeight;

    private ExecuteOneJob _ej;
    private ArrayList<Thread> _threads;
    private ArrayList<ProgressCallback> _callbacks = new ArrayList<ProgressCallback>();

    public RandomJobExecutor() {

        this(2000);

    }

    public RandomJobExecutor(long delay) {
        _jobs = new ArrayList<JobDescription>();
        _delay = delay;
    }

    public RandomJobExecutor(int numThreads,long delay, long varianz) {
        this(delay);
        _numThreads = numThreads;
        _varianz = varianz;
    }


    @Override
    public List<JobDescription> getAllJobs() {
        return new ArrayList<JobDescription>(_jobs);
    }

    @Override
    public String toDebugString() {
        StringBuffer erg = new StringBuffer("JOBS:\n--------\n");
        for (JobDescription desc : _jobs) {
            erg.append(desc.getExcecutionCount())
                    .append("  : ")
                    .append(desc.getJob().getName())
                    .append("  (")
                    .append(desc.getWeight())
                    .append(")\n");
        }

        erg.append("------------\nMax Weight:")
        .append(findAggregatedWeight())
        .append("\n");

        return erg.toString();
    }

    @Override
    public JobDescription addJob(Job job) throws Exception {

        if (isRunning()) throw new Exception("Stop Execution first!");

        JobDescription jd = new JobDescription(job, 1);
        _jobs.add(jd);
        return jd;
    }


    public boolean isRunning() {

        //no Threadlist means not running
        if (_threads == null) return false;


        // is Running if at least 1 Thread runs
        for (Thread t: _threads) {
            if (t.isAlive()) return true;
        }

        // otherwise it'S not running
        return false;
    }

    @Override
    public void addProgressCallback(ProgressCallback progressCallback) {
        _callbacks.add(progressCallback);
    }

    @Override
    public JobDescription addJob(Class<Job> jobClazz) throws Exception {

        Job j = jobClazz.newInstance();
        return addJob(j);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JobDescription addJob(String job) throws Exception {

        Class<Job> cl = (Class<Job>) Class.forName(job);

        return addJob(cl);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void run() {
        _maxWeight = findAggregatedWeight();
        _threads = new ArrayList<Thread>();

        for (int i = 0; i < _numThreads; i++) {
            long delay = _delay;

            if (_varianz>0) delay+= new Random().nextInt((int) _varianz);
            Thread thread = new Thread(_ej = new ExecuteOneJob(delay));
            thread.start();

            _threads.add(thread);

        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private int findAggregatedWeight() {
        int current = 0;
        for (JobDescription jd :_jobs) current += jd.getWeight();
        return current;
    }

    @Override
    public void stop() {
       _ej.stop();
    }

    protected class ExecuteOneJob implements Runnable {

        Logger _log = Logger.getLogger("executor");

        private final long _delay;
        private Random _rnd = new Random();

        public  ExecuteOneJob(long delay) {
            _delay = delay;
        }
        private boolean _finish = false;

        @Override
        public void run()  {
            while (!_finish) {
               int max = getMaxWeight();
                int selector = _rnd.nextInt(max);
                JobDescription j = findWeightedJob(selector);
                if (j != null) {
                    long currentNano = System.nanoTime();

                    JobResult jr = j.getJob().call();
                    j.executed();

                    for ( ProgressCallback cb : _callbacks) {
                        try {
                            cb.callbackProgress(j,jr);
                        }   catch (Throwable t) {}

                    }

                    long usedMs = (System.nanoTime()-currentNano)/1000000;
                    try {
                        if ((_delay-usedMs)>0) Thread.sleep(_delay-usedMs);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }

            }

        }

        private JobDescription findWeightedJob(int selector) {
            int currentWeight = 0;
            for(JobDescription desc : _jobs ) {
                if ( (currentWeight+desc.getWeight()) > selector) {
                   return desc;
                } else currentWeight+=desc.getWeight();
            }
            return null;
        }

        public void stop() {_finish = true;}
    }

    private int getMaxWeight() {
        return _maxWeight;
    }
}

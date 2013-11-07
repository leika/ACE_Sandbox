package de.appdynamics.sandbox.simpleTest.frontend;

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
        final RandomJobExecutor jobExecutor = new RandomJobExecutor(5,700l,100l);
        try {
            jobExecutor.addJob(new SimpleBusinessJob("MySimpleBusinessJob",12,80,20));
            jobExecutor.addJob(new SimpleBusinessJob("MyComplexTask",12,280,20)).setWeight(7);
            jobExecutor.addJob(new SimpleBusinessJob("CRM-FindUser", 12, 280, 20));
            jobExecutor.addJob(new SimpleBusinessJob("CRM-Report",12,280,20));
            jobExecutor.addJob(new TCPClientSimple()).setWeight(100);
            jobExecutor.addJob(new WebserviceHelloWorld()).setWeight(15);

            System.out.println("Starting Client Mockups (5 Threads)");
            System.out.println(jobExecutor.toDebugString());


            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\n"+jobExecutor.toDebugString());
                }
            },20000,30000);


            jobExecutor.addProgressCallback(new ThreadLogCallback());
            jobExecutor.run();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private static class ThreadLogCallback implements ProgressCallback {
        private int _maxLength;
        private Map<Thread,Character> _threadMap = new HashMap<Thread, Character>();
        private int _currentIndex = 0;
        private String _threadMarkers ;
        private int _pos = 0;

        public ThreadLogCallback() {
             this("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,.;:",80);
        }

        public ThreadLogCallback(int maxLength) {
            this();
            _maxLength = maxLength;
        }
        public ThreadLogCallback(String threadMarkers, int maxLength) {
            _threadMarkers = threadMarkers;
            _maxLength = maxLength;
        }

        @Override
        public synchronized void callbackProgress(JobDescription j, JobResult jr) {
            System.out.print(getThreadCharacter());
            if (_pos++ >_maxLength) {
                _pos = _pos%_maxLength;
                System.out.print("\n");
            }
        }

        private synchronized  Character getThreadCharacter() {
            if (_threadMap.containsKey(Thread.currentThread())) return _threadMap.get(Thread.currentThread());
            else {
                _currentIndex++;
                Character c = _threadMarkers.charAt(_currentIndex % _threadMarkers.length());
                _threadMap.put(Thread.currentThread(),c);
                return c;
            }
        }
    }
}

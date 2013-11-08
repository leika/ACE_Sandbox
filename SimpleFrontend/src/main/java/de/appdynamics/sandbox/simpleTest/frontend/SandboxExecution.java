package de.appdynamics.sandbox.simpleTest.frontend;

import com.appdynamics.ace.util.cli.api.api.AbstractCommand;
import com.appdynamics.ace.util.cli.api.api.Command;
import com.appdynamics.ace.util.cli.api.api.CommandException;
import com.appdynamics.ace.util.cli.api.api.OptionWrapper;
import de.appdynamics.ace.framework.jobs.*;
import de.appdynamics.sandbox.simpleTest.frontend.jobs.SimpleBusinessJob;
import org.apache.commons.cli.Option;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 08.11.13
 * Time: 09:59
 * To change this template use File | Settings | File Templates.
 */
public class SandboxExecution extends AbstractCommand {
    public static final String ARG_THREADS = "threads";
    public static final String ARG_DELAY = "delay";
    public static final String ARG_DELAY_VARIANZ = "delayVarianz";
    private final String _title;
    private RandomJobExecutor _execution  = new RandomJobExecutor();

    public SandboxExecution(String title) {
        _title = title;
    }

    @Override
    protected List<Option> getCLIOptionsImpl() {
        ArrayList<Option> opts = new ArrayList<Option>();

        Option o;
        opts.add(o = new Option(ARG_THREADS,true,"Number of parallel Executions."));
        o.setRequired(false);

        opts.add(o = new Option(ARG_DELAY,true,"Delay between Executions."));
        o.setRequired(false);

        opts.add(o = new Option(ARG_DELAY_VARIANZ,true,"Delay Varianz between threads."));
        o.setRequired(false);

        return opts;
    }

    @Override
    protected int executeImpl(OptionWrapper optionWrapper) throws CommandException {
        try {

            updateExecution(optionWrapper);

            System.out.println("Starting Client Mockups (5 Threads)");
            System.out.println(_execution.toDebugString());

            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\n"+_execution.toDebugString());
                }
            },20000,30000);

            _execution.run();

        } catch (Exception e) {
            e.printStackTrace();
            throw new CommandException("Error while execute "+e.getMessage(),e);
        }

        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void updateExecution(OptionWrapper optionWrapper) {

        int threads = Integer.parseInt(optionWrapper.getOptionValue(ARG_THREADS, "5"));
        long delay = Long.parseLong(optionWrapper.getOptionValue(ARG_DELAY, "700"));
        long varianz = Long.parseLong(optionWrapper.getOptionValue(ARG_DELAY_VARIANZ,"100"));

        _execution.setDelay(delay);
        _execution.setNumThreads(threads);
        _execution.setVarianz(varianz);


        _execution.addProgressCallback(new ThreadLogCallback());

    }

    @Override
    public String getName() {
        return _title;
    }


    @Override
    public String getDescription() {
        String msg = "Start Sandbox Profile " + _title + "\nIncludes:\n";

        msg += _execution.toDebugString();

        msg += "-----\n";
        return msg;


    }

    public JobDescription addJob(Job mySimpleBusinessJob) throws Exception {
        return _execution.addJob(mySimpleBusinessJob);  //To change body of created methods use File | Settings | File Templates.
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

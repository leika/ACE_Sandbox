package de.appdynamics.ace.framework.jobs.test;

import de.appdynamics.ace.framework.jobs.Job;
import de.appdynamics.ace.framework.jobs.JobExecutor;
import de.appdynamics.ace.framework.jobs.RandomJobExecutor;
import org.testng.TestListenerAdapter;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;
import static org.testng.Reporter.*;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 11:27
 * To change this template use File | Settings | File Templates.
 */
public class SimpleExecutorTest  {
    private JobExecutor _executor = new RandomJobExecutor();
    private int _count = 0;

    public void setupExecutor(){
        _executor = new RandomJobExecutor();
    }

    @Test
    public void testSetupSimple() {
        setupExecutor();
        assertEquals(_executor.getAllJobs().size(),0);
        log("Setup successful");
        log(_executor.toDebugString());

    }

    @Test
    public void testSimple2() throws Exception {
        setupExecutor();
        _executor.addJob(new Job(){

            @Override
            protected String callJob() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getName() {
                return "myTestJob";  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        assertEquals(_executor.getAllJobs().size(),1);
        assertEquals(_executor.toDebugString().contains("myTestJob"), true);

        log("Setup successful");
        log(_executor.toDebugString());

    }


    @Test
    public void executeSimple() throws Exception {
        setupExecutor();
        _executor.addJob(new Job(){

            @Override
            protected String callJob() {
                _count++;
                return "TRUE";  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getName() {
                return "myTestJob";  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        assertEquals(_executor.getAllJobs().size(),1);
        assertEquals (_executor.toDebugString().contains("myTestJob"),true);

        _executor.run();
        Thread.sleep(5000);
        _executor.stop();
        Thread.sleep(5000);

        assertEquals(_count,3);

        assertFalse(_executor.isRunning());
        log("Setup successful");
        log(_executor.toDebugString());
    }



}

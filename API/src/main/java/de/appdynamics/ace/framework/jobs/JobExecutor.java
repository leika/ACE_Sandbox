package de.appdynamics.ace.framework.jobs;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 11:03
 * To change this template use File | Settings | File Templates.
 */
public interface JobExecutor {

    public List<JobDescription> getAllJobs();
    public String toDebugString();

    public JobDescription addJob(Job job) throws Exception;
    public JobDescription addJob(Class<Job> job) throws Exception;
    public JobDescription addJob(String job) throws Exception;

    public void run();
    public void stop();
    public boolean isRunning();


    void addProgressCallback(ProgressCallback progressCallback);
}

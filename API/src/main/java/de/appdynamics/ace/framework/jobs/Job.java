package de.appdynamics.ace.framework.jobs;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public abstract class Job implements Callable<JobResult> {

    ThreadLocal<JobResult> _localResult = new ThreadLocal<JobResult>();

    public JobResult call()  {
         try {
             _localResult.set(new JobResult());
             _localResult.get().addMessage(callJob());
             _localResult.get().stopTimer();
         } catch (Exception ex) {
             ex.printStackTrace();
         } finally {
             JobResult result = _localResult.get();
           _localResult.remove();
             return result;
         }

    }

    protected abstract String callJob();


    public abstract String getName();
}

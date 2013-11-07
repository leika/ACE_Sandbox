package de.appdynamics.ace.framework.jobs;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 07.11.13
 * Time: 09:06
 * To change this template use File | Settings | File Templates.
 */
public interface ProgressCallback {
    void callbackProgress(JobDescription j, JobResult jr);
}

package de.appdynamics.ace.framework.jobs;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 07.11.13
 * Time: 09:11
 * To change this template use File | Settings | File Templates.
 */
public class LogCallback implements ProgressCallback {
    Logger _log = Logger.getLogger("executor");
    private Level _level;


    public LogCallback() {
           this(Level.INFO);
    }
    public LogCallback(Level level) {

        _level = level;
    }

    public void callbackProgress(JobDescription j, JobResult jr) {
        _log.log(_level,"Job Executed :"+j+" ==>"+jr);
    }
}

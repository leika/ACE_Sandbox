package de.appdynamics.ace.framework.jobs;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 11:06
 * To change this template use File | Settings | File Templates.
 */
public class JobResult {
    private String _message;

    private long _jobTimerStart = -1;
    private long _jobTimerEnd = -1;

    public JobResult() {
        startTimer();
    }

    public void startTimer() {
        _jobTimerStart = System.nanoTime();
    }
    public void stopTimer() {
        _jobTimerEnd = System.nanoTime();
    }

    public boolean isFinished() {
        return (_jobTimerEnd != -1 && _jobTimerEnd > _jobTimerStart);
    }

    public long getDurationMilliseconds() {
        return (isFinished())?(_jobTimerEnd-_jobTimerStart)/1000000:-1;
    }

    public void addMessage(String msg) {
        _message = msg;
    }

    public String toString() {
        return "["+getDurationMilliseconds()+"ms ] "+getMessage();
    }

    public String getMessage() {
        return _message;
    }
}

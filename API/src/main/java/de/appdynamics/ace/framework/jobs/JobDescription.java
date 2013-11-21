package de.appdynamics.ace.framework.jobs;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public class JobDescription {
    private int _weight = 1;
    private Job _job;
    private int _count;
    private double _averageTime;

    public JobDescription(Job job, int i) {
        _job = job;
        _weight = i;
    }

    public int getExcecutionCount() {
        return _count;
    }

    public synchronized void executed() {
        _count++;
    }


    public Job getJob() {
        return _job;
    }

    public int getWeight() {
        return _weight;
    }

    public void setWeight(int weight) {
        _weight = weight;
    }

    public String toString() {
        return _job.getName() + "[W:"+getWeight()+"]  Executed "+getExcecutionCount()+" times";
    }

    public void setAverageTime(double averageTime) {
        _averageTime = averageTime;
    }

    public double getAverageTime() {
        return _averageTime;
    }

    public void executed(long usedMs) {

        if (_count == 0) setAverageTime(usedMs);
        else {
            double totalTime = (getAverageTime()*_count) + usedMs;
            setAverageTime(totalTime/(_count +1));
        }
        executed();


    }
}

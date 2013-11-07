package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import de.appdynamics.ace.framework.jobs.Job;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public class SimpleBusinessJob extends Job {
    private final String _name;
    private final BusinessCalltree _tree;

    public SimpleBusinessJob(String name, int depth, int avgDuration, int variation) {
        super();
        _name = name;
        _tree = new BusinessCalltree(depth,avgDuration,variation);
    }

    @Override
    protected String callJob() {
        _tree.callMyBusiness();
        return "finished";
    }

    @Override
    public String getName() {
        return _name;
    }
}

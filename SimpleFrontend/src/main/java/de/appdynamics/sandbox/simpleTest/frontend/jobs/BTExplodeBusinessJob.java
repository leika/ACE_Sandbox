package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import de.appdynamics.sandbox.simpleTest.frontend.jobs.SimpleBusinessJob;

/**
 * Created by stefan.marx on 10.06.14.
 */
public class BTExplodeBusinessJob extends SimpleBusinessJob {
    private final Callback _cb;

    public BTExplodeBusinessJob(String s, int i, int i1, int i2, Callback cb) {

        super(s,i,i1,i2);
        _cb = cb;
    }

    @Override
    public String getName() {
        return _cb.getNamePrefix()+"  Jobname="+super.getName();
    }

    public interface Callback {
        public String getNamePrefix() ;
    }
}

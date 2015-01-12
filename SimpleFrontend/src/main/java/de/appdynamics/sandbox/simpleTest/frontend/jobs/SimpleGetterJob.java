package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import de.appdynamics.ace.framework.jobs.Job;
import de.appdynamics.sandbox.simpleTest.frontend.jobs.SimpleBusinessJob;

/**
 * Created by stefan.marx on 05.11.14.
 */
public class SimpleGetterJob extends SimpleBusinessJob {
    public SimpleGetterJob(String s, int i, int i1, int i2) {
        super(s,i,i1,i2);
    }

    public String getSimpleTest() {
        return "simple";
    }

    public String getComplexName(String pre, String post) {
         return "TEST Pre:"+pre+" .. Post:"+post+":";
    }

}

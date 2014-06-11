package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import java.util.Random;

/**
 * Created by stefan.marx on 10.06.14.
 */
public class RandomNamePrefix implements BTExplodeBusinessJob.Callback {

    private final int _size;
    Random r = new Random();
    public RandomNamePrefix(int i) {
        _size = i;
    }

    @Override
    public String getNamePrefix() {
        return "ID:"+r.nextInt(_size);
    }
}

package de.appdynamics.sandbox.simpleTest.frontend.jobs;

/**
 * Created by stefan.marx on 22.07.14.
 */
public class MyComplexException extends Throwable {
    public MyComplexException(String s) {
        super (s);
    }

    public MyComplexException(String s, MySimpleException e) {
        super (s,e);

    }
}

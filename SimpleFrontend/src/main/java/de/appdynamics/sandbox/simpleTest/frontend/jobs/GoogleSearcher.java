package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 08.11.13
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */
public class GoogleSearcher {
    public static String searchGoogle(String s, String search) {
        try {
            Thread.sleep(94);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return new Date().toString();
    }
}

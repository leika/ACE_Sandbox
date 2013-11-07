package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
public class BusinessCalltree {

    private final int _depth;
    private final int _avg;
    private final int _vari;

    private int _currentDepth = 0;

    private Random _rnd = new Random();

    public BusinessCalltree(int depth, int avgDuration, int variation) {
        _depth = depth/2+_rnd.nextInt(depth/2);
        _avg = avgDuration;
        _vari = variation;
    }

    public void  callMyBusiness() {
        doBusiness();
        doSome();
    }

    private void doSome() {
        _currentDepth++;
        if (_currentDepth <= _depth) {
            switch (_rnd.nextInt(10)) {
                case 0: makeSomeNoise();
                    break;
                case 1: followRabbit();
                    break;
                case 2:
                case 3:
                case 4:  createDBUser();
                    break;
                case 5:  notMyProblemField();
                    break;
                case 6:
                case 7:
                case 8:
                case 9:  monitorYourApps();
                    break;
            }
            
        }
                
    }

    private void monitorYourApps() {
        doBusiness();
        doSome();

    }

    private void notMyProblemField() {
        doBusiness();
        try { Thread.sleep(50); } catch (InterruptedException e) {   }
        doSome();

    }

    private void createDBUser() {
        doBusiness();
        doSome();

    }

    private void followRabbit() {
        doBusiness();
        doSome();

    }

    private void makeSomeNoise() {
        try { Thread.sleep(50); } catch (InterruptedException e) {   }
        doBusiness();
        doSome();
    }

    private void doBusiness() {
        int i = _rnd.nextInt(_vari*2);
        try {
            Thread.sleep(_avg-_vari+i);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return;

    }
}

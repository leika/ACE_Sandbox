package de.appdynamics.ace.sandbox.tcpBackend.api;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 23:49
 * To change this template use File | Settings | File Templates.
 */
public enum ResultCode {
    OK(0,"OK"),
    FAIL(1,"FAIL"),
    DELAY(3,"DELAY");




    private final int _code;

    private final String _name;


    public String getName() {
        return _name;
    }
    public int getCode() {
        return _code;
    }


    ResultCode(int i, String nameValue) {
        _code = i;
        _name = nameValue;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)",getName(),getCode());
    }
}

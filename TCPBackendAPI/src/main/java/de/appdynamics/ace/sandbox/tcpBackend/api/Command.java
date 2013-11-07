package de.appdynamics.ace.sandbox.tcpBackend.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 23:48
 * To change this template use File | Settings | File Templates.
 */
public class Command {

    public String getCorrelation() {
        return correlation;
    }

    public void setCorrelation(String correlation) {
        this.correlation = correlation;
    }

    private String correlation;

    public Command() {}

    public String getCmd() {
        return _cmd;
    }

    public void setCmd(String cmd) {
        _cmd = cmd;
    }

    public Map<String, String> getArgs() {
        return _args;
    }

    public void setArgs(Map<String, String> args) {
        _args = args;
    }

    public Map<String, String> getContext() {
        return _context;
    }

    public void setContext(Map<String, String> context) {
        _context = context;
    }

    private  String _cmd;
    private  Map<String, String> _args;
    private  Map<String, String> _context;

    public Command(String command, Map<String,String> arguments) {
        _cmd = command;
        _args = arguments;
        _context = new HashMap<String,String>();
    }

    public void addContext(String k, String v) {
        _context.put(k,v);
        System.out.println(" !!!! Added to context : "+k+ "  -->  "+v);
    }

}

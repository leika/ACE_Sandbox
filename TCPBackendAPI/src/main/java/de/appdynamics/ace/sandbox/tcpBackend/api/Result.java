package de.appdynamics.ace.sandbox.tcpBackend.api;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 23:48
 * To change this template use File | Settings | File Templates.
 */
public class Result {

    private String _message;
    private ResultCode _resultCode;

    public Result() {}

    public Result(ResultCode code, String message) {
        _resultCode = code;
        _message = message;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }

    public ResultCode getResultCode() {
        return _resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        _resultCode = resultCode;
    }

    @Override
    public String toString() {
        return String.format("%s  :== %s",getResultCode().toString(),getMessage());
    }
}

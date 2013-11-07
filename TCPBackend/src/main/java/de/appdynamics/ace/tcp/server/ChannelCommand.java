package de.appdynamics.ace.tcp.server;

import de.appdynamics.ace.sandbox.tcpBackend.api.Command;
import de.appdynamics.ace.sandbox.tcpBackend.api.Result;
import de.appdynamics.ace.sandbox.tcpBackend.api.ResultCode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 06.11.13
 * Time: 00:32
 * To change this template use File | Settings | File Templates.
 */
public class ChannelCommand implements Runnable {
    private final ObjectMapper _mapper;
    private final Socket _socket;


    public ChannelCommand(Socket clientSocket) {
        _socket = clientSocket;
        _mapper = new ObjectMapper();
        _mapper.writerWithDefaultPrettyPrinter();
    }

    @Override
    public void run() {
        try {
            BufferedReader r = new BufferedReader( new InputStreamReader(_socket.getInputStream()));
            OutputStreamWriter w = new OutputStreamWriter(_socket.getOutputStream()) ;

            String l = "";
            StringBuffer command = new StringBuffer();
            do {
                l = r.readLine();
                System.out.println(":::"+l);
                if (!l.equals("!!!END")) command.append(l).append("\n");
            } while (!l.equals("!!!END"));

            Command c = _mapper.readValue(command.toString(),Command.class);

            System.out.println("Command Received :: "+command);
            try {
                Thread.sleep(90);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Result t = ComandExecutor.executeCommand(c);


            _mapper.writeValue(_socket.getOutputStream(),t);
            _socket.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void run2() {
        try {
            Command c = _mapper.readValue(_socket.getInputStream(),Command.class);
            String s = _mapper.writerWithDefaultPrettyPrinter().writeValueAsString(c);
            System.out.println(s+"\n-----------");


            Result t = new Result(ResultCode.OK,"NOPE");
            _mapper.writeValue(_socket.getOutputStream(),t);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}

package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import de.appdynamics.ace.framework.jobs.Job;
import de.appdynamics.ace.sandbox.tcpBackend.api.Command;
import de.appdynamics.ace.sandbox.tcpBackend.api.Result;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 06.11.13
 * Time: 10:42
 * To change this template use File | Settings | File Templates.
 */
public class TCPClientSimple extends Job {

    private final String _hostname;
    private final int _port;
    private String _command = "MASTER";
    private int _targetPort = 8989;

    public TCPClientSimple(String s, int i) {
        address = s+":"+i;
        _port = i;
        _hostname = s;

    }

    public TCPClientSimple(String host, int port, String command, int targetPort) {
          this(host,port);
          _command = command;
        _targetPort = targetPort;

    }

    public String getAddress() {
        return address;
    }

    private String address = "localhost:8989";

    @Override
    protected String callJob() {

        Map<String, String> map = new HashMap<String, String>();
        try {
            Thread.sleep(21);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.put("localFile","NOPE");
        map.put("currentDate",new Date().toString());
        map.put("targetPort",""+_targetPort);
        Command m = new Command(_command, map);
        return communicate(m);

    }

    private String communicate(Command m) {
        try {
            Thread.sleep(12);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();


        try {
            String json = writer.writeValueAsString(m);

            Socket socket = new Socket(_hostname,_port);
            OutputStream out = socket.getOutputStream();
            String s = writer.writeValueAsString(m);

            OutputStreamWriter ow = new OutputStreamWriter(out);

            ow.write(s);
            ow.write("\n");
            ow.write("!!!END");
            ow.write("\n");
            ow.flush();


            //out.close();


            InputStream in = socket.getInputStream();
            Result r = mapper.readValue(in,Result.class);

            return r.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "TCPSimpleBackend";  //To change body of implemented methods use File | Settings | File Templates.
    }
}

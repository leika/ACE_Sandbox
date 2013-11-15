package de.appdynamics.ace.tcp.server;

import de.appdynamics.ace.sandbox.tcpBackend.api.Command;
import de.appdynamics.ace.sandbox.tcpBackend.api.Result;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 15.11.13
 * Time: 08:04
 * To change this template use File | Settings | File Templates.
 */
public class BackendConnector {
    private final int _port;
    private final String _host;
    private String _endpoint;


    public BackendConnector(String host, String targetPort) {
        _host = host;
        _port = Integer.parseInt(targetPort);
        setEndpoint(host+":"+targetPort);

    }

    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public String getAddress() { return getEndpoint();}

    public void callBackend(Command c) {


        Map<String, String> args = new HashMap<String, String>();
        args.put("MIKE",new Date().toString());
        Command c2 = new Command("MASTER", args);

        c2.setCorrelation(c.getCorrelation()) ;
        try {
            communicate(c2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String communicate(Command m) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();


        try {
            String json = writer.writeValueAsString(m);

            Socket socket = new Socket(_host,_port);
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
}

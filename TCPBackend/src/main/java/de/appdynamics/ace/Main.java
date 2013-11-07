package de.appdynamics.ace;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 23:40
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        Command m = new Command("MASTER", System.getenv());

        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

        try {
            String json = writer.writeValueAsString(m);

            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host,8989);
            OutputStream out = socket.getOutputStream();
            String s = writer.writeValueAsString(m);

            OutputStreamWriter ow = new OutputStreamWriter(out);
            System.out.println("SENDING"+s);
            ow.write(s);
            ow.write("\n");
            ow.write("!!!END");
            ow.write("\n");
            ow.flush();


            //out.close();


            InputStream in = socket.getInputStream();
            Result r = mapper.readValue(in,Result.class);
            System.out.println("::"+r);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

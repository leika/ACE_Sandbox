package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import de.appdynamics.ace.framework.jobs.Job;
import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpExchange;

import java.io.IOException;
import java.util.Random;


/**
 * Created by stefan.marx on 22.01.14.
 */
public class JettyWebserviceWorld extends Job {
    private final HttpClient _client;
    private final Random _rnd;

    public JettyWebserviceWorld() throws Exception {
        _client  = new HttpClient();

// Start HttpClient
        _client.start();
        _rnd = new Random(System.currentTimeMillis());
    }

    @Override
    protected String callJob() {

        ContentExchange exchange = new ContentExchange(true);
        switch (_rnd.nextInt(2)) {
            case 0:
                exchange.setURL("http://localhost:4080/BackendServices/rest/ace/hello/Mike");
                break;
            case 1:
                exchange.setURL("http://127.0.0.1:4080/BackendServices/rest/ace/hello/Mike");
                break;
            default:
                exchange.setURL("http://localhost:4080/BackendServices/rest/ace/hello/Mike");
                break;
        }

        try {

            exchange.setMethod("GET");

            _client.send(exchange);
            exchange.waitForDone();

            System.out.println("A:"+exchange.getAddress());
            System.out.println("R:" + exchange.getStatus());
            System.out.println("A:"+exchange.getResponseContent());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getName() {
        return "Jetty Call!";
    }
}

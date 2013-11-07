package de.appdynamics.sandbox.simpleTest.frontend.jobs;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import de.appdynamics.ace.framework.jobs.Job;

import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public class WebserviceHelloWorld extends Job {
    private WebResource _restClient;

    @Override
    protected String callJob() {
        _restClient  = createClient();
        String response = _restClient.path("ace/hello")
                .path("world")
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);
        return "Converted To "+response;
    }

    @Override
    public String getName() {
        return "Hello World Webservice";
    }

    public WebResource createClient() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put("com.sun.jersey.api.json.POJOMappingFeature", Boolean.TRUE);
        Client client = Client.create(clientConfig);
        //client.addFilter(new HTTPBasicAuthFilter(this._username, this._password));
        WebResource webResource = client.resource("http://localhost:4080/BackendServices/rest/");
        return webResource;
    }
}

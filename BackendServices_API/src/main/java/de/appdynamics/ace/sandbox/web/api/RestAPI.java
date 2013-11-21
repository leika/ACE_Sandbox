package de.appdynamics.ace.sandbox.web.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 20.11.13
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */
@Path("ace")
public interface RestAPI {
    @GET
    @Path("hello/{world}")
    String helloWorld(@PathParam("world") String hello);
}

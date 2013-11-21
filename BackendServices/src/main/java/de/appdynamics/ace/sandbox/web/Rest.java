package de.appdynamics.ace.sandbox.web;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 05.11.13
 * Time: 15:05
 * To change this template use File | Settings | File Templates.
 */


@Path("ace")
public class Rest  {

    @GET
    @Path("hello/{world}")
    public String helloWorld(String hello) {
        try {
            Thread.sleep(59);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello "+hello.toUpperCase();
    }
}

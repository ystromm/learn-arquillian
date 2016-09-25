package com.github.ystromm.learn_arquillian.message;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/message")
public class MessageResource {

    @GET
    @Path("/{param}")
    public Response printMessage(@PathParam("param") String msg) {

        final String result = "Restful example : " + msg;

        return Response.status(200).entity(result).build();

    }

}
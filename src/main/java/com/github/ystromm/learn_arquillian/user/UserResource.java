package com.github.ystromm.learn_arquillian.user;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/")
public class UserResource {

    @Path("/authenticate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(User user) {
        return Response.status(401).build();
    }

    @Path("/user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<User> getUsers() {
        return Collections.singletonList(new User(1, "user"));
    }
}

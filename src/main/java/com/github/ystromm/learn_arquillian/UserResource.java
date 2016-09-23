package com.github.ystromm.learn_arquillian;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class UserResource {

    @Path("/users/authenticate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(User user) {
        return Response.status(401).build();
    }

    List<User> getUsers() {
        return Collections.emptyList();

    }
}

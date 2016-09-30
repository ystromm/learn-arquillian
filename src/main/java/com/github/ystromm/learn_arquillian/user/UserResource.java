package com.github.ystromm.learn_arquillian.user;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/user")
public interface UserResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Collection<User> getUsers();

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    User getUser(@PathParam("id") long id);
}

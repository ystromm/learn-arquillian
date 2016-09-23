package com.github.ystromm.learn_arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class MessageResourceTest {
    @Deployment
    public static WebArchive createDeployment()
    {
        return ShrinkWrap
                .create(WebArchive.class)
                .addPackages(true, Filters.exclude(".*Test.*"),
                        UserResource.class.getPackage())
        .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @RunAsClient
    public void authenticateUser(
            @ArquillianResteasyResource final WebTarget webTarget)
    {
        final Response response = webTarget
                .path("/users/authenticate")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(new User(
                        1l,
                        "mypassword")));
    }

}
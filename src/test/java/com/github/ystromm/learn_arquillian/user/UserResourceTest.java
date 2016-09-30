package com.github.ystromm.learn_arquillian.user;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;

@RunAsClient
@RunWith(Arquillian.class)
public class UserResourceTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addPackages(true, Filters.exclude(".*Test.*"),
                        UserResource.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void authenticate_should_return_401(@ArquillianResteasyResource WebTarget webTarget) {
        assertThat(webTarget.path("/authenticate").request().post(Entity.json(new User(1, "nameValue"))).getStatus(),
        equalTo(Response.Status.UNAUTHORIZED.getStatusCode()));
    }

    @Test
    public void get_users_should_return_empty(@ArquillianResteasyResource WebTarget webTarget) {
        assertThat(webTarget.path("/user").request().get(new GenericType<Collection<User>>() {
        }), empty());
    }
}
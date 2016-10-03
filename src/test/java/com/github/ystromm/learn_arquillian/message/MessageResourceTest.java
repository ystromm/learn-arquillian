package com.github.ystromm.learn_arquillian.message;

import com.github.ystromm.learn_arquillian.JaxRsActivator;
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

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.WebTarget;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunAsClient
@RunWith(Arquillian.class)
public class MessageResourceTest {

    public static final String MARTIAN = "Martian";

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addClass(JaxRsActivator.class)
                .addPackages(true, Filters.exclude(".*Test.*"),
                        MessageResource.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test(expected = NotFoundException.class)
    public void get_should_return_message(@ArquillianResteasyResource WebTarget webTarget) {
        webTarget.path("/message/").request().get(String.class);
    }

    @Test
    public void get_with_param_should_return_message(@ArquillianResteasyResource WebTarget webTarget) {
        assertThat(webTarget.path("/message/" + MARTIAN).request().get(String.class), equalTo("Restful example : " +
                MARTIAN));
    }
}
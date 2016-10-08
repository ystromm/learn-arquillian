package com.github.ystromm.learn_arquillian.user;

import com.github.ystromm.learn_arquillian.JaxRsActivator;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.WebTarget;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(Arquillian.class)
public class UserResourceRestAssuredTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "user.war")
                .addClass(JaxRsActivator.class)
                .addPackages(true, Filters.exclude(".*Test.*"),
                        UserResourceImpl.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void get_gurka_should_return_404(@ArquillianResteasyResource WebTarget webTarget) {
        givenWebTarget(webTarget).when().get("/gurka").then().statusCode(404);
    }

    @Test
    public void get_user_1_should_return_knatte(@ArquillianResteasyResource WebTarget webTarget) {
        givenWebTarget(webTarget).when().get("/user/{id}", 1).then().body("name", equalTo("knatte"));
    }

    @Test
    public void get_user_should_return_knatte_fnatte_and_tjatte(@ArquillianResteasyResource WebTarget webTarget) {
        givenWebTarget(webTarget).when().get("/user").then().body("name", containsInAnyOrder("knatte", "fnatte", "tjatte"));
    }


    private RequestSpecification givenWebTarget(@ArquillianResteasyResource WebTarget webTarget) {
        return given().baseUri(webTarget.getUri().toString());
    }
}

package com.github.ystromm.learn_arquillian.user;

import com.github.ystromm.learn_arquillian.JaxRsActivator;
import org.jboss.arquillian.container.test.api.Deployment;
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

import static com.github.ystromm.learn_arquillian.user.User.user;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Arquillian.class)
public class UserResourceTest {

    public static final User KNATTE = user(1l, "knatte");
    public static final User FNATTE = user(2l, "fnatte");
    public static final User TJATTE = user(3l, "tjatte");

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
    public void get_users_should_return_KNATTE_FNATTE_TJATTE(@ArquillianResteasyResource UserResource userResource) {
        assertThat(userResource.getUsers(), containsInAnyOrder(KNATTE, FNATTE, TJATTE));
    }

    @Test
    public void get_user_1_should_return_KNATTE(@ArquillianResteasyResource WebTarget webTarget) {
        assertThat(webTarget.path("/user/1").request().get(User.class), equalTo(KNATTE));
    }

    @Test(expected = NotFoundException.class)
    public void get_user_4_should_return_not_found(@ArquillianResteasyResource WebTarget webTarget) {
        webTarget.path("/user/4").request().get(User.class);
    }
}
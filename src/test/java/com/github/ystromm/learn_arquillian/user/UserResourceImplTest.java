package com.github.ystromm.learn_arquillian.user;

import com.github.ystromm.learn_arquillian.JaxRsActivator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import static com.github.ystromm.learn_arquillian.user.User.user;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Arquillian.class)
public class UserResourceImplTest {
    private static final User KNATTE = user(1l, "knatte");
    private static final User FNATTE = user(2l, "fnatte");
    private static final User TJATTE = user(3l, "tjatte");

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addClass(JaxRsActivator.class)
                .addPackages(true, Filters.exclude(".*Test.*"),
                        UserResourceImpl.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private UserResourceImpl userResource;

    @Test
    public void getUsers_should_return_KNATTE_FNATTE_and_TJATTE() {
        assertThat(userResource.getUsers(), containsInAnyOrder(KNATTE, FNATTE, TJATTE));
    }

    @Test
    public void getUser_should_return_KNATTE() {
        assertThat(userResource.getUser(KNATTE.getId()), equalTo(KNATTE));
    }

    @Test(expected = NotFoundException.class)
    public void getUser_should_throw() {
        userResource.getUser(42l);
    }
}
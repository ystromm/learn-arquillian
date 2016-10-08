package com.github.ystromm.learn_arquillian.user;

import com.github.ystromm.learn_arquillian.JaxRsActivator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.spi.ArquillianProxyException;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static com.github.ystromm.learn_arquillian.user.User.user;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Arquillian.class)
public class UserResourceImplTest {
    static final User KNATTE = user(1l, "knatte");
    private static final User FNATTE = user(2l, "fnatte");
    private static final User TJATTE = user(3l, "tjatte");

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addAsLibraries(Maven.resolver().resolve("com.google.guava:guava:19.0").withTransitivity().asFile())
                .addClass(JaxRsActivator.class)
                .addPackages(true, Filters.exclude(".*Test.*"),UserResourceImpl.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private UserResourceImpl userResourceImpl;

    @Test
    public void getUsers_should_return_KNATTE_FNATTE_and_TJATTE() {
        assertThat(userResourceImpl.getUsers(), containsInAnyOrder(KNATTE, FNATTE, TJATTE));
    }

    @Test
    public void getUser_should_return_KNATTE() {
        assertThat(userResourceImpl.getUser(KNATTE.getId()), equalTo(KNATTE));
    }

    // Arquillian wraps WebApplicationException because they are not serializable
    @Test(expected = ArquillianProxyException.class)
    public void getUser_should_throw() {
        userResourceImpl.getUser(42l);
    }
}
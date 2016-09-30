package com.github.ystromm.learn_arquillian.user;

import com.google.common.collect.ImmutableMap;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static com.github.ystromm.learn_arquillian.user.User.user;


public class UserResourceImpl implements UserResource {
    public static final User KNATTE = user(1l, "knatte");
    public static final User FNATTE = user(2l, "fnatte");
    public static final User TJATTE = user(3l, "tjatte");
    private final Map<Long, User> users = ImmutableMap.of(KNATTE.getId(), KNATTE, FNATTE.getId(), FNATTE, TJATTE.getId(), TJATTE);

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public User getUser(long id) {
        final Optional<User> user = Optional.ofNullable(users.get(id));
        return user.orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
}

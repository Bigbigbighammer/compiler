package mapper.impl;

import domain.User;
import mapper.UserMapper;

import java.util.HashMap;
import java.util.Map;

public class DefaultUserMapper implements UserMapper {

    private final Map<String, User> userTable = new HashMap<>();

    @Override
    public void save(User user) {
        userTable.put(user.getName(), user);
    }

    @Override
    public User findByName(String name) {
        return userTable.get(name);
    }
}

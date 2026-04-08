package mapper.impl;

import domain.User;
import mapper.UserMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户数据访问的默认实现。
 * <p>
 * 使用内存中的 HashMap 存储用户数据，适用于单机演示。
 * 可替换为数据库实现。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class DefaultUserMapper implements UserMapper {

    /** 用户表，键为用户名，值为用户对象 */
    private final Map<String, User> userTable = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(User user) {
        userTable.put(user.getName(), user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByName(String name) {
        return userTable.get(name);
    }
}

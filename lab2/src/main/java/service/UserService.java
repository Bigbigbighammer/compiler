package service;

import domain.User;
import exception.AuthFailedException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import mapper.UserMapper;

/**
 * 用户服务类。
 * <p>
 * 提供用户注册、查询、认证等业务逻辑。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class UserService {

    private final UserMapper userMapper;

    /**
     * 构造用户服务。
     *
     * @param userMapper 用户数据访问对象
     */
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 添加新用户。
     * <p>
     * 如果用户名已存在，则抛出 {@link UserAlreadyExistsException}。
     * </p>
     *
     * @param name     用户名
     * @param password 密码
     * @throws UserAlreadyExistsException 如果用户名已存在
     */
    public void addUser(String name, String password) {
        User user = userMapper.findByName(name);
        if (user != null) {
            throw new UserAlreadyExistsException(user.getName());
        }
        user = new User(name, password);
        userMapper.save(user);
    }

    /**
     * 根据用户名查找用户。
     *
     * @param name 用户名
     * @return 用户对象，未找到则返回 null
     */
    public User findUser(String name) {
        return userMapper.findByName(name);
    }

    /**
     * 用户认证。
     * <p>
     * 验证用户名和密码是否正确。
     * 如果用户不存在，抛出 {@link UserNotFoundException}。
     * 如果密码错误，抛出 {@link AuthFailedException}。
     * </p>
     *
     * @param name     用户名
     * @param password 密码
     * @return 认证成功的用户对象
     * @throws UserNotFoundException 如果用户不存在
     * @throws AuthFailedException   如果密码错误
     */
    public User authorize(String name, String password) {
        User user = userMapper.findByName(name);
        if (user == null) {
            throw new UserNotFoundException(name);
        }
        if (!user.getPassword().equals(password)) {
            throw new AuthFailedException();
        }
        return user;
    }
}

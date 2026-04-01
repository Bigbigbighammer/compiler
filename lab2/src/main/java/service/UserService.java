package service;

import domain.User;
import exception.AuthFailedException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import mapper.UserMapper;

public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void addUser(String name, String password) {
        User user = userMapper.findByName(name);
        if (user != null) {
            throw new UserAlreadyExistsException(user.getName());
        }
        user = new User(name, password);
        userMapper.save(user);
    }

    public User findUser(String name) {
        return userMapper.findByName(name);
    }

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

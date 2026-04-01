package mapper;

import domain.User;

public interface UserMapper {

    void save(User user);

    User findByName(String name);
}

package mapper;

import domain.User;

/**
 * 用户数据访问接口。
 * <p>
 * 提供用户的持久化操作，包括保存和查询。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public interface UserMapper {

    /**
     * 保存用户。
     *
     * @param user 要保存的用户对象
     */
    void save(User user);

    /**
     * 根据用户名查找用户。
     *
     * @param name 用户名
     * @return 找到的用户对象，未找到则返回 null
     */
    User findByName(String name);
}

package domain;

/**
 * 用户实体类，表示系统中的注册用户。
 * <p>
 * 用户可以通过用户名和密码进行注册和登录，
 * 并可以创建和管理会议。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class User {

    /** 用户名，唯一标识用户 */
    private String name;

    /** 用户密码 */
    private String password;

    /**
     * 默认构造函数。
     */
    public User() {
    }

    /**
     * 带参数的构造函数。
     *
     * @param name     用户名
     * @param password 密码
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * 获取用户名。
     *
     * @return 用户名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户名。
     *
     * @param name 用户名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取密码。
     *
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码。
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

package top.zetiny.vuemusicproject.dao;

/**
 * 管理员 Dao 层
 */
public interface AdminMapper {
    /**
     * 验证密码是否正确
     * @param username
     * @param password
     * @return
     */
    int verifyPassword(String username, String password);
}

package top.zetiny.vuemusicproject.service;

import org.apache.ibatis.annotations.Param;

public interface AdminService {
    boolean verifyPassword(@Param("username") String username, @Param("password") String password);
}

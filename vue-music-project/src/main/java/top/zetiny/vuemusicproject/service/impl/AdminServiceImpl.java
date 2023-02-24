package top.zetiny.vuemusicproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zetiny.vuemusicproject.dao.AdminMapper;
import top.zetiny.vuemusicproject.service.AdminService;

/**
 * 管理员 Service 层
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 验证用户登录密码是否有效
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean verifyPassword(String username, String password) {
        return adminMapper.verifyPassword(username, password) > 0;
    }


}

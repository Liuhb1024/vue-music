package top.zetiny.vuemusicproject.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.zetiny.vuemusicproject.VueMusicProjectApplication;
import top.zetiny.vuemusicproject.dao.AdminMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VueMusicProjectApplication.class)
class AdminServiceImplTest {

    @Autowired
    private AdminMapper adminMapper;

    @Test
    void verifyPassword() {
        System.out.println(adminMapper.verifyPassword("admin", "123"));
    }
}
package top.zetiny.vuemusicproject.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.zetiny.vuemusicproject.bean.Consumer;
import top.zetiny.vuemusicproject.dao.ConsumerMapper;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ConsumerServiceTest {
    @Autowired
    private ConsumerMapper consumerMapper;

    @Test
    void verifyPassword() {
        Consumer consumer = consumerMapper.verifyPassword("", "18884369734", "123456");
        System.out.println("测试结果："+ consumer);
    }
}
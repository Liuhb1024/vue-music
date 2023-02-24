package top.zetiny.vuemusicproject.service;

import top.zetiny.vuemusicproject.bean.Consumer;

import java.util.List;

public interface ConsumerService {
    List<Consumer> queryConsumerById(Integer id);

    List<Consumer> queryConsumerByUsername(String username);

    List<Consumer> queryAllConsumers();

    int addConsumer(Consumer consumer);

    int modifyConsumer(Consumer consumer);

    int removeConsumer(Integer id);

    Consumer verifyPassword(String username, String phoneNum, String password);
}

package top.zetiny.vuemusicproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zetiny.vuemusicproject.bean.Consumer;
import top.zetiny.vuemusicproject.dao.ConsumerMapper;
import top.zetiny.vuemusicproject.service.ConsumerService;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Override
    public List<Consumer> queryConsumerById(Integer id) {
        return consumerMapper.selectById(id);
    }

    @Override
    public List<Consumer> queryConsumerByUsername(String username) {
        return consumerMapper.selectByUsername(username);
    }

    @Override
    public List<Consumer> queryAllConsumers() {
        return consumerMapper.selectAll();
    }

    @Override
    public int addConsumer(Consumer consumer) {
        return consumerMapper.insert(consumer);
    }

    @Override
    public int modifyConsumer(Consumer consumer) {
        return consumerMapper.update(consumer);
    }

    @Override
    public int removeConsumer(Integer id) {
        return consumerMapper.delete(id);
    }

    @Override
    public Consumer verifyPassword(String username, String phoneNum, String password) {
        return consumerMapper.verifyPassword(username, phoneNum, password);
    }
}

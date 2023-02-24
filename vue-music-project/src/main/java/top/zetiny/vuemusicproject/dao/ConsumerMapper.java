package top.zetiny.vuemusicproject.dao;

import top.zetiny.vuemusicproject.bean.Consumer;

import java.util.List;

public interface ConsumerMapper {
    List<Consumer> selectById(Integer id);

    List<Consumer> selectByUsername(String username);

    List<Consumer> selectAll();

    int insert(Consumer consumer);

    int update(Consumer consumer);

    int delete(Integer id);

    Consumer verifyPassword(String username, String phoneNum, String password);
}

package top.zetiny.vuemusicproject.dao;

import top.zetiny.vuemusicproject.bean.Singer;

import java.util.List;

public interface SingerMapper {
    List<Singer> selectById(Integer id);

    List<Singer> selectAll();

    List<Singer> selectByName(String name);

    List<Singer> selectBySex(Integer sex);

    int insert(Singer singer);

    int update(Singer singer);

    int delete(Integer id);
}

package top.zetiny.vuemusicproject.service;

import top.zetiny.vuemusicproject.bean.Singer;

import java.util.List;

public interface SingerService {
    List<Singer> querySingerById(Integer id);

    List<Singer> queryAllSingers();

    List<Singer> querySingersByName(String name);

    List<Singer> querySingersBySex(Integer sex);

    int addSinger(Singer singer);

    int modifySinger(Singer singer);

    int removeSinger(Integer id);
}

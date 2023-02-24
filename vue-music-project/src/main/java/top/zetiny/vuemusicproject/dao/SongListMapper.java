package top.zetiny.vuemusicproject.dao;

import top.zetiny.vuemusicproject.bean.SongList;

import java.util.List;

public interface SongListMapper {
    List<SongList> selectById(Integer id);

    List<SongList> selectByConsumerId(Integer consumerId);

    List<SongList> selectAll();

    List<SongList> selectByTitle(String title);

    int insert(SongList songList);

    int update(SongList songList);

    int delete(Integer consumerId);
}

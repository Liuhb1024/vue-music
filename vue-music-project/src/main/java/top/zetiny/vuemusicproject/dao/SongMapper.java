package top.zetiny.vuemusicproject.dao;

import top.zetiny.vuemusicproject.bean.Song;

import java.util.List;

public interface SongMapper {
    List<Song> selectById(Integer id);

    List<Song> selectBySingerId(Integer singerId);

    List<Song> selectBySongListId(Integer singerId);

    List<Song> selectAll();

    List<Song> selectByName(String name);

    int insert(Song Song);

    int update(Song Song);

    int delete(Integer id);
}

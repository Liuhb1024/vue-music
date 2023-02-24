package top.zetiny.vuemusicproject.dao;

import top.zetiny.vuemusicproject.bean.Favorite;
import top.zetiny.vuemusicproject.bean.SongList;

import java.util.List;

public interface FavoriteMapper {
    List<Favorite> selectById(Integer id);

    List<SongList> selectSongListsByConsumerId(Integer consumerId);

    List<Favorite> selectAll();

    int insert(Favorite favorite);

    int update(Favorite favorite);

    int delete(Integer consumerId, Integer songListId);
    int deleteBySongListId(Integer songListId);
}

package top.zetiny.vuemusicproject.service;

import top.zetiny.vuemusicproject.bean.Favorite;
import top.zetiny.vuemusicproject.bean.SongList;

import java.util.List;

public interface FavoriteService {
    List<Favorite> queryFavoriteById(Integer id);

    List<SongList> querySongListsByConsumerId(Integer consumerId);

    List<Favorite> queryAllFavorites();

    int addFavorite(Favorite favorite);

    int modifyFavorite(Favorite favorite);

    int removeFavorite(Integer consumerId, Integer songListId);

    int removeFavoritesBySongListId(Integer songListId);

}

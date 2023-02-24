package top.zetiny.vuemusicproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zetiny.vuemusicproject.bean.Favorite;
import top.zetiny.vuemusicproject.bean.SongList;
import top.zetiny.vuemusicproject.dao.FavoriteMapper;
import top.zetiny.vuemusicproject.service.FavoriteService;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public List<Favorite> queryFavoriteById(Integer id) {
        return favoriteMapper.selectById(id);
    }

    @Override
    public List<SongList> querySongListsByConsumerId(Integer consumerId) {
        return favoriteMapper.selectSongListsByConsumerId(consumerId);
    }

    @Override
    public List<Favorite> queryAllFavorites() {
        return favoriteMapper.selectAll();
    }

    @Override
    public int addFavorite(Favorite favorite) {
        return favoriteMapper.insert(favorite);
    }

    @Override
    public int modifyFavorite(Favorite favorite) {
        return favoriteMapper.update(favorite);
    }

    @Override
    public int removeFavorite(Integer consumerId, Integer songListId) {
        return favoriteMapper.delete(consumerId, songListId);
    }

    @Override
    public int removeFavoritesBySongListId(Integer songListId) {
        return favoriteMapper.deleteBySongListId(songListId);
    }
}

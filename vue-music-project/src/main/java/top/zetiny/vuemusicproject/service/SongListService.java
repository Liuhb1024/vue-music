package top.zetiny.vuemusicproject.service;

import top.zetiny.vuemusicproject.bean.SongList;

import java.util.List;

public interface SongListService {

    List<SongList> querySongListById(Integer id);
    List<SongList> querySongListsByConsumerId(Integer consumerId);

    List<SongList> queryAllSongLists();

    List<SongList> querySongListsByTitle(String title);

    int addSongList(SongList songList);

    int modifySongList(SongList songList);

    int removeSongList(Integer id);
}

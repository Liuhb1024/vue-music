package top.zetiny.vuemusicproject.service;

import top.zetiny.vuemusicproject.bean.Song;

import java.util.List;

public interface SongService {
    List<Song> querySongById(Integer id);

    List<Song> querySongsBySongListId(Integer songId);

    List<Song> querySongsBySingerId(Integer songId);

    List<Song> queryAllSongs();

    List<Song> querySongsByName(String name);

    int addSong(Song song);

    int modifySong(Song song);

    int removeSong(Integer id);
}

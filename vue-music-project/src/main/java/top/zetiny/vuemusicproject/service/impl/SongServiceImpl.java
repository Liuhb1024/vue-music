package top.zetiny.vuemusicproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zetiny.vuemusicproject.bean.Song;
import top.zetiny.vuemusicproject.dao.SongMapper;
import top.zetiny.vuemusicproject.service.SongService;

import java.util.List;

/**
 * 歌手 Service 层
 */
@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;

    @Override
    public List<Song> querySongById(Integer id) {
        return songMapper.selectById(id);
    }

    @Override
    public List<Song> querySongsBySongListId(Integer songId) {
        return songMapper.selectBySongListId(songId);
    }

    @Override
    public List<Song> querySongsBySingerId(Integer singerId) {
        return songMapper.selectBySingerId(singerId);
    }


    @Override
    public List<Song> queryAllSongs() {
        return songMapper.selectAll();
    }

    @Override
    public List<Song> querySongsByName(String name) {
        return songMapper.selectByName('%' + name + '%');
    }

    @Override
    public int addSong(Song song) {
        return songMapper.insert(song);
    }

    @Override
    public int modifySong(Song song) {
        return songMapper.update(song);
    }

    @Override
    public int removeSong(Integer id) {
        return songMapper.delete(id);
    }
}

package top.zetiny.vuemusicproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zetiny.vuemusicproject.bean.SongList;
import top.zetiny.vuemusicproject.dao.SongListMapper;
import top.zetiny.vuemusicproject.service.SongListService;

import java.util.List;

@Service
public class SongListServiceImpl  implements SongListService {
    @Autowired
    private SongListMapper songListMapper;

    @Override
    public List<SongList> querySongListById(Integer id) {
        return songListMapper.selectById(id);
    }

    @Override
    public List<SongList> querySongListsByConsumerId(Integer consumerId) {
        return songListMapper.selectByConsumerId(consumerId);
    }

    @Override
    public List<SongList> queryAllSongLists() {
        return songListMapper.selectAll();
    }

    @Override
    public List<SongList> querySongListsByTitle(String title) {
        return songListMapper.selectByTitle(title);
    }

    @Override
    public int addSongList(SongList songList) {
        return songListMapper.insert(songList);
    }

    @Override
    public int modifySongList(SongList songList) {
        return songListMapper.update(songList);
    }

    @Override
    public int removeSongList(Integer id) {
        return songListMapper.delete(id);
    }
}

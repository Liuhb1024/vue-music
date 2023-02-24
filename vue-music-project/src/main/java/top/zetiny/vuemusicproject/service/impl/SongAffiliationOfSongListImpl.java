package top.zetiny.vuemusicproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zetiny.vuemusicproject.bean.SongAffiliationOfSongList;
import top.zetiny.vuemusicproject.dao.SongAffiliationOfSongListMapper;
import top.zetiny.vuemusicproject.service.SongAffiliationOfSongListService;

import java.util.List;

@Service
public class SongAffiliationOfSongListImpl implements SongAffiliationOfSongListService {

    @Autowired
    private SongAffiliationOfSongListMapper affiliationMapper;

    @Override
    public List<SongAffiliationOfSongList> querySongListAffiliationOfSongListById(Integer id) {
        return affiliationMapper.selectById(id);
    }

    @Override
    public int queryAffiliationByCandidateKeys(Integer songId, Integer songListId) {
        return affiliationMapper.selectByCandidateKeys(songId, songListId);
    }

    @Override
    public int addSongListAffiliationOfSongList(SongAffiliationOfSongList affiliation) {
        return affiliationMapper.insert(affiliation);
    }

    @Override
    public int modifySongListAffiliationOfSongList(SongAffiliationOfSongList affiliation) {
        return affiliationMapper.update(affiliation);
    }

    @Override
    public int removeSongListAffiliationOfSongList(SongAffiliationOfSongList affiliation) {
        return affiliationMapper.delete(affiliation);
    }
}

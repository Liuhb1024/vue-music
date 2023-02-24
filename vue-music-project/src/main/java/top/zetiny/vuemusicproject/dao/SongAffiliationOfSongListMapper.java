package top.zetiny.vuemusicproject.dao;

import top.zetiny.vuemusicproject.bean.SongAffiliationOfSongList;

import java.util.List;

public interface SongAffiliationOfSongListMapper {
    List<SongAffiliationOfSongList> selectById(Integer id);

    int selectByCandidateKeys(Integer songId, Integer songListId);

    int insert(SongAffiliationOfSongList affiliation);

    int update(SongAffiliationOfSongList affiliation);

    int delete(SongAffiliationOfSongList affiliation);
}

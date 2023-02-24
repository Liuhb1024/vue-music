package top.zetiny.vuemusicproject.service;

import top.zetiny.vuemusicproject.bean.Favorite;
import top.zetiny.vuemusicproject.bean.SongAffiliationOfSongList;

import java.util.List;

public interface SongAffiliationOfSongListService {
    List<SongAffiliationOfSongList> querySongListAffiliationOfSongListById(Integer id);

    int queryAffiliationByCandidateKeys(Integer songId, Integer songListId);

    int addSongListAffiliationOfSongList(SongAffiliationOfSongList affiliation);

    int modifySongListAffiliationOfSongList(SongAffiliationOfSongList affiliation);

    int removeSongListAffiliationOfSongList(SongAffiliationOfSongList affiliation);
}

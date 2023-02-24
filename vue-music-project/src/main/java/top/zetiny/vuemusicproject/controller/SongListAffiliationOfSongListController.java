package top.zetiny.vuemusicproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zetiny.vuemusicproject.bean.SongAffiliationOfSongList;
import top.zetiny.vuemusicproject.service.SongAffiliationOfSongListService;
import top.zetiny.vuemusicproject.utils.Consts;

import java.util.HashMap;

@RestController
@RequestMapping("/affiliation")
public class SongListAffiliationOfSongListController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SongAffiliationOfSongListService affiliationService;

    @PostMapping("/add")
    public Object addOfficialPlaylist(@RequestBody SongAffiliationOfSongList affiliation) throws JsonProcessingException {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (affiliationService.queryAffiliationByCandidateKeys(affiliation.getSongId(), affiliation.getSongListId()) > 0) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "歌曲已存在");
            return objectMapper.writeValueAsString(hashMap);
        }
        int flag = 0;
        try {
            flag = affiliationService.addSongListAffiliationOfSongList(affiliation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag > 0) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "添加歌曲到歌单成功");
        } else {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "添加歌曲到歌单失败");
        }
        return objectMapper.writeValueAsString(hashMap);
    }

    @DeleteMapping("/remove")
    public Object removeOfficialPlaylist(@RequestBody SongAffiliationOfSongList affiliation) throws JsonProcessingException {
        int flag = affiliationService.removeSongListAffiliationOfSongList(affiliation);
        HashMap<String, Object> hashMap = new HashMap<>();
        if (flag > 0) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "删除歌曲收藏成功");
        } else {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "删除歌曲收藏失败");
        }
        return objectMapper.writeValueAsString(hashMap);
    }
}

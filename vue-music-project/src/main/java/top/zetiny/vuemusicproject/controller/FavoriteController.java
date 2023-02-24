package top.zetiny.vuemusicproject.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zetiny.vuemusicproject.bean.Favorite;
import top.zetiny.vuemusicproject.bean.SongList;
import top.zetiny.vuemusicproject.service.FavoriteService;
import top.zetiny.vuemusicproject.utils.CustomMsgUtils;

import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private CustomMsgUtils customMsgUtils;

    @GetMapping("/query")
    public Object queryFavoriteById(@RequestParam Integer consumerId) throws JsonProcessingException {
        Favorite favorite = null;
        try {
            favorite = favoriteService.queryFavoriteById(consumerId).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customMsgUtils.getJSONString(favorite);
    }

    @GetMapping("/querysonglistsbyconsumerid")
    public Object querySongListByConsumerId(@RequestParam Integer consumerId) throws JsonProcessingException {
        List<SongList> songlists = null;
        try {
            songlists = favoriteService.querySongListsByConsumerId(consumerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customMsgUtils.getJSONString(songlists);
    }

    @GetMapping("/query-all")
    public Object queryAllFavorites() throws JsonProcessingException {
        List<Favorite> favoriteList = null;
        try {
            favoriteList = favoriteService.queryAllFavorites();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customMsgUtils.getJSONString(favoriteList);
    }

    @PostMapping("/add")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Object addFavorite(@RequestBody Favorite favorite) throws JsonProcessingException {
        int flag = 0;
        try {
            flag = favoriteService.addFavorite(favorite);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag > 0) {
            return customMsgUtils.getCustomMessage(1, "保存歌单收藏信息成功");
        }
        return customMsgUtils.getCustomMessage(0, "保存歌单收藏信息失败");
    }

    @PutMapping("/modify")
    public Object modifyFavorite(@RequestBody Favorite favorite) throws JsonProcessingException {
        int flag = 0;
        try {
            flag = favoriteService.modifyFavorite(favorite);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag > 0) {
            return customMsgUtils.getCustomMessage(1, "更新歌单收藏信息成功");
        }
        return customMsgUtils.getCustomMessage(0, "更新歌单收藏信息失败");
    }

    @DeleteMapping("/remove")
    public Object removeFavorite(@RequestParam Integer consumerId, @RequestParam Integer songListId) throws JsonProcessingException {
        int flag = 0;
        try {
            flag = favoriteService.removeFavorite(consumerId, songListId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag > 0) {
            return customMsgUtils.getCustomMessage(1, "移除歌单收藏成功");
        }
        return customMsgUtils.getCustomMessage(0, "移除歌单收藏失败");
    }
}

package top.zetiny.vuemusicproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zetiny.vuemusicproject.bean.SongList;
import top.zetiny.vuemusicproject.service.FavoriteService;
import top.zetiny.vuemusicproject.service.SongListService;
import top.zetiny.vuemusicproject.service.SongService;
import top.zetiny.vuemusicproject.utils.Consts;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/songlist")
public class SongListController {
    @Autowired
    private SongListService songListService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SongService songService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/querysongsbysonglistid")
    public Object querySongsBySongListId(@RequestParam Integer songListId) throws JsonProcessingException {
        return objectMapper.writeValueAsString(songService.querySongsBySongListId(songListId));
    }


    @GetMapping("/querysonglistsbyconsumerid")
    public Object querySongListsByConsumerId(@RequestParam Integer consumerId) throws JsonProcessingException {
        return objectMapper.writeValueAsString(songListService.querySongListsByConsumerId(consumerId));
    }

    @GetMapping("/querysonglistsbytitle")
    public Object querySongListsByTitle(@RequestParam String title) throws JsonProcessingException {
        return objectMapper.writeValueAsString(songListService.querySongListsByTitle("%" + title + "%"));
    }

    @GetMapping("/queryallsonglists")
    public Object queryAllSongLists() throws JsonProcessingException {
        return objectMapper.writeValueAsString(songListService.queryAllSongLists());
    }

    @PostMapping("/add")
    public Object addSongList(@RequestBody SongList songList) throws JsonProcessingException {
        HashMap<String, Object> hashMap = new HashMap<>();
        for (SongList sl : songListService.querySongListsByTitle(songList.getTitle())) {
            if (sl.getTitle().equals(songList.getTitle())) {
                hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
                hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "新增歌单信息失败，歌单名已存在。");
                return objectMapper.writeValueAsString(hashMap);
            }
        }
        int flag = songListService.addSongList(songList);
        if (flag > 0) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "保存歌单信息成功");
        } else {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "保存歌单信息失败");
        }
        return objectMapper.writeValueAsString(hashMap);
    }

    @PutMapping("/modify")
    public Object modifySongList(@RequestBody SongList songList) throws JsonProcessingException {
        HashMap<String, Object> hashMap = new HashMap<>();
        int flag = 0;
        try {
            flag = songListService.modifySongList(songList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag > 0) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "更新歌单信息成功");
        } else {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "更新歌单信息失败");
        }
        return objectMapper.writeValueAsString(hashMap);
    }

    @Transactional
    @DeleteMapping("/remove/{songListId}")
    public Object removeSongLists(@PathVariable("songListId") Integer songListId) throws JsonProcessingException {
        HashMap<String, Object> hashMap = new HashMap<>();
        int flag = 0;
        try {
            favoriteService.removeFavoritesBySongListId(songListId);
            flag = songListService.removeSongList(songListId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag > 0) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "删除歌单信息成功");
        } else {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "删除歌单信息失败");
        }
        return objectMapper.writeValueAsString(hashMap);
    }

    /**
     * 更新歌单封面
     *
     * @param id      歌单id
     * @param imgFile 歌单封面图片
     * @return MSg - JSON
     * @throws JsonProcessingException JSON转换异常
     */
    @PostMapping("/updatesonglistimg")
    public Object updateSongImg(@RequestParam("songListId") String id, @RequestParam("song-list-img-file") MultipartFile imgFile) throws JsonProcessingException {
        if (imgFile.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "上传歌单封面失败");
            return objectMapper.writeValueAsString(hashMap);
        }
        String songListImgFileName = imgFile.getOriginalFilename();
        String filePathTargetDir = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "song-list-img";
        File file = new File(filePathTargetDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File destFile = new File(filePathTargetDir + System.getProperty("file.separator") + songListImgFileName);
        String avatarPath = "/img/song-list-img/" + songListImgFileName;
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            imgFile.transferTo(destFile);
            SongList songList = songListService.querySongListById(Integer.parseInt(id)).get(0);
            songList.setPic(avatarPath);
            int flag = songListService.modifySongList(songList);
            //返回上传操作的结果信息
            if (flag > 0) {
                hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
                hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "上传歌单封面成功");
                hashMap.put("avatar", avatarPath);
            } else {
                hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
                hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "上传歌单封面失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectMapper.writeValueAsString(hashMap);
    }
}

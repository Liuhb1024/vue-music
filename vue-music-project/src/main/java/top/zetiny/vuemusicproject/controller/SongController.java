package top.zetiny.vuemusicproject.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zetiny.vuemusicproject.bean.Song;
import top.zetiny.vuemusicproject.service.SongService;
import top.zetiny.vuemusicproject.utils.Consts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RequestMapping("/song")
@RestController
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/querysongbyid")
    public Object querySongById(HttpServletRequest request) throws JsonProcessingException {
        String id = request.getParameter("id").trim();
        return objectMapper.writeValueAsString(songService.querySongById(Integer.valueOf(id)));
    }

    @GetMapping("/querysongsbysingerid")
    public Object querySongBySongId(HttpServletRequest request) throws JsonProcessingException {
        String singerId = request.getParameter("singerId").trim();
        return objectMapper.writeValueAsString(songService.querySongsBySingerId(Integer.valueOf(singerId)));
    }

    @GetMapping("/querysongsbyname")
    public Object querySongsByName(@RequestParam String kw) throws JsonProcessingException {
        return objectMapper.writeValueAsString(songService.querySongsByName(kw));
    }

    @GetMapping("/queryallsongs")
    public Object queryAllSongs() throws JsonProcessingException {
        return objectMapper.writeValueAsString(songService.queryAllSongs());
    }

    /**
     * 添加歌曲信息
     *
     * @param request   HttpServletRequest对象
     * @param musicFile 音乐文件
     * @return 上传结果-JSON
     * @throws JsonProcessingException
     */
    @PostMapping("/add")
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Object addSong(HttpServletRequest request, @RequestParam("music-file") MultipartFile musicFile) throws JsonProcessingException {
        if (musicFile.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "上传歌曲失败");
            return objectMapper.writeValueAsString(hashMap);
        }
        String singerId = request.getParameter("singerId").trim();
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();
        String createTime = request.getParameter("createTime");
        String updateTime = request.getParameter("updateTime");
        String pic = request.getParameter("pic").trim();
        String lyric = request.getParameter("lyric").trim();
        Song song = new Song();
        song.setSingerId(Integer.valueOf(singerId));
        song.setName(name);
        song.setIntroduction(introduction);
        song.setPic(pic);
        song.setLyric(lyric);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date createTimeDate = null;
        Date updateTimeDate = null;
        try {
            createTimeDate = dateFormat.parse(createTime);
            updateTimeDate = dateFormat.parse(updateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //获取歌曲文件名
        String musicFileName = musicFile.getOriginalFilename();
        //拼接要存储歌曲图片的地址
        String musicFileTargetDir = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        File file = new File(musicFileTargetDir);
        //文件目录不存在则创建
        if (!file.exists()) {
            file.mkdirs();
        }

        File destFile = new File(musicFileTargetDir + System.getProperty("file.separator") + musicFileName);
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            musicFile.transferTo(destFile);
            String musicPath = "/song/" + musicFileName;
            song.setCreateTime(createTimeDate);
            song.setUpdateTime(updateTimeDate);
            song.setUrl(musicPath);
            //返回上传操作的结果信息
            int flag = songService.addSong(song);
            if (flag > 0) {
                hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
                hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "上传歌曲成功");
                hashMap.put("musicPath", musicPath);
            } else {
                hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
                hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "上传歌曲失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectMapper.writeValueAsString(hashMap);
    }

    @PutMapping("/modify")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Object modifySong(@RequestBody Song song) throws JsonProcessingException {
        int isSucceeded = 0;
        try {
            isSucceeded = songService.modifySong(song);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 将添加歌曲操作的结果以json格式返回
        HashMap<String, Object> hashMap = new HashMap<>();
        if (isSucceeded > 0) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "更新歌曲信息成功");
        } else {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "更新歌曲信息失败");
        }
        return objectMapper.writeValueAsString(hashMap);
    }

    @DeleteMapping("/remove/{songId}")
    public Object removeSong(@PathVariable("songId") String songId) throws JsonProcessingException {
        int isSucceeded = songService.removeSong(Integer.valueOf(songId));
        HashMap<String, Object> hashMap = new HashMap<>();
        if (isSucceeded > 0) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "删除歌曲信息成功");
        } else {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "删除歌曲信息失败");
        }
        return objectMapper.writeValueAsString(hashMap);

    }

    /**
     * 更新歌曲专辑封面
     *
     * @param photoFile 专辑图片
     * @param id        歌曲ID
     * @return 操作结果JSON
     * @throws JsonProcessingException
     */
    @PostMapping("/updatesongimg")
    public Object updateSongImg(@RequestParam("song-img-file") MultipartFile photoFile, @RequestParam("songId") String id) throws JsonProcessingException {
        if (photoFile.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "上传歌曲专辑封面失败");
            return objectMapper.writeValueAsString(hashMap);
        }
        String songImgFileName = photoFile.getOriginalFilename();
        String filePathTargetDir = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "song-img";
        File file = new File(filePathTargetDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File destFile = new File(filePathTargetDir + System.getProperty("file.separator") + songImgFileName);
        String avatarPath = "/img/song-img/" + songImgFileName;
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            photoFile.transferTo(destFile);
            Song song = songService.querySongById(Integer.parseInt(id)).get(0);
            song.setPic(avatarPath);
            int flag = songService.modifySong(song);
            //返回上传操作的结果信息
            if (flag > 0) {
                hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
                hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "上传歌曲专辑封面成功");
                hashMap.put("avatar", avatarPath);
            } else {
                hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
                hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "上传歌曲专辑封面失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectMapper.writeValueAsString(hashMap);
    }

    @PostMapping("/updatesongfile")
    public Object updateSongFile(@RequestParam("song-file") MultipartFile songFile, @RequestParam("songId") String id) throws JsonProcessingException {
        if (songFile.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "更新歌曲文件失败");
            return objectMapper.writeValueAsString(hashMap);
        }
        String songFileName = songFile.getOriginalFilename();
        String filePathTargetDir = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        File file = new File(filePathTargetDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File destFile = new File(filePathTargetDir + System.getProperty("file.separator") + songFileName);
        String songFilePath = "/song/" + songFileName;
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            songFile.transferTo(destFile);
            Song song = songService.querySongById(Integer.parseInt(id)).get(0);
            song.setUrl(songFilePath);
            int flag = songService.modifySong(song);
            //返回上传操作的结果信息
            if (flag > 0) {
                hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
                hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "更新歌曲文件成功");
                hashMap.put("songFilePath", songFilePath);
            } else {
                hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
                hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "更新歌曲文件失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectMapper.writeValueAsString(hashMap);
    }

    @GetMapping("/download")
    public void downloadSong(@RequestParam String url, HttpServletResponse response) {
        try {
            // 获取项目根路径
            String basePath = System.getProperty("user.dir");
            // 从 url 中提取文件名
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            // 完整的文件路径
            String filePath = basePath + url;
            
            File file = new File(filePath);
            if (!file.exists()) {
                throw new RuntimeException("文件不存在");
            }

            // 设置响应头
            response.setContentType("audio/mpeg");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            
            // 写入响应
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
                
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

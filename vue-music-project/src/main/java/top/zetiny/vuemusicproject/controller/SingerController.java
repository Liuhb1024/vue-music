package top.zetiny.vuemusicproject.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zetiny.vuemusicproject.bean.Singer;
import top.zetiny.vuemusicproject.service.SingerService;
import top.zetiny.vuemusicproject.utils.Consts;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/singer")
public class SingerController {

    @Autowired
    private SingerService singerService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/querysingerbyid")
    public Object querySingerById(HttpServletRequest request) throws JsonProcessingException {
        String id = request.getParameter("id").trim();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("singer", singerService.querySingerById(Integer.parseInt(id)));
        return objectMapper.writeValueAsString(hashMap);
    }

    /**
     * 查询所有歌手
     *
     * @return json 查询结果
     * @throws JsonProcessingException
     */
    @GetMapping("/queryallsingers")
    public Object queryAllSingers() throws JsonProcessingException {
        return objectMapper.writeValueAsString(singerService.queryAllSingers());
    }

    @GetMapping("/querysingersbyname")
    public Object querySingersByName(HttpServletRequest request) throws JsonProcessingException {
        String name = request.getParameter("name").trim();
        return objectMapper.writeValueAsString(singerService.querySingersByName(name));
    }

    @GetMapping("/querysingersbysex")
    public Object querySingersBySex(HttpServletRequest request) throws JsonProcessingException {
        String sex = request.getParameter("sex").trim();
        return objectMapper.writeValueAsString(singerService.querySingersBySex(Integer.parseInt(sex)));
    }

    @PostMapping("/add")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Object addSinger(@RequestBody Singer singer) throws JsonProcessingException {
        HashMap<String, Object> hashMap = new HashMap<>();
        for (Singer s : singerService.querySingersByName(singer.getName())) {
            if (s.getName().equals(singer.getName())) {
                hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
                hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "保存歌手信息失败，歌手已存在。");
                return objectMapper.writeValueAsString(hashMap);
            }
        }
        if (singer.getIntroduction().length()>= 255) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "保存歌手信息失败, 简介字数超过限制。");
            return objectMapper.writeValueAsString(hashMap);
        }
        int flag = singerService.addSinger(singer);
        if (flag > 0) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "保存歌手信息成功");
        } else {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "保存歌手信息失败");
        }
        return objectMapper.writeValueAsString(hashMap);
    }

    /**
     * 更新歌手信息
     *
     * @param singer
     * @return json 操作结果
     * @throws JsonProcessingException
     */
    @PutMapping("/modify")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Object modifySinger(@RequestBody Singer singer) throws JsonProcessingException {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (singer.getIntroduction().length()>= 255) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "保存歌手信息失败, 简介字数超过限制。");
            return objectMapper.writeValueAsString(hashMap);
        }
        int flag = 0;
        try {
            flag = singerService.modifySinger(singer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (flag > 0) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "保存歌手信息成功");
        } else {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "保存歌手信息失败");
        }
        return objectMapper.writeValueAsString(hashMap);
    }

    /**
     * 删除歌手信息
     *
     * @param singerId 歌手id
     * @return json-操作结果
     * @throws JsonProcessingException
     */
    @DeleteMapping("/remove/{singerId}")
    public Object removeSinger(@PathVariable("singerId") String singerId) throws JsonProcessingException {
        int flag = singerService.removeSinger(Integer.parseInt(singerId));
        HashMap<String, Object> hashMap = new HashMap<>();
        if (flag > 0) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "删除歌手信息成功");
        } else {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "删除歌手信息失败");
        }
        return objectMapper.writeValueAsString(hashMap);
    }

    /**
     * 更新歌手图片信息
     *
     * @param photoFile 单文件（歌手图片）
     * @param id        歌手id
     * @return 更新操作结果
     */
    @PostMapping("/updatesingerimg")
    public Object modifySingerImg(@RequestParam("singer-img-file") MultipartFile photoFile, @RequestParam("id") String id) throws JsonProcessingException {
        if (photoFile.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "上传歌手图片失败");
            return objectMapper.writeValueAsString(hashMap);
        }
        String singerImgFileName = photoFile.getOriginalFilename();
        String filePathTargetDir = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "singer-img";
        File file = new File(filePathTargetDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File destFile = new File(filePathTargetDir + System.getProperty("file.separator") + singerImgFileName);
        String avatarPath = "/img/singer-img/" + singerImgFileName;
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            photoFile.transferTo(destFile);
            Singer singer = singerService.querySingerById(Integer.parseInt(id)).get(0);
            singer.setPic(avatarPath);
            int flag = singerService.modifySinger(singer);
            //返回上传操作的结果信息
            if (flag > 0) {
                hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
                hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "上传歌手图片成功");
                hashMap.put("avatar", "avatarPath");
            } else {
                hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
                hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "上传歌手图片失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectMapper.writeValueAsString(hashMap);
    }
}

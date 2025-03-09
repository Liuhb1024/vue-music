package top.zetiny.vuemusicproject.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Share {
    private Integer id;
    private Integer userId;
    private Integer contentId;
    private Integer contentType; // 0:歌曲 1:歌单
    private String description;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date shareTime;
    
    private Integer status; // 1:有效 0:无效
    private Integer likes;
    private Integer comments;
    
    // 非数据库字段
    private transient Consumer userInfo;
    private transient Object contentObject; // 歌曲或歌单信息
    private transient List<ShareComment> commentList;
    private transient Boolean isLiked; // 当前用户是否点赞
}
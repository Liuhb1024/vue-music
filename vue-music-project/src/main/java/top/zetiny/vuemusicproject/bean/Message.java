package top.zetiny.vuemusicproject.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private Integer contentType; // 0:文本 1:歌曲 2:歌单
    private Integer contentId; // 歌曲或歌单ID
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    
    private Integer status; // 0:未读 1:已读
    
    // 非数据库字段
    private transient Consumer senderInfo;
    private transient Object contentObject; // 歌曲或歌单信息
}
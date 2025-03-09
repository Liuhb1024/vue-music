package top.zetiny.vuemusicproject.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ShareComment {
    private Integer id;
    private Integer shareId;
    private Integer userId;
    private String content;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    
    private Integer likes;
    
    // 非数据库字段
    private transient Consumer userInfo;
}
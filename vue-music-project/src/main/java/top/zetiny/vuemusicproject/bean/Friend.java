package top.zetiny.vuemusicproject.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Friend {
    private Integer id;
    private Integer userId;
    private Integer friendId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    
    private Integer status; // 0:待接受 1:已接受 2:已拒绝
    
    // 非数据库字段，用于前端展示
    private transient Consumer friendInfo;
}
package top.zetiny.vuemusicproject.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consumer {
    private Integer id;
    private String username;
    private String password;
    private Integer sex;
    private String phoneNum;
    private String email;
    private Date birth;
    private String introduction;
    private String location;
    private String avatar;
    private Date createTime;
    private Date updateTime;
}

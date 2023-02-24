package top.zetiny.vuemusicproject.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Singer implements Serializable {
    private Integer id;

    private String name;

    private Integer sex;

    private String pic;

    //@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birth;

    private String location;

    private String introduction;
}

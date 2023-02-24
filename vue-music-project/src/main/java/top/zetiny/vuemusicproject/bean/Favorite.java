package top.zetiny.vuemusicproject.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite implements Serializable {
    private Integer id;
    private Integer consumerId;
    private Integer songListId;
    private Date createTime;
}

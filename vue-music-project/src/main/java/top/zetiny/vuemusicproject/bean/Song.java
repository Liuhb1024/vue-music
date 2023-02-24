package top.zetiny.vuemusicproject.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song implements Serializable {
    /* 主键 */
    private Integer id;

    /* 歌手 id */
    private Integer singerId;

    /* 歌名 */
    private String name;

    /* 歌曲专辑名 */
    private String introduction;

    /* 歌曲封面地址 */
    private String pic;

    /* 创建时间 */
    private Date createTime;

    /* 更新时间 */
    private Date updateTime;

    /* 歌词 */
    private String lyric;

    /* 歌曲地址 */
    private String url;
}

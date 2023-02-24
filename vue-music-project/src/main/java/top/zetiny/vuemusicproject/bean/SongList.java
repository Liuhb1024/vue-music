package top.zetiny.vuemusicproject.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongList implements Serializable {
    /* 主键 */
    private Integer id;

    /* 歌单类型 */
    private Integer type;

    /* 创建者id */
    private Integer consumerId;

    /* 歌单名 */
    private String title;

    /* 歌单封面地址 */
    private String pic;

    /* 歌单简介 */
    private String introduction;

    /* 歌曲风格 */
    private String style;

}

package top.zetiny.vuemusicproject.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongAffiliationOfSongList implements Serializable {
    private Integer id;
    private Integer songId;
    private Integer songListId;
}

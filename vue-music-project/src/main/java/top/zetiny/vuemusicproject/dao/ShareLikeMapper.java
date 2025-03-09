package top.zetiny.vuemusicproject.dao;

import top.zetiny.vuemusicproject.bean.ShareLike;

public interface ShareLikeMapper {

    int addLike(ShareLike like);

    int removeLike(Integer shareId, Integer userId);

    ShareLike checkLike(Integer shareId, Integer userId);
}

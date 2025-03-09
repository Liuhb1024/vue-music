package top.zetiny.vuemusicproject.dao;

import top.zetiny.vuemusicproject.bean.Share;
import java.util.List;

public interface ShareMapper {

    int createShare(Share share);

    List<Share> getShareList(Integer offset, Integer limit);

    List<Share> getUserShares(Integer userId);

    Share getShareById(Integer shareId);

    int incrementLikes(Integer shareId);

    int decrementLikes(Integer shareId);

    int incrementComments(Integer shareId);
}

package top.zetiny.vuemusicproject.dao;

import top.zetiny.vuemusicproject.bean.ShareComment;
import java.util.List;

public interface ShareCommentMapper {

    int addComment(ShareComment comment);

    List<ShareComment> getCommentsByShareId(Integer shareId);
}

package top.zetiny.vuemusicproject.dao;

import top.zetiny.vuemusicproject.bean.Message;
import java.util.List;

public interface MessageMapper {

    int sendMessage(Message message);

    int markAllAsRead(Integer userId);

    int markAsRead(Integer messageId, Integer userId);

    List<Message> getChatHistory(Integer userId, Integer friendId, Integer offset, Integer limit);

    int getUnreadCount(Integer userId);
}

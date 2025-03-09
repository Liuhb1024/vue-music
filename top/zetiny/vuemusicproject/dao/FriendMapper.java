package top.zetiny.vuemusicproject.dao;

import top.zetiny.vuemusicproject.bean.Friend;
import java.util.List;

public interface FriendMapper {
    
    int addFriend(Friend friend);
    
    int updateFriendStatus(Integer id, Integer status);
    
    List<Friend> getFriendList(Integer userId);
    
    Friend checkFriendship(Integer userId, Integer friendId);
    
    List<Friend> getPendingFriendRequests(Integer userId);
} 
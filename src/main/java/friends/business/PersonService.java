package friends.business;

import friends.entity.PostMsgEntity;
import friends.entity.RequestTargetEntity;

import java.util.List;

public interface PersonService {
    void makeFriends(String email1, String email2);
    List<String> getFriends(String email);
    List<String> getMutualFriends(String email1, String email2);
    void subscribe(RequestTargetEntity entity);
    void block(RequestTargetEntity entity);
    List<String> getRecipients(PostMsgEntity entity);
}

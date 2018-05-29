package friends.dao;

public interface PersonRepoCustom {
    long addFriend(String email1, String email2);
    long addBlocker(String target, String requestor);
    long addFollower(String target, String requestor);
}

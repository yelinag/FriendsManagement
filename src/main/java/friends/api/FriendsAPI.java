package friends.api;

import friends.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/friends")
public class FriendsAPI {

    @PostMapping("/friends")
    public ResponseEntity<MsgEntity> makeFriends(@RequestBody TwoFriendsEntity newFriends){
        return ResponseEntity.ok(new MsgEntity(true));
    }

    @GetMapping("/friends")
    public ResponseEntity<FriendListEntity> listFriends(@RequestParam String email){
        return null;
    }

    @PostMapping("/get/mutualFriends")
    public ResponseEntity<FriendListEntity> getMutualFriends(@RequestBody TwoFriendsEntity friends){
        return null;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<MsgEntity> subscribe(@RequestBody RequestTargetEntity subscription){
        return null;
    }

    @PostMapping("/block")
    public ResponseEntity<MsgEntity> block(@RequestBody RequestTargetEntity blockEntity){
        return null;
    }

    @PostMapping("/timeline/posts")
    public ResponseEntity<RecipientsEntity> timelinePost(@RequestBody PostMsgEntity postMsgEntity){
        return null;
    }
}

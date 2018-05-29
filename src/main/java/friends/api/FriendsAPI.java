package friends.api;

import friends.business.PersonService;
import friends.business.error.BizErrorStatus;
import friends.business.error.BizException;
import friends.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class FriendsAPI {

    private final PersonService personService;

    @PostMapping("/friends")
    public ResponseEntity<MsgEntity> makeFriends(@RequestBody TwoFriendsEntity newFriends){
        personService.makeFriends(newFriends.getFriends().get(0), newFriends.getFriends().get(1));
        return ResponseEntity.ok(new MsgEntity(true, null));
    }

    @GetMapping("/friends")
    public ResponseEntity<FriendListEntity> listFriends(@RequestParam String email){
        List<String> friends = personService.getFriends(email);
        FriendListEntity friendList = new FriendListEntity(friends, friends.size());
        friendList.setSuccess(true);
        return ResponseEntity.ok(friendList);
    }

    @PostMapping("/get/mutualFriends")
    public ResponseEntity<FriendListEntity> getMutualFriends(@RequestBody TwoFriendsEntity friends){
        List<String> mutualFriends = personService.getMutualFriends(friends.getFriends().get(0), friends.getFriends().get(1));
        FriendListEntity friendList = new FriendListEntity(mutualFriends, mutualFriends.size());
        friendList.setSuccess(true);
        return ResponseEntity.ok(friendList);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<MsgEntity> subscribe(@RequestBody RequestTargetEntity subscription){
        personService.subscribe(subscription);
        return ResponseEntity.ok(new MsgEntity(true, null));
    }

    @PostMapping("/block")
    public ResponseEntity<MsgEntity> block(@RequestBody RequestTargetEntity blockEntity){
        personService.block(blockEntity);
        return ResponseEntity.ok(new MsgEntity(true, null));
    }

    @PostMapping("/timeline/posts")
    public ResponseEntity<RecipientsEntity> timelinePost(@RequestBody PostMsgEntity postMsgEntity){
        List<String> recipients = personService.getRecipients(postMsgEntity);
        RecipientsEntity responseBody = new RecipientsEntity(recipients);
        responseBody.setSuccess(true);
        return ResponseEntity.ok(responseBody);
    }

    @ExceptionHandler({ BizException.class })
    public ResponseEntity<MsgEntity> handleException(BizException ex) {
        HttpStatus status = ex.getErrorStatus().equals(BizErrorStatus.CLIENT) ?
                HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .body(new MsgEntity(false, ex.getMessage()));
    }
}

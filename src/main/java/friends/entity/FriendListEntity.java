package friends.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendListEntity extends MsgEntity{

    @NonNull
    private List<String> friends;

    @NonNull
    private int count;

}

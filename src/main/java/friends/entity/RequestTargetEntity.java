package friends.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTargetEntity {

    @NonNull
    @Email
    private String requestor;

    @NonNull
    @Email
    private String target;
}

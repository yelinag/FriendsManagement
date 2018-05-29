package friends.business.error;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException{
    private BizErrorStatus errorStatus;
    public BizException(BizErrorStatus errorStatus, String message){
        super(message);
        this.errorStatus = errorStatus;
    }
}

package friends.business;

import friends.business.error.BizErrorStatus;
import friends.business.error.BizException;
import friends.dao.PersonRepository;
import friends.dto.Person;
import friends.entity.PostMsgEntity;
import friends.entity.RequestTargetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;

    public void makeFriends(String email1, String email2){
        long result1 = personRepository.addFriend(email1, email2);
        long result2 = personRepository.addFriend(email2, email1);
        if(result1 < 0 || result2 < 0)
            throw new BizException(BizErrorStatus.CLIENT, "Email(s) not found");
    }

    public List<String> getFriends(String email){
        Person person = personRepository.findByEmail(email);
        if(person==null)
            throw new BizException(BizErrorStatus.CLIENT, "Email not found");

        return person.getFriends()!=null? person.getFriends() : new ArrayList<>();
    }

    public List<String> getMutualFriends(String email1, String email2){
        List<String> friendList1 = this.getFriends(email1);
        List<String> friendList2 = this.getFriends(email2);

        Set<String> friendSet = new HashSet<>();
        friendSet.addAll(friendList1);

        List<String> outputList = new ArrayList<>();
        for(Iterator iterator=friendList2.iterator(); iterator.hasNext();){
            String tmpEmail = (String) iterator.next();
            if(friendSet.contains(tmpEmail)) {
                outputList.add(tmpEmail);
            }
        }
        return outputList;
    }

    public void subscribe(RequestTargetEntity entity){
        long result = personRepository.addFollower(entity.getTarget(),
                entity.getRequestor());
        if(result < 0)
            throw new BizException(BizErrorStatus.CLIENT, "Email(s) not found");
    }

    public void block(RequestTargetEntity entity){
        long result = personRepository.addBlocker(entity.getTarget(),
                entity.getRequestor());
        if(result < 0)
            throw new BizException(BizErrorStatus.CLIENT, "Email(s) not found");
    }

    public List<String> getRecipients(PostMsgEntity entity){
        Person person = personRepository.findByEmail(entity.getSender());
        List<String> friends = Optional.ofNullable(person.getFriends())
                .orElse(new ArrayList<>());
        List<String> followers = Optional.ofNullable(person.getFollowers())
                .orElse(new ArrayList<>());
        Set<String> recipients = new HashSet<>();
        recipients.addAll(friends);
        recipients.addAll(followers);

        List<String> foundEmails = this.findEmails(entity.getText());
        for(int i=0; i<foundEmails.size(); i++){
            Person foundPerson = personRepository.findByEmail(foundEmails.get(i));
            if(foundPerson!=null){
                recipients.add(foundEmails.get(i));
            }
        }

        List<String> blockers = Optional.ofNullable(person.getBlockers())
                .orElse(new ArrayList<>());
        recipients.removeAll(blockers);

        List<String> outputList = new ArrayList<>();
        outputList.addAll(recipients);
        return outputList;
    }

    private List<String> findEmails(String text){
        Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(text);
        List<String> emails = new ArrayList<>();
        while(m.find()){
            emails.add(m.group());
        }
        return emails;
    }
}

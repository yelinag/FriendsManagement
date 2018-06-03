package friends.business;

import friends.dao.PersonRepository;
import friends.dto.Person;
import friends.entity.PostMsgEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl sut;

    @Mock
    private PersonRepository personRepository;

    private static String FRIEND_ONE = "friend1@gmail.com";
    private static String FRIEND_TWO = "friend2@gmail.com";
    private static String FRIEND_THREE = "friend3@gmail.com";
    private static String FOLLOWER_ONE= "follower1@gmail.com";
    private static String FOLLOWER_TWO = "follower2@gmail.com";

    @Test
    public void testMakeFriends(){
        when(personRepository.addFriend(FRIEND_ONE, FRIEND_TWO)).thenReturn(new Long(1));
        when(personRepository.addFriend(FRIEND_TWO, FRIEND_ONE)).thenReturn(new Long(1));

        sut.makeFriends(FRIEND_ONE, FRIEND_TWO);
        verify(personRepository, times(1)).addFriend(FRIEND_ONE, FRIEND_TWO);
        verify(personRepository, times(1)).addFriend(FRIEND_TWO, FRIEND_ONE);
    }

    @Test
    public void testGetFriends(){
        Person person = mockPerson();
        when(personRepository.findByEmail(FRIEND_ONE)).thenReturn(person);

        List<String> friends = sut.getFriends(FRIEND_ONE);
        verify(personRepository, times(1)).findByEmail(FRIEND_ONE);
        assertEquals(person.getFriends(), friends);
    }

    @Test
    public void testGetMutualFriends(){
        Person person = mockPerson();
        Person anotherPerson = mockAnotherPerson();

        when(personRepository.findByEmail(FRIEND_ONE)).thenReturn(person);
        when(personRepository.findByEmail(FRIEND_TWO)).thenReturn(anotherPerson);

        List<String> mutualList = sut.getMutualFriends(FRIEND_ONE, FRIEND_TWO);

        assertEquals(1, mutualList.size());
        assertEquals(FRIEND_THREE, mutualList.get(0));
    }

    @Test
    public void testGetRecipients(){
        Person person = mockPerson();
        HashSet recipientSet = new HashSet();

        when(personRepository.findByEmail(person.getEmail())).thenReturn(person);
        for(int i=0; i<person.getFriends().size(); i++){
            String tmpEmail = person.getFriends().get(i);
            when(personRepository.findByEmail(tmpEmail)).thenReturn(mockEmptyPerson(tmpEmail));
            recipientSet.add(tmpEmail);
        }

        for(int i=0; i<person.getFollowers().size(); i++){
            String tmpEmail = person.getFollowers().get(i);
            when(personRepository.findByEmail(tmpEmail)).thenReturn(mockEmptyPerson(tmpEmail));
            recipientSet.add(tmpEmail);
        }

        String mentioned1 = "mentioned1@gmail.com";
        String mentioned2 = "mentioned2@gmail.com";

        when(personRepository.findByEmail(mentioned1)).thenReturn(mockEmptyPerson(mentioned1));
        when(personRepository.findByEmail(mentioned2)).thenReturn(mockEmptyPerson(mentioned2));

        recipientSet.add(mentioned1); recipientSet.add(mentioned2);

        for(int i=0; i<person.getBlockers().size(); i++){
            String tmpEmail = person.getBlockers().get(i);
            when(personRepository.findByEmail(tmpEmail)).thenReturn(mockEmptyPerson(tmpEmail));
            recipientSet.remove(tmpEmail);
        }

        PostMsgEntity postMsgEntity = new PostMsgEntity(person.getEmail(),
                "hello world mentioned1@gmail.com mentioned2@gmail.com");

        List<String> results = sut.getRecipients(postMsgEntity);

        assertEquals(new ArrayList<String>(recipientSet), results);

    }

    private Person mockPerson(){
        Person person = new Person();
        person.setId(randomAlphaNumeric(5));
        person.setEmail(FRIEND_ONE);
        person.setName("Person name");
        person.setFollowers(new ArrayList<String>(){{
            add(FOLLOWER_ONE);
            add(FOLLOWER_TWO);
        }});
        person.setBlockers(new ArrayList<String>(){{
            add(FRIEND_TWO);
        }});
        person.setFriends(new ArrayList<String>(){{
            add(FRIEND_TWO);
            add(FRIEND_THREE);
        }});
        return person;
    }

    private Person mockAnotherPerson(){
        Person person = new Person();
        person.setId(randomAlphaNumeric(5));
        person.setEmail(FRIEND_TWO);
        person.setName("Person name");
        person.setFollowers(new ArrayList<>());
        person.setBlockers(new ArrayList<>());
        person.setFriends(new ArrayList<String>(){{
            add(FRIEND_ONE);
            add(FRIEND_THREE);
        }});
        return person;
    }

    private Person mockEmptyPerson(String email){
        Person person = new Person();
        person.setId(randomAlphaNumeric(5));
        person.setEmail(email);
        return person;
    }



    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }



}

package friends.dao;


import com.mongodb.client.result.UpdateResult;
import friends.dto.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;


public class PersonRepoCustomImpl implements PersonRepoCustom{

    @Autowired
    MongoTemplate mongoTemplate;

    public long addFriend(String email1, String email2){
        Query query = new Query(Criteria.where("email").is(email1));

        List<Person> persons = mongoTemplate.find(query, Person.class);
        if(persons.size() < 1)
            return -1;
        List<String> friends = persons.get(0).getFriends();
        friends = addToList(friends, email2);

        Update update = new Update();
        update.set("friends", friends);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Person.class);
        if(result!=null)
            return result.getModifiedCount();
        else
            return 0;
    }

    public long addBlocker(String target, String requestor){
        Query query = new Query(Criteria.where("email").is(target));

        List<Person> persons = mongoTemplate.find(query, Person.class);
        if(persons.size() < 1)
            return -1;
        List<String> blockers = persons.get(0).getBlockers();
        blockers = addToList(blockers, requestor);

        Update update = new Update();
        update.set("blockers", blockers);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Person.class);
        if(result!=null)
            return result.getModifiedCount();
        else
            return 0;
    }

    public long addFollower(String target, String requestor){
        Query query = new Query(Criteria.where("email").is(target));

        List<Person> persons = mongoTemplate.find(query, Person.class);
        if(persons.size() < 1)
            return -1;
        List<String> followers = persons.get(0).getFollowers();
        followers = addToList(followers, requestor);

        Update update = new Update();
        update.set("followers", followers);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Person.class);
        if(result!=null)
            return result.getModifiedCount();
        else
            return 0;
    }

    private List<String> addToList(List<String> list, String email){
        for(int i=0; i<list.size(); i++){
            if(email.equals(list.get(i)))
                return list;
        }
        list.add(email);
        return list;
    }
}

# Friends Management

It's a social networking demo application where you can add friend, subscribe, post and block. 

In this project, Spring framework is used for main implementation and mongodb to store users data. Users can perform following actions

1. Add friend
2. Retrieve current friends
3. Retrieve mutual friends between two individuals
4. Subscribe/follow person
5. Block person
6. Retrieve post notify user list 

### Prerequisites

Docker is necessary for deployment.

```
Docker
```

## Deployment

Run following commands

1. `docker run -d -p 27017:27017 --name mongo mongo`

2. `docker run -p 8080:8080 --name friends-management --link=mongo  yellingag/friends-management`

## Running the application

Server is running on `localhost:8080`

For documentations, please go to `localhost:8080/swagger-ui.html` for API usages

## Inserting data

1. `sudo docker exec -it mongo bash`
2. `mongo`
4. `use friendsmanagement`
3. `db.persons.insert([ { "email":"andy@example.com", "name":"Andy", "followers":[ ], "blockers":[ ], "friends":[ "common@example.com" ] }, { "email":"john@example.com", "name":"John", "followers":[ ], "blockers":[ ], "friends":[ "common@example.com" ] }, { "email":"common@example.com", "name":"John", "followers":[ ], "blockers":[ ], "friends":[ "andy@example.com" ] }, { "email":"lisa@example.com", "name":"John", "followers":[ ], "blockers":[ ], "friends":[ ] }, { "email":"kate@example.com", "name":"John", "followers":[ ], "blockers":[ ], "friends":[ ] } ])`

## Authors

* **Ye Lin Aung**

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details


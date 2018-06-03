# Friends Management

One Paragraph of project description goes here

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

```
Docker
```

## Deployment

Run following commands

1. `docker run -d -p 27017:27017 --name mongo mongo`

2. `docker run -p 8080:8080 --name friends-management --link=mongo  yellingag/friends-management`

## Running the application

After running, go to `localhost:8080`

For documentations, go to `localhost:8080/swagger-ui.html`

## Inserting data

1. `sudo docker exec -it mongo bash`
2. `mongo`
3. `db.persons.insert([{ "email" : "andy@example.com", "name" : "Andy", "followers" : [ "john@example.com" ], "blockers" : [ ], "friends" : [ "john@example.com", "common@example.com" ] }, { "email" : "john@example.com", "name" : "John", "followers" : [ ], "blockers" : [ ], "friends" : [ "andy@example.com" ] },{ "email" : "common@example.com", "name" : "John", "followers" : [ ], "blockers" : [ ], "friends" : [ "andy@example.com" ] }, { "email" : "lisa@example.com", "name" : "John", "followers" : [ ], "blockers" : [ ], "friends" : [ ] }])`

## Authors

* **Ye Lin Aung**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


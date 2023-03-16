# Agora

The Agora project is a chat application that allows students and teachers to exchange ideas within the Assignment Tracker Pro platform. The project involves setting up a server-side with PHP and MySQL, and implementing an Android app using Android Studio and Kotlin/Java. The app will have a LoginActivity for user registration and login, a MainActivity for displaying a list of chat groups using RecyclerView, and a ChatActivity for displaying and sending messages in a group chat. The chat will be updated in real-time using long-polling, WebSockets, or Firebase Cloud Messaging.

- [x] Set up the server-side with PHP and MySQL:
    - [x] Create a MySQL database to store user information and chat messages.
    - [x] Create PHP scripts to handle user registration, login, and message CRUD operations.
    - [x] Expose the PHP scripts through a REST API.

- [x] Create an Android app using Android Studio and Kotlin/Java:
    - [x] Implement user registration and login activities.
    - [x] Create a main activity to display the list of chat groups.
    - [x] Implement a chat activity to display and send messages in a group chat.

Server-side (PHP and MySQL):

- [x] Set up a MySQL database with the following tables:
    - [x] users: (id, username, password, email)
    - [x] groups: (id, name)
    - [x] group_members: (id, group_id, user_id)
    - [x] messages: (id, group_id, user_id, content, timestamp)
- [x] Create PHP scripts to handle the following operations:
    - [x] Register and login users.
    - [x] Create and join chat groups.
    - [x] Send and retrieve messages.
- [x] Use a REST API framework like Slim or Lumen to expose the PHP scripts as API endpoints.

Android app (Android Studio and Kotlin/Java):

- [x] Set up a new Android Studio project.
- [x] Create a LoginActivity to handle user registration and login. Use Retrofit or Volley to make API calls to the PHP server.
- [x] Implement a MainActivity that displays a list of chat groups. Use RecyclerView for the list.
- [x] Create a ChatActivity to display and send messages in a group chat. Use RecyclerView for the message list and the PHP API to send and receive messages.
- [x] To keep the chat updated in real-time, you can use long-polling, WebSockets, or Firebase Cloud Messaging.



```md
# Chatroom Application - README

## Project Overview
This project is a simple chatroom application where multiple clients can connect to a server, send messages, and broadcast them to all connected users. The server handles multiple clients and forwards messages from one client to the others. The project consists of three main classes: `Server`, `ClientHandler`, and `Client`.

## Features
- **Server-Client Communication**: The server accepts multiple clients, each of whom can send and receive messages.
- **Broadcasting**: Any message sent by a client is broadcast to all other clients connected to the server.
- **Username Support**: Each client sets a username when connecting to the server, which is displayed alongside their messages.
- **Server Messages**: The server can also send broadcast messages to all clients when a new user joins.
- **Concurrency**: The server uses threads to handle multiple clients simultaneously.

## Project Structure
```
src/
└── main/
└── chatroom/
├── Server.java
├── ClientHandler.java
└── Client.java
```
- **`Server.java`**: This class represents the chat server. It listens for incoming connections on a specific port and handles each client connection via a separate thread (`ClientHandler`). The server also broadcasts messages received from one client to all others.
- **`ClientHandler.java`**: This class is responsible for managing communication between the server and a connected client. It reads incoming messages from the client and forwards them to the server for broadcasting. It also handles receiving broadcast messages from other clients.
- **`Client.java`**: This class represents the client application. It connects to the server, sends the client's username, reads messages from the console to send to the server, and displays incoming messages from other users.

## How to Run
### Server Setup:
1. Compile the project.
2. Run the `Server` class to start the server. It will listen on port 1234 (this can be modified in the code).
```bash
java src.main.chatroom.Server
```

### Client Setup:
1. Compile the project.
2. Run the `Client` class to start a client instance. The client connects to the server and prompts the user to enter a username. Afterward, the client can start sending messages to the server.
```bash
java src.main.chatroom.Client
```

### Chat Interaction:
- Once connected, you can type and send messages in the client terminal, and they will be broadcast to all connected clients.
- New users joining the chat will be announced with a server message: "A new user has joined."

## Example Usage
### Start the server:
```bash
Starting server.
Waiting for new client.
```

### Start the client

:


```bash
Enter your username: Alice
```

### When another client connects:
```bash
Enter your username: Bob
```

### Chat messages between clients:
```plaintext
Alice: Hello everyone!
Bob: Hi Alice!
```
## Requirements
- Java Development Kit (JDK) - Version 8 or later.

## Conclusion
This project provides a simple multi-client chatroom using Java sockets. It is a great starting point for building more complex server-client applications with features like private messaging, encryption, and a more sophisticated UI.

Feel free to expand upon this basic chat application for further learning or use it as a foundation for a more advanced chat system.
```
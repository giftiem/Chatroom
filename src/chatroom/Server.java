package chatroom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server class that manages client connections and broadcasts messages to all clients.
 */
public class Server {
    public static final int port = 1234; // Default port for the chat server

    public static void main(final String[] args) throws IOException {
        // Start the server on the defined port
        new Server(new ServerSocket(port));
    }

    private final ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers;

    /**
     * Constructor to initialize the server with the provided server socket.
     * @param serverSocket The socket used to accept client connections.
     */
    public Server(final ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        // Start accepting client connections
        startAcceptClientLoop();
    }

    /**
     * Broadcasts a message from a sender client to all connected clients.
     * @param sender The client sending the message.
     * @param message The message to broadcast.
     */
    public void broadcastMessage(final ClientHandler sender, final String message) {
        for (final ClientHandler clientHandler : clientHandlers) {
            // Send the message to all clients except the sender
            clientHandler.receiveMessage(sender, message);
        }
        System.out.println("Message has been broadcasted.");
    }

    /**
     * Sends a server-generated message to all clients (e.g., when a user joins or leaves).
     * @param message The server message to send.
     */
    private void sendServerMessage(final String message) {
        for (final ClientHandler clientHandler : clientHandlers) {
            // Send the server message to each client
            clientHandler.receiveServerMessage(message);
        }
        System.out.println("Server message has been broadcasted.");
    }

    /**
     * Continuously listens for new client connections and starts a new thread for each client.
     */
    private void startAcceptClientLoop() {
        System.out.println("Starting server.");
        clientHandlers = new ArrayList<>();
        try {
            while (!serverSocket.isClosed()) {
                System.out.println("Waiting for new client.");
                final Socket socket = serverSocket.accept(); // Accept a new client connection
                System.out.println("A new client has connected.");
                final ClientHandler clientHandler = new ClientHandler(this, socket); // Create a handler for the client
                clientHandlers.add(clientHandler); // Add the client handler to the list
                System.out.println("A new client handler has been made and added.");
                new Thread(clientHandler).start(); // Start the client handler thread
                System.out.println("A new client handler has been started.");
                sendServerMessage("A new user has joined."); // Notify all clients that a new user joined
            }
        } catch (final IOException e) {
            System.out.println("There was an IOException.");
        }
    }
}
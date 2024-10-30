package chatroom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Handles communication between the server and a specific client.
 */
public class ClientHandler implements Runnable {
    private String username;
    private Server server;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    /**
     * Constructor to initialize the client handler for a specific client socket.
     * @param server The server instance handling the client.
     * @param socket The socket representing the client connection.
     */
    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            // Initialize buffered streams for reading and writing
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run method of the client handler, sets the username and starts listening for messages.
     */
    @Override
    public void run() {
        // Set the username for the client
        setUsername();
        // Start listening for messages from the client
        startSendMessageLoop();
    }

    /**
     * Receives a message from another client and sends it to this client.
     * @param sender The client sending the message.
     * @param message The message to send.
     */
    public void receiveMessage(ClientHandler sender, String message) {
        if (sender == this) {
            return; // Do not send the message to the sender itself
        }

        try {
            // Send the message to this client
            bufferedWriter.write(sender.username + ": " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message from the server to this client.
     * @param message The server message to send.
     */
    public void receiveServerMessage(String message) {
        try {
            // Send the server message
            bufferedWriter.write("SERVER: " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the client's username from the socket input stream.
     */
    private void setUsername() {
        try {
            // Read and set the username for this client
            username = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Continuously reads messages from the client and sends them to the server for broadcasting.
     */
    private void startSendMessageLoop() {
        try {
            // Keep reading messages from the client
            while (socket.isConnected()) {
                String message = bufferedReader.readLine();
                // Broadcast the message to all connected clients
                server.broadcastMessage(ClientHandler.this, message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
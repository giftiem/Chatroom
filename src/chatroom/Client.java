package chatroom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client class representing a chat client that connects to the server.
 * Handles sending and receiving messages.
 */
public class Client {

    public static void main(final String[] args) {
        // Start the client with the server port
        new Client(Server.port);
    }

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    /**
     * Constructor to initialize the Client, connecting to the server on a given port.
     * @param port The port on which the server is running.
     */
    public Client(final int port) {
        try {
            // Connect to the server using the localhost and provided port
            socket = new Socket("localhost", port);
            // Initialize buffered streams for reading and writing
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Set username and start message loops
            setUsername();
            startReadMessageLoop();
            startSendMessageLoop();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prompts the user to enter a username and sends it to the server.
     */
    private void setUsername() {
        System.out.print("Enter your username: ");
        try (Scanner scanner = new Scanner(System.in)) {
            // Read the username and send it to the server
            bufferedWriter.write(scanner.nextLine());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sent username information.");
    }

    /**
     * Continuously reads user input from the console and sends it to the server.
     */
    private void startSendMessageLoop() {
        try (Scanner scanner = new Scanner(System.in)) {
            // Keep reading and sending messages while the socket is connected
            while (socket.isConnected()) {
                bufferedWriter.write(scanner.nextLine());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            System.out.println("Send message loop done.");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Continuously listens for messages from the server and prints them.
     * This is run in a separate thread to allow simultaneous reading and writing.
     */
    private void startReadMessageLoop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Keep reading and printing messages while the socket is connected
                    while (socket.isConnected()) {
                        System.out.println(bufferedReader.readLine());
                    }
                    System.out.println("Read message loop done.");
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
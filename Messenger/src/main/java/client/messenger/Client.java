package client.messenger;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import message.Message;

public class Client {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public void send(Message message) throws IOException {
        out.writeObject(message);
    }

    public Message receive() throws IOException, ClassNotFoundException {
         Message message = (Message) in.readObject();
         return message;
    }

    public Client() throws IOException {
        try {
            socket = new Socket("127.0.0.1", 8000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error creating Client");
            throw new RuntimeException(e);
        }
    }

}

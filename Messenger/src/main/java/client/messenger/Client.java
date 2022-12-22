package client.messenger;

import client.messenger.controllers.MainController;
import javafx.application.Platform;
import message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    public static Socket socket;
    public ObjectInputStream in;
    public ObjectOutputStream out;
    public Client() {
        try {
            socket = new Socket("127.0.0.1", 8000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {
            System.out.println("Error creating Client");
            throw new RuntimeException(e);
        }
    }

    public void send(Message message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public Message receive() throws IOException, ClassNotFoundException {
        return (Message) in.readObject();
    }

}

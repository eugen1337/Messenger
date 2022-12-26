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
    public static ObjectInputStream in;
    public static ObjectOutputStream out;
    public static String login;

    public static void init() {
        try {
            socket = new Socket("176.196.126.138", 8000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {
            System.out.println("Error creating Client");
            throw new RuntimeException(e);
        }
    }

    public static void send(Message message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public static Message receive() throws IOException, ClassNotFoundException {
        return (Message) in.readObject();
    }

    public static void setLogin(String login) {Client.login = login;}
}

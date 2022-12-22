package client.messenger;

import client.messenger.controllers.MainController;
import javafx.application.Platform;
import message.Message;

import java.io.IOException;

public class ClientThread extends Thread {

    Client client;
    Updatable updater;
    public ClientThread(Client client, Updatable updater)
    {
        this.updater = updater;
        this.client = client;
    }

    @Override
    public void run()
    {
            System.out.println("New Thread");
            if(client != null)
                System.out.println("Client is alive");
            while (!Client.socket.isClosed()) {
                Message message;
                try {
                    message = client.receive();
                    updater.addMessage(message.getText());
                } catch (IOException e) {
                    System.out.println("Client is disconnected");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        System.out.println("Thread is closed");
    }

    public interface Updatable {
        public void addMessage(String str);
    }
}

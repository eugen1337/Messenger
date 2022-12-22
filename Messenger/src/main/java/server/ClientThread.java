package server;

import message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientThread extends Thread {
    Clients client;
    ClientThread(Clients client)
    {
        this.client = client;
    }

    public void sendMessage() throws IOException, ClassNotFoundException {
        Message message = receiveMessage(); // get message from every client by thread
        System.out.println(message.getText());
        Clients receivingClient = Model.clients.get(message.getName()); // get client object by key - name
        if(receivingClient != null) { // if client exist
            Server.send(receivingClient.out, message);
            System.out.println("sending client");

        }
        else {
            System.out.println("Error sending message to client");
            Server.send(client.out, new Message("Error sending message"));
        }
    }
    public Message receiveMessage() throws IOException, ClassNotFoundException {
        Message message = Server.receive(client.in);
        System.out.println("receiveMessage()");
        return message;
    }
    @Override
    public void run() {
        while (client.socket.isConnected()) {
            try {
                System.out.println("RECEIVING");
                //Message message = receiveMessage();
                //System.out.println(message.getText());
                sendMessage();
            } catch (IOException e) {
                System.out.println("Client is disconnected");
                break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Socket is closed");
    }

}


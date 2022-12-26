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

    public void sendMessage(Message message) throws IOException, ClassNotFoundException {
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
                Message message = receiveMessage(); // get message from every client by thread
                String str = message.getText();
                    if(str.equals("/online")){
                        new Thread(() -> {
                            try {
                                System.out.println(Model.clients.keySet().toArray()[0]);
                                client.out.writeObject(Model.clients.keySet().toArray());
                                //sendMessage(message);

                                client.out.flush();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
                        Thread.sleep(5*1000);
                    }
                    else
                        sendMessage(message);

            }
            catch (IOException e) {
                System.out.println("Client is disconnected");
                break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Socket is closed");
        Model.clients.remove(client.login);
    }

}


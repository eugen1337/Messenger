package server;

import message.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Model {
    Model(){};

    public static Map<String, Clients> clients = new HashMap<>();
    public static ArrayList<ClientThread> clientThreads = new ArrayList<>();

    public void setCT(int num, Clients client)
    {
        ClientThread ct = new ClientThread(client);
        clientThreads.set(num, ct);
    }

    class ClientThread extends Thread
    {
        Clients client;
        ClientThread(Clients client)
        {
            this.client = client;
        }

        public static void sendMessage(Clients sendingClient) throws IOException, ClassNotFoundException {
            Message message = (Message) sendingClient.in.readObject(); // get message from every client by thread
            if(Model.clients.get(message.getName()) != null) { // if client exist
                Clients receivingClient = Model.clients.get(message.getName()); // get client object by key - name
                receivingClient.out.writeObject(message);
            }
        }
        @Override
        public void run() {
            while (true) {
                try {
                    sendMessage(client);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}

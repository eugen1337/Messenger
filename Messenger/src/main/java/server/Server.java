package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import client.messenger.Client;
import message.Message;

public class Server {

    private static Clients clientAccept(ServerSocket serverSocket) throws IOException, ClassNotFoundException {
        Socket socket = serverSocket.accept();
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        Message message = new Message("Type your name");
        out.writeObject(message);

        message = (Message) in.readObject(); // get name from client
        String name = message.getText();

        //stringWrite(out, "Hello " + name);
        Clients client = new Clients(out, in);
        Model.clients.put(name, client);

        //very useful string
        //ClientThread ct = new ClientThread(client);


        Clients.count ++;
        System.out.println("Client #" + Clients.count + " is accepted - ");
        System.out.println(name);

        return client;
    }

// relocate to model
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



    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server launched");

        new Thread(() -> {
            while(true)
            {
                try {
                    clientAccept(serverSocket);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        //test
        Thread.sleep(40 * 1000);
        try {
            ClientThread.sendMessage(Model.clients.get("LOL"));
        }
        catch (IOException e){
            System.out.println("exception" + e.getLocalizedMessage());
        }
        catch (ClassNotFoundException e){
            System.out.println("exception" + e.getLocalizedMessage());
        }
        System.out.println("END");
    }

    public static void stringWrite(OutputStream out, String str) throws IOException {
        int count = str.length();
        out.write(count);
        byte[] b = str.getBytes();
        out.write(b);
    }
    public static String stringRead(InputStream in) throws IOException {
        int count = in.read();
        byte[] b = new byte[count];
        in.read(b);
        return new String(b);
    }
}

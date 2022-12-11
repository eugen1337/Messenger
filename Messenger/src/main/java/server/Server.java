package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import client.messenger.Client;
import message.Message;

public class Server {

    public void sendToClient(Message message, ObjectOutputStream out) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public Message receiveFromClient(ObjectInputStream in) throws IOException, ClassNotFoundException {
        Message message = (Message) in.readObject();
        return message;
    }

    private static Clients clientAccept(ServerSocket serverSocket) throws IOException, ClassNotFoundException {
        Socket socket = serverSocket.accept();
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        Clients client;
/*

        Message message = new Message("Type your name");
        out.writeObject(message);

        message = (Message) in.readObject(); // get name from client
        String name = message.getText();

        //stringWrite(out, "Hello " + name);

*/

        String name = login(in, out);
        client = new Clients(out, in);
        Model.clients.put(name, client);



        Clients.count ++;
        /*System.out.println("Client #" + Clients.count + " is accepted - ");
        System.out.println(client.g);*/

        return client;
    }



    private static String login(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Message message = (Message) in.readObject(); // get login from client
        String name = message.getText();

        message = (Message) in.readObject(); // get login from client
        String password = message.getText();

        message = new Message("success");
        out.writeObject(message);

        System.out.println("Client #" + Clients.count + " is accepted - ");
        System.out.println(name);
        return name;
    }


    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server launched");
        Model model = new Model();

        new Thread(() -> {
            while(true)
            {
                try {
                    model.setCT(Clients.count,
                            clientAccept(serverSocket)
                    );
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        //test
        /*
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
        */

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

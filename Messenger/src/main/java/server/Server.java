package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


import client.messenger.Client;
import database.DBHandler;
import message.Message;

public class Server {

    private static DBHandler dbHandler = new DBHandler();

    private static Clients clientAccept(ServerSocket serverSocket) throws IOException, ClassNotFoundException, SQLException {
        Socket socket = serverSocket.accept();
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        Clients client;
        String name = "default";
/*

        Message message = new Message("Type your name");
        out.writeObject(message);

        message = (Message) in.readObject(); // get name from client
        String name = message.getText();

        //stringWrite(out, "Hello " + name);

*/

        Message message = (Message) in.readObject(); // get login from client
        String action = message.getText();
        switch (action) {
            case "logging":
                name = login(in, out);
                break;
            case "register":
                name = register(in, out);
                break;
        }

        client = new Clients(out, in);
        Model.clients.put(name, client);

        Clients.count ++;
        /*System.out.println("Client #" + Clients.count + " is accepted - ");
        System.out.println(client.g);*/

        return client;
    }




    private static String login(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException, SQLException {
        System.out.println("LOGIN");

        String login = receive(in).getText(); // get login from client

        String password = receive(in).getText(); // get password from client

        if(dbHandler.getUser(login, password) != null)
            send(out, new Message("success"));
        else
            send(out, new Message("error"));
        System.out.println("Client #" + Clients.count + " is accepted - ");
        System.out.println(login);
        return login;
    }


    private static String register(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException, SQLException {
        System.out.println("Register");

        String login = receive(in).getText(); // get login from client

        String password = receive(in).getText(); // get password from client

        dbHandler.signUp(login, password, "Oleg");

        send(out, new Message("success"));

        System.out.println("Client #" + Clients.count + " is accepted - ");
        System.out.println(login);
        return login;
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server launched");
        Model model = new Model();
        DBHandler dbHandler = new DBHandler();

        new Thread(() -> {
            while(true)
            {
                try {
                    model.setCT(Clients.count,
                            clientAccept(serverSocket)
                    );
                } catch (IOException | ClassNotFoundException | SQLException e) {
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
    public static void send(ObjectOutputStream out, Message message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public static Message receive(ObjectInputStream in) throws IOException, ClassNotFoundException {
        return (Message) in.readObject();
    }
}

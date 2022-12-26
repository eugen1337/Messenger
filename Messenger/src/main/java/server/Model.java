package server;

import database.DBHandler;
import message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Model {
    private static DBHandler dbHandler;

    static {
        try {
            dbHandler = new DBHandler();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    Model(){};
    public static Map<String, Clients> clients = new HashMap<>();
    public static ArrayList<ClientThread> clientThreads = new ArrayList<>();

    public void setCT(int num, Clients client)
    {
        ClientThread ct = new ClientThread(client);
        ct.start();
        clientThreads.add(ct);
    }

    public Clients userAdding(Socket clientSocket) throws IOException, ClassNotFoundException, SQLException {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        Clients client;
        String name = "";


        while (name.equals("")) {
            String action = Server.receive(in).getText(); // get action from client
            switch (action) {
                case "logging":
                    name = login(in, out);
                    break;
                case "register":
                    name = register(in, out);
                    break;
            }
        }

        client = new Clients(clientSocket,out, in);
        client.login = name;
        Model.clients.put(name, client);

        Clients.count ++;

        setCT(Clients.count, client);

        System.out.println("Client #" + Clients.count + " is accepted - ");
        System.out.println(name);

        return client;
    }

    private static String login(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException, SQLException {
        System.out.println("LOGIN");

        String login = Server.receive(in).getText(); // get login from client

        String password = Server.receive(in).getText(); // get password from client

        /*int counter = 0;
        while (dbHandler.getUser(login, password).next())
            counter ++;
        System.out.println(counter);*/

        if(dbHandler.getUser(login, password).next() && !Model.clients.containsKey(login)) {
            Server.send(out, new Message("success"));
        }
        else {
            if(!Model.clients.containsKey(login))
                Server.send(out, new Message("user is already logged in"));
                else
            Server.send(out, new Message("error"));

            return "";
        }
        return login;
    }

    private static String register(ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException, SQLException {

        System.out.println("Register");

        String login = Server.receive(in).getText();

        String password = Server.receive(in).getText();

        boolean isSignedUp = dbHandler.signUp(login, password, "Oleg");

        if (!isSignedUp)
        {
            Server.send(out, new Message("user exist"));
            return "";
        }

        Server.send(out, new Message("success"));

        return login;
    }
}

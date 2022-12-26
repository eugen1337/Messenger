package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import message.Message;

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server launched");
        Model model = new Model();

        while(!serverSocket.isClosed())
        {
            Socket socket = clientAccept(serverSocket);
            new Thread(() -> {
                System.out.println("New thread");
                try{
                    model.userAdding(socket);
                }
                catch (IOException e){
                    System.out.println("Client disconnected");
                    e.printStackTrace();
                }
                catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    private static Socket clientAccept(ServerSocket serverSocket) throws IOException {
        Socket socket = serverSocket.accept();
        return socket;

    }

    public static void send(ObjectOutputStream out, Message message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public static Message receive(ObjectInputStream in) throws IOException, ClassNotFoundException {
        return (Message) in.readObject();
    }
}

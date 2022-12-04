package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private static Clients clientAccept(ServerSocket serverSocket) throws IOException {
        Socket socket = serverSocket.accept();
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        stringWrite(out, "Type your name\n");
        String name = stringRead(in);
        stringWrite(out, "Hello " + name);
        Clients client = new Clients(out, in);
        Clients.count ++;
        System.out.println("Client #" + Clients.count + " is accepted - ");
        System.out.println(name);
        return client;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server launched");
        Clients[] clients = new Clients[10];
        new Thread(() -> {
            while(true)
            {
                try {
                    clients[Clients.count] = clientAccept(serverSocket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        /*
        for(Clients client : clients)
        {
            client.name = stringRead(client.in);
        }
*/

        //clients[0] = clientAccept(serverSocket);
        //clients[1] = clientAccept(serverSocket);
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

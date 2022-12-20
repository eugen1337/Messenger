package server;

import java.io.*;
import java.net.Socket;

public class Clients {
    Clients(Socket socket, ObjectOutputStream out, ObjectInputStream in) throws IOException {
        this.socket = socket;
        this.out = out;
        this.in = in;
    }
    public static int count = 0;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public Socket socket;
}

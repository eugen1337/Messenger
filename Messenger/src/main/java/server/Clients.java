package server;

import java.io.*;

public class Clients {
    Clients(ObjectOutputStream out, ObjectInputStream in) throws IOException {
        this.out = out;
        this.in = in;
    }
    public static int count = 0;
    public ObjectOutputStream out;
    public ObjectInputStream in;
}

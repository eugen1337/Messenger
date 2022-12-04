package server;

import java.io.InputStream;
import java.io.OutputStream;

public class Clients {
    Clients(OutputStream out, InputStream in){
        this.out = out;
        this.in = in;
        this.name = name;
    }
    public static int count = 0;
    public OutputStream out;
    public InputStream in;
    public String name;
}

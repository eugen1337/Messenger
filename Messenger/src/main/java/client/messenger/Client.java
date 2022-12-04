package client.messenger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    InputStream in;
    OutputStream out;

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 8000);
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
        Scanner scanner = new Scanner(System.in);
        System.out.println(stringRead(in));
        stringWrite(out, scanner.nextLine());
        System.out.println(stringRead(in));
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

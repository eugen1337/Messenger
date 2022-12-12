package client.messenger;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import message.Message;

public class ConsoleClient {

    InputStream in;
    OutputStream out;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket s = new Socket("127.0.0.1", 8000);
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
        Scanner scanner = new Scanner(System.in);

        Message message = (Message) in.readObject(); // get "type name" from server
        System.out.println(message.getText());

        message = new Message(scanner.nextLine()); //get name by client
        out.writeObject(message);
        out.flush();
        /*
        message = (Message) in.readObject();
        System.out.println(message.getText());
        */


        message = (Message) in.readObject(); // get "type name" from server
        System.out.println(message.getText());
    }

}
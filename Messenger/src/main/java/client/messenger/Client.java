package client.messenger;

import message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public Client() {
        try {
            socket = new Socket("176.196.126.138", 8000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error creating Client");
            throw new RuntimeException(e);
        }
    }

    public void send(Message message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public Message receive() throws IOException, ClassNotFoundException {
        return (Message) in.readObject();
    }
    /*public void receivingFromServer()
    {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    Controller.addMessage(receive().getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    } */
}

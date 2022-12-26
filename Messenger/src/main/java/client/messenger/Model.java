package client.messenger;

import message.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static client.messenger.Client.in;

public class Model {
    public static void login(String login, String password, Updatable updater) {
        new Thread(() -> {
            if (login.isEmpty() || password.isEmpty()) {
                updater.addMessage("Введите правильные данные", "RED");
            } else {
                try {
                    Client.send(new Message("logging"));
                    Client.send(new Message(login));
                    Client.send(new Message(password));
                    switch (Client.receive().getText()) {
                        case "success" -> updater.addMessage("success", "GREEN");
                        case "error" ->
                                updater.addMessage("Такого пользователя не существует или пароль неверный", "RED");
                        case "user is already logged in" ->
                                updater.addMessage("Данный пользователь уже авторизован", "RED");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public static void register(String login, String password, String password1, Updatable updater) {
        new Thread(() -> {
            if (password.equals(password1)) {
                try {
                    Client.send(new Message("register"));
                    Client.send(new Message(login));
                    Client.send(new Message(password));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    if (Client.receive().getText().equals("success")) {
                        updater.addMessage("Успешная регистрация", "GREEN");
                    }
                    else {
                        updater.addMessage("Логин занят другим пользователем", "RED");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                updater.addMessage("Пароли не совпадают", "RED");
            }
        }).start();
    }

    public static void mainInit(Updatable updater) {
        new Thread(() -> {
            while(true) {
                try {
                    Client.send(new Message("/online"));
                    ArrayList<String> A = new ArrayList<>();
                    int n = (int) in.readObject();
                    for (int i = 0; i < n; i++) {
                        A.add(String.valueOf(in.readObject()));
                    }
                    for (String o : A) {
                        updater.addMessage(o, "");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        /*new Thread(() -> {

        });*/
    }

    public static void sendMsg(String text, String name) throws IOException {
        Client.send(new Message(text, name));
    }

    public static void shutdown() throws IOException {
        Client.socket.close();
    }

    public interface Updatable {
        public void addMessage(String str, String color);
    }
}

/*class ClientThread extends Thread {

    Client client;
    Updatable updater;
    public ClientThread(Client client, Updatable updater)
    {
        this.updater = updater;
        this.client = client;
    }

    @Override
    public void run()
    {
        System.out.println("New Thread");
        if(client != null)
            System.out.println("Client is alive");
        while (!Client.socket.isClosed()) {
            Message message;
            try {
                message = Client.receive();
                updater.addMessage(message.getText());
            } catch (IOException e) {
                System.out.println("Client is disconnected");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Thread is closed");
    }

    public interface Updatable {
        public void addMessage(String str);
    }
}*/

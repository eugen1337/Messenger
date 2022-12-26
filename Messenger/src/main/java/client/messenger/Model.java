package client.messenger;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import message.Message;
import java.io.IOException;
import javafx.scene.paint.Color;

public class Model {
    public static void login(TextField login, PasswordField password, Label info) {
        new Thread(() -> {
            System.out.println("login");
            if (login.getText().isEmpty() || password.getText().isEmpty()) {
                Platform.runLater(() -> info.setText("Введите правильные данные"));
            } else {
                try {
                    Client.send(new Message("logging"));
                    Client.send(new Message(login.getText()));
                    Client.send(new Message(password.getText()));
                    switch (Client.receive().getText()) {
                        case "success" -> Platform.runLater(() -> {
                            Stage stage = (Stage) login.getScene().getWindow();
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load(), 600, 400);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            stage.setTitle("Missenger");
                            stage.setScene(scene);
                        });
                        case "error" ->
                                Platform.runLater(() -> info.setText("Такого пользователя не существует или пароль неверный"));
                        case "user is already logged in" ->
                                Platform.runLater(() -> info.setText("Данный пользователь уже авторизован"));
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public static void register(TextField login, PasswordField password, PasswordField password1, Label info) {
        new Thread(() -> {
            if (password.getText().equals(password1.getText())) {
                try {
                    Client.send(new Message("register"));
                    Client.send(new Message(login.getText()));
                    Client.send(new Message(password.getText()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    if (Client.receive().getText().equals("success")) {
                        Platform.runLater(() -> {
                            info.setTextFill(Color.GREEN);
                            info.setText("Успешная регистрация");
                        });
                    }
                    else {
                        Platform.runLater(() -> info.setText("Логин занят другим пользователем"));
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Platform.runLater(() -> info.setText("Пароли не совпадают"));
            }
        }).start();
    }

    public static void shutdown() throws IOException {
        Client.socket.close();
    }
}

class ClientThread extends Thread {

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
                message = client.receive();
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
}

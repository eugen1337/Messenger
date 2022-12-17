package client.messenger.controllers;

import client.messenger.Client;
import client.messenger.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import message.Message;

import java.io.IOException;

public class LoginController {
    private static Client client;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Label info;
    @FXML
    private Button register;

    public void initialize() throws IOException {
        client = new Client();
    }

    @FXML
    public void onLoginClicked(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        System.out.println("LOLOLOL");
        //new Thread(() -> {
        String str = login.getText();
        System.out.println(str);
        if(login.getText().isEmpty() || password.getText().isEmpty()) {
            info.setText("Введите правильные данные");
            System.out.println("KEKEKE");
        }
        else {
            System.out.println("ELSE");
            Message message = new Message("logging");
            try {
                client.send(message);

                message = new Message(login.getText());
                client.send(message);
                message = new Message(password.getText());
                client.send(message);
                if (client.receive().getText() == "success") {
                    Stage stage = (Stage) login.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
                    fxmlLoader.setController(new MainController(client));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    stage.setTitle("Missenger");
                    stage.setScene(scene);
                }
            }
            catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        //});
    }

    @FXML
    public void onRegisterClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        fxmlLoader.setController(new RegisterController(client));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Missenger: Регистрация");
        stage.setScene(scene);
    }

}
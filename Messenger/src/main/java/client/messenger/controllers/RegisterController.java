package client.messenger.controllers;

import client.messenger.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import message.Message;

import java.io.IOException;

public class RegisterController {
    @FXML
    private Label info;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField password1;
    private Client client;
    void setClient(Client client) {
        this.client = client;
    }

    public void onRegisterClicked(ActionEvent actionEvent) throws IOException {
        if(password.getText().equals(password1.getText())) {
            Message message = new Message("register");
            client.send(message);
            message = new Message(login.getText());
            client.send(message);
            message = new Message(password.getText());
            client.send(message);
        }
        else {
            info.setText("Пароли не совпадают");
        }
    }
}

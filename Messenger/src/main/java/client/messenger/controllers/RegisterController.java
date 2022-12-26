package client.messenger.controllers;

import client.messenger.Client;
import client.messenger.Model;
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

    public void onRegisterClicked(ActionEvent actionEvent) throws IOException {
        Model.register(login, password, password1, info);
    }
}

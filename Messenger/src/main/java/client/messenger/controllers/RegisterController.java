package client.messenger.controllers;

import client.messenger.Model;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

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

    public void onRegisterClicked(ActionEvent actionEvent) {
        new Thread(() -> Model.register(login.getText(), password.getText(), password1.getText(),
                (str, color) -> Platform.runLater(() -> {
                    info.setText(str);
                    info.setTextFill(Paint.valueOf(color));
                }))).start();
    }
}

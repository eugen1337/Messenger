package client.messenger.controllers;

import client.messenger.Client;
import client.messenger.HelloApplication;
import client.messenger.Model;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Label info;
    @FXML
    private Button register;

    public void initialize() {
        Client.init();
    }

    @FXML
    public void onLoginClicked(ActionEvent actionEvent) {
        new Thread(() -> Model.login(login.getText(), password.getText(), (str, color) -> Platform.runLater(() -> {
            if (str.equals("success")) {
                Client.setLogin(login.getText());
                Platform.runLater(() -> {
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
            });}
            else {
                info.setText(str);
                info.setTextFill(Paint.valueOf(color));
            }
        }))).start();
    }

    @FXML
    public void onRegisterClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) register.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Missenger: Регистрация");
        stage.setScene(scene);
    }
}

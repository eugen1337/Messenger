package client.messenger.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RegisterController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onLoginClicked(ActionEvent actionEvent) {
    }

    public void onRegisterClicked(ActionEvent actionEvent) {
    }
}
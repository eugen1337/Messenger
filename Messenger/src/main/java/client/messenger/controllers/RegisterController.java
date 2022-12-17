package client.messenger.controllers;

import client.messenger.Client;
import javafx.event.ActionEvent;

public class RegisterController {
    private Client client;
    RegisterController(Client client) {
        System.out.println("register constructor");
        this.client = client;
    }

    public void onRegisterClicked(ActionEvent actionEvent) {

    }
}

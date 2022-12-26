package client.messenger.controllers;

import client.messenger.Client;
import client.messenger.Model;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class MainController {
    @FXML
    private TabPane tabPane;
    @FXML
    private TextFlow[] flows;
    @FXML
    private Text[][] texts;

    public static void shutdown() throws IOException {
        Model.shutdown();
    }


    public void onSendButtonClicked(ActionEvent actionEvent) {
        //flows[1].getChildren().add()
    }

    public void tabChanged(Event event) {
        tabPane.getSelectionModel();
    }
}

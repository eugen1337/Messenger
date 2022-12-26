package client.messenger.controllers;

import client.messenger.Client;
import client.messenger.Model;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;


import java.io.IOException;

public class MainController {
    @FXML
    private TabPane tabPane;
    @FXML
    private VBox users;
    @FXML
    private TextField text;
    @FXML
    private TextField tabName;

    public void initialize() {
        Model.mainInit((str, color) -> Platform.runLater(() -> {
            boolean flag = true;
            for (int i = 0; i < users.getChildren().size(); i++) {
                if (((Label) users.getChildren().get(i)).getText().equals(str) | ((Label) users.getChildren().get(i)).getText().equals("(Вы) " + str)) {
                    flag = false;
                }
            }
            if (flag) {
                if (str.equals(Client.login)) {
                    users.getChildren().add(new Label("(Вы) " + str));
                }
                else {users.getChildren().add(new Label(str));}
            }
        }));
    }

    public static void shutdown() throws IOException {
        Model.shutdown();
    }

    public void makeTab(ActionEvent actionEvent) {
        new Thread(() -> {
            boolean flag = false;
            for (int i = 0; i < users.getChildren().size(); i++) {
                if (((Label) users.getChildren().get(i)).getText().equals(tabName.getText()) | ((Label) users.getChildren().get(i)).getText().equals("(Вы) " + tabName.getText())) {
                    flag = true;
                }
            }
            if (flag) {
                Platform.runLater(() -> {
                    Tab tab = new Tab();
                    if (tabName.getText().equals(Client.login)) {
                        tab.setText("(Вы)" + tabName.getText());
                    } else {
                        tab.setText(tabName.getText());
                    }
                    HBox hBox = new HBox();
                    TextFlow textFlow = new TextFlow();
                    hBox.getChildren().add(textFlow);
                    tab.setContent(hBox);
                    tabPane.getTabs().add(tab);
                });
            }
        }).start();
    }

    public void onSendButtonClicked(ActionEvent actionEvent) throws IOException {
        Model.sendMsg(text.getText(), tabPane.getSelectionModel().getSelectedItem().getText());
    }

}

package client.messenger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import message.Message;

import java.io.IOException;

public class Controller {
    private Client client;
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
        if(login.getText() == "" || password.getText() == "") {
            info.setText("Введите правильные данные");
        }
        else {
            Message message = new Message("logging");
            client.send(message);
            message = new Message(login.getText());
            client.send(message);
            message = new Message(password.getText());
            client.send(message);
            if(client.receive().getText() == "success") {
                //Close current
                Stage stage = (Stage) login.getScene().getWindow();
                // do what you have to do
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                //Parent root1 = (Parent) fxmlLoader.load();
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Missenger");
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    @FXML
    public void onRegisterClicked(ActionEvent actionEvent) throws IOException {
        //Close current
        Stage stage = (Stage) register.getScene().getWindow();
        // do what you have to do
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        //Parent root1 = (Parent) fxmlLoader.load();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Регистрация");
        stage.setScene(scene);
        stage.show();
    }

}
package client.messenger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    private static Client client;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Label info;
    @FXML
    private Button register;
    private static boolean clientIsCreated = false;

    public void initialize() throws IOException {
        if(!clientIsCreated) {
            client = new Client();
        }
        clientIsCreated = true;
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
                    //Close current
                    Stage stage = (Stage) login.getScene().getWindow();
                    // do what you have to do
                    stage.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
                System.out.println(fxmlLoader);
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    System.out.println(scene);
                    //Parent root1 = (Parent) fxmlLoader.load();
                    stage = new Stage();
                    stage.setTitle("Missenger");
                    stage.setScene(scene);
                    stage.show();
                System.out.println("MAIN");
                }
            }
            catch (IOException | ClassNotFoundException e) {
                System.out.println("MAIN");
                throw new RuntimeException(e);
            }
        }
        //});
    }

    @FXML
    public void onRegisterClicked(ActionEvent actionEvent) throws IOException {
        //Close current
        Stage stage = (Stage) register.getScene().getWindow();
        // do what you have to do
        //stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        //Parent root1 = (Parent) fxmlLoader.load();
        //stage = new Stage();
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Регистрация");
        stage.setScene(scene);
        //stage.show();
    }

}
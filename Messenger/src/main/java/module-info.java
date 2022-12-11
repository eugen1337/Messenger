module client.messenger {
    requires javafx.controls;
    requires javafx.fxml;


    opens client.messenger to javafx.fxml;
    exports client.messenger;
}
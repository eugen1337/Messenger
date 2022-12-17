module client.messenger {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens client.messenger to javafx.fxml;
    exports client.messenger;
}
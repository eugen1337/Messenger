package database;

import java.sql.*;

public class DBHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection() throws SQLException {
        String connectString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        //Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectString, dbUser, dbPass);

        return dbConnection;
    }

    public void signUp(String login, String password, String name) throws SQLException {
        String insertQuery = "INSERT INTO " + Constants.USER_TABLE + "(" + Constants.USERS_LOGIN + "," +
                Constants.USERS_PASSWORD + "," + Constants.USERS_NAME + ")" + "VALUES(?,?,?)";
        PreparedStatement prState = getDbConnection().prepareStatement(insertQuery);
        prState.setString(1, login);
        prState.setString(2, password);
        prState.setString(3, name);

        prState.executeUpdate();
    }

    public ResultSet getUser(String login, String password) throws SQLException {
        ResultSet resSet = null;

        String selectQuery = "SELECT * FROM " + Constants.USER_TABLE + " WHERE " +
                Constants.USERS_LOGIN + "=? AND " + Constants.USERS_PASSWORD + "=?";
        PreparedStatement prState = getDbConnection().prepareStatement(selectQuery);
        prState.setString(1, login);
        prState.setString(2, password);

        resSet = prState.executeQuery();

        return resSet;
    }

}

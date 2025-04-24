package db;

import properties.FilePropertiesReader;

import java.io.IOException;
import java.sql.*;
import java.util.Map;

public class MySqlConnectorDb implements IDatabase {

    private Connection connection = null;
    private Statement statement = null;

    private void openConnectToDb() throws SQLException, IOException {
        if (connection == null) {
            Map<String,String> settings = new FilePropertiesReader().getSettings();
            connection = DriverManager.getConnection(settings.get("url"), settings.get("login"), settings.get("password"));
        }
        if (statement == null) {
            statement = connection.createStatement();
        }
    }

    @Override
    public ResultSet requestExecuteWithReturned (String sqlRequest) throws SQLException, IOException {
        openConnectToDb();
        return statement.executeQuery(sqlRequest);
    }
    @Override
    public void requestExecute (String sqlRequest) throws SQLException, IOException {
        openConnectToDb();
        statement.execute(sqlRequest);
    }
    @Override
    public void close () throws SQLException {
        if (statement != null){
            statement.close();
        }
        if ( connection != null) {
            connection.close();
        }
    }
}

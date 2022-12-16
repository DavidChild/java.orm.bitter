package io.github.davidchild.bitter.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class DbConnection implements AutoCloseable {

    protected Connection connection;
    protected String connectionString;

    public DbConnection() throws SQLException {

        connection = DataSourceFactory.getDynamicSource().getConnection();
        if (connection != null)
            connectionString = this.connection.toString();
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void finalize() {

    }

    @Override
    public String toString() {
        return connectionString;
    }

}

package io.github.davidchild.bitter.connection;

import io.github.davidchild.bitter.exception.DataSourceException;

import java.sql.Connection;

public class DbConnection implements AutoCloseable {

    protected Connection connection;
    protected String connectionString;

    public DbConnection() throws DataSourceException {
        connection = DataSourceFactory.getDataSourceConnection();

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

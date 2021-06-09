package b.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCFacade implements DBFacade {

    private Connection connection;

    @Override
    public void open() {
        try {
            connection = DriverManager.getConnection("jdbc:derby:db");
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo establecer una conexión con la base de datos.", e);
        }
    }

    @Override
    public List<Map<String, String>> queryResultAsAssociation(String sql) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Map<String, String>> result = new ArrayList<>();
                while (resultSet.next()) {
                    Map<String, String> row = new HashMap<>();
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        row.put(
                                metaData.getColumnName(i), resultSet.getString(i)
                        );
                    }
                    result.add(row);
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String[]> queryResultAsArray(String sql) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<String[]> result = new ArrayList<>();
                while (resultSet.next()) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    String[] row = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = resultSet.getString(i);
                    }
                    result.add(row);
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo cerrar la conexión con la base de datos.", e);
        }
    }

}

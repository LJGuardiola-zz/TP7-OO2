package a.persistence;

import a.model.Person;
import a.model.PersonDao;
import a.model.Phone;
import a.model.ProxyPhoneSet;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class PersonDaoJdbc implements PersonDao {

    private Connection getConnection() throws SQLException {
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:derby:db");
        } catch (SQLException e) {
            connection = DriverManager.getConnection("jdbc:derby:db;create=true");
            setupDB(connection);
        }
        return connection;
    }

    private void setupDB(Connection connection) throws SQLException {
        PreparedStatement statement1 = connection.prepareStatement(
                "CREATE TABLE PERSONS (" +
                        "ID INTEGER NOT NULL CONSTRAINT PERSONS_PK PRIMARY KEY, " +
                        "NAME VARCHAR(100) NOT NULL " +
                    ")"
        );
        statement1.executeUpdate();
        statement1.close();
        PreparedStatement statement2 = connection.prepareStatement(
                "CREATE TABLE PHONES (" +
                        "ID INT NOT NULL CONSTRAINT PHONES_PK PRIMARY KEY, " +
                        "NUMBER VARCHAR(20) NOT NULL, " +
                        "PERSON_ID INT NOT NULL CONSTRAINT PHONES_PERSONS_ID_FK REFERENCES PERSONS " +
                    ")"
        );
        statement2.executeUpdate();
        statement2.close();
    }

    public Person getPersonById(int id) {
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM PERSONS, PHONES WHERE PERSONS.ID = PHONES.PERSON_ID = ?"
            )
        ) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Person(
                            resultSet.getInt("ID"),
                            resultSet.getString("NAME"),
                            new ProxyPhoneSet(this, id)
                    );
                } else throw new RuntimeException("No se encontró ningún resultado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Phone> getPhonesByPersonId(int personId) {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM PHONES WHERE PERSON_ID = ?"
                )
        ) {

            statement.setInt(1, personId);

            try (ResultSet resultSet = statement.executeQuery()) {
                Set<Phone> phones = new HashSet<>();
                while (resultSet.next()) {
                    phones.add(
                            new Phone(
                                    resultSet.getString("NUMBER")
                            )
                    );
                }
                return phones;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

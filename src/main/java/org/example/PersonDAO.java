package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    private Connection connection;

    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertPerson(Person person) throws SQLException {
        String query = "INSERT INTO persons (firstname, lastname, age) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, person.getFirstname());
            stmt.setString(2, person.getLastname());
            stmt.setInt(3, person.getAge());
            stmt.executeUpdate();
        }
    }

    public List<Person> getAllPersons() throws SQLException {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT firstname, lastname, age FROM persons";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                persons.add(new Person(rs.getString("firstname"), rs.getString("lastname"), rs.getInt("age")));
            }
        }
        return persons;
    }

    public void updatePersonLastName(String oldLastName, String newLastName) throws SQLException {
        String query = "UPDATE persons SET lastname = ? WHERE lastname = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newLastName);
            stmt.setString(2, oldLastName);
            stmt.executeUpdate();
        }
    }

    public void deletePersonByFirstname(String firstname) throws SQLException {
        String query = "DELETE FROM persons WHERE firstname = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, firstname);
            stmt.executeUpdate();
        }
    }

    public void displayColumnDataTypes() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, "persons", null);
        while (resultSet.next()) {
            String columnName = resultSet.getString("COLUMN_NAME");
            String columnType = resultSet.getString("TYPE_NAME");
            System.out.println("Column Name: " + columnName + ", Data Type: " + columnType);
        }
    }
}

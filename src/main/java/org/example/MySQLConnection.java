package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MySQLConnection {

    private static final String JDBC_URL = "jdbc:mysql://localhost/quest";
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASS");

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Connected to database!");

            PersonDAO dao = new PersonDAO(connection);

            dao.insertPerson(new Person("Max", "Mustermann", 65));

            List<Person> persons = dao.getAllPersons();
            for (Person person : persons) {
                System.out.println(person);
            }

            dao.updatePersonLastName("Mustermann", "Neumann");

            persons = dao.getAllPersons();
            System.out.println("\nUpdated List:");
            for (Person person : persons) {
                System.out.println(person);
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Database connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


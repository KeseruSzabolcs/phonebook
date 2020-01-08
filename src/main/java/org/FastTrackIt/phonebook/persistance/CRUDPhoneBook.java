package org.FastTrackIt.phonebook.persistance;

import org.FastTrackIt.phonebook.domain.PhoneBook;
import org.FastTrackIt.phonebook.transfer.CreatePhoneBookItemRequest;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDPhoneBook {

    public void createPhoneBook(CreatePhoneBookItemRequest request) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT INTO phone_book_main (lastName, firstName, phoneNumber) VALUES (?, ?, ?)";

        try (Connection connection = DataBaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, request.getLastName());
            preparedStatement.setString(2, request.getFirstName());
            preparedStatement.setString(3, request.getPhone());

            preparedStatement.executeUpdate();
        }
    }

    public void updatePhoneBook(long id, String lastName, String firstName, String phoneNumber) throws SQLException, IOException, ClassNotFoundException {
        String sql = "UPDATE phone_book_main SET lastName=?, firstName=?, phoneNumber=? WHERE id=?";

        try (Connection connection = DataBaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setLong(4, id);

            preparedStatement.executeUpdate();
        }
    }

    public void deletePhoneBook(long[] id) throws SQLException, IOException, ClassNotFoundException {
        String sql = "DELETE FROM phone_book_main WHERE id=?";

        try (Connection connection = DataBaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id[0]);

            preparedStatement.executeUpdate();
        }
    }

    public void deletePhoneBooks(long[] id) throws SQLException, IOException, ClassNotFoundException {
        String sql = "DELETE FROM phone_book_main WHERE id in (?)";
        try (Connection connection = DataBaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < id.length; i++) {
                preparedStatement.setLong(1, id[i]);
                preparedStatement.executeUpdate();
            }
        }
    }


    public List<PhoneBook> getPhoneBooks() throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT id, lastName, firstName, phoneNumber FROM phone_book_main";

        List<PhoneBook> phoneBooks = new ArrayList<>();

        try (Connection connection = DataBaseConfiguration.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                PhoneBook phoneBook = new PhoneBook();

                phoneBook.setId(resultSet.getLong("id"));
                phoneBook.setLastName(resultSet.getString("lastName"));
                phoneBook.setFirstName(resultSet.getString("firstName"));
                phoneBook.setPhone(resultSet.getString("phoneNumber"));

                phoneBooks.add(phoneBook);
            }
        }
        return phoneBooks;
    }

    public List<PhoneBook> getContactByName(String firstName, String lastName) throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT id, firstName, lastName, phoneNumber FROM phone_book_main WHERE firstName=? OR lastName=?";
        //PhoneBook requestName = new PhoneBook();
        List<PhoneBook> phoneBooks = new ArrayList<>();
        try (Connection connection = DataBaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PhoneBook phoneBook = new PhoneBook();

                phoneBook.setId(resultSet.getLong("id"));
                phoneBook.setFirstName(resultSet.getString("firstName"));
                phoneBook.setLastName(resultSet.getString("lastName"));
                phoneBook.setPhone(resultSet.getString("phoneNumber"));

                phoneBooks.add(phoneBook);
            }
            return phoneBooks;
        }
    }

}

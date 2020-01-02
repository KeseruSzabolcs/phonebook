package org.FastTrackIt.phonebook.persistance;

import org.FastTrackIt.phonebook.domain.PhoneBook;
import org.FastTrackIt.phonebook.transfer.Dto;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDPhoneBook {

    public void createPhoneBook(Dto request) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT INTO phone_book_main (lastName, firstName, phone) VALUES (?, ?, ?)";

        try (Connection connection = DataBaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, request.getLastName());
            preparedStatement.setString(2, request.getFirstName());
            preparedStatement.setString(3, request.getPhone());

            preparedStatement.executeUpdate();
        }
    }

    public void updatePhoneBook(long id, String lastName, String firstName, String phone) throws SQLException, IOException, ClassNotFoundException {
        String sql = "UPDATE phone_book_main SET lastName=?, firstName=?, phone=? WHERE id=?";

        try (Connection connection = DataBaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, phone);
            preparedStatement.setLong(4, id);

            preparedStatement.executeUpdate();
        }
    }

    public void deletePhoneBook(long id) throws SQLException, IOException, ClassNotFoundException {
        String sql = "DELETE FROM phone_book_main WHERE id=?";

        try (Connection connection = DataBaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

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


    public Dto getPhoneBook(long id) throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT id, firstName, lastName, phone FROM phone_book_main WHERE id=?";
        Dto request = new Dto();
        try (Connection connection = DataBaseConfiguration.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                request.setFirstName(resultSet.getString("firstName"));
                request.setLastName(resultSet.getString("lastName"));
                request.setPhone(resultSet.getString("phone"));
            }
            return request;
        }
    }

    public List<PhoneBook> getPhoneBooks() throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT id, lastName, firstName, phone FROM phone_book_main";

        List<PhoneBook> phoneBooks = new ArrayList<>();

        try (Connection connection = DataBaseConfiguration.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                PhoneBook phoneBook = new PhoneBook();

                phoneBook.setId(resultSet.getLong("id"));
                phoneBook.setLastName(resultSet.getString("lastName"));
                phoneBook.setFirstName(resultSet.getString("firstName"));
                phoneBook.setPhone(resultSet.getString("phone"));

                phoneBooks.add(phoneBook);
            }
        }
        return phoneBooks;
    }

}

package org.FastTrackIt.phonebook.persistance;

import org.FastTrackIt.phonebook.domain.PhoneBook;
import org.FastTrackIt.phonebook.transfer.Dto;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDPhoneBook {

    public void createPhoneBook (Dto request) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT INTO phone_book_main (lastName, firstName, phone) VALUES (?, ?, ?)";

        try (Connection connection = DataBaseConfiguration.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, request.getLastName());
            preparedStatement.setString(2, request.getFirstName());
            preparedStatement.setString(3, request.getPhone());

            preparedStatement.executeUpdate();
        }
    }

    public void updatePhoneBook (long id, String lastName, String firstName, String phone) throws SQLException, IOException, ClassNotFoundException {
        String sql = "UPDATE phone_book_main SET lastName=?, firstName=?, phone=? WHERE id=?";

        try (Connection connection = DataBaseConfiguration.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,lastName);
            preparedStatement.setString(2,firstName);
            preparedStatement.setString(3,phone);
            preparedStatement.setLong(4,id);

            preparedStatement.executeUpdate();
        }
    }

    public void deletePhoneBook(long id) throws SQLException, IOException, ClassNotFoundException {
        String sql = "DELETE FROM phone_book_main WHERE id=?";

        try (Connection connection = DataBaseConfiguration.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        }
    }


    public List<PhoneBook> getPhoneBook() throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT id, lastName, firstName, phone FROM phone_book_main";

        List<PhoneBook> phoneBooks = new ArrayList<>();

        try (Connection connection = DataBaseConfiguration.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){

            while (resultSet.next()){
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

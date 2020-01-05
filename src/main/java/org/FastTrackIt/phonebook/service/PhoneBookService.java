package org.FastTrackIt.phonebook.service;

import org.FastTrackIt.phonebook.domain.PhoneBook;
import org.FastTrackIt.phonebook.persistance.CRUDPhoneBook;
import org.FastTrackIt.phonebook.transfer.Dto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PhoneBookService {

    private CRUDPhoneBook crudPhoneBook = new CRUDPhoneBook();

    public void createPhoneBook(Dto request) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Creating contact: " + request);
        crudPhoneBook.createPhoneBook(request);
    }

    public void updatePhoneBook(long id, Dto request) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Updating contact: "+ request);
        crudPhoneBook.updatePhoneBook(id, request.getLastName(), request.getFirstName(), request.getPhone());
    }

    public void deletePhoneBook(long[] id) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Deleting contact: "+id[0]);
        crudPhoneBook.deletePhoneBook(id);
    }

    public void deletePhoneBooks(long[] id) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Deleting contacts: " + Arrays.toString(id));
        crudPhoneBook.deletePhoneBooks(id);
    }

    public List<PhoneBook> getPhoneBooks() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Getting contacts...");
        return crudPhoneBook.getPhoneBooks();
    }

    public List<PhoneBook> getContactByName(String firstName, String lastName) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Getting contact by firstName");
        return crudPhoneBook.getContactByName(firstName, lastName);
    }
}

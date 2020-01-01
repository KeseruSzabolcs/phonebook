package org.FastTrackIt.phonebook.service;

import org.FastTrackIt.phonebook.domain.PhoneBook;
import org.FastTrackIt.phonebook.persistance.CRUDPhoneBook;
import org.FastTrackIt.phonebook.transfer.Dto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PhoneBookService {

    private CRUDPhoneBook crudPhoneBook = new CRUDPhoneBook();

    public void createPhoneBook(Dto request) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Creating contact: " + request);
        crudPhoneBook.createPhoneBook(request);
    }

    public void updatePhoneBook(Long id, Dto request) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Updating contact: "+ request);
        crudPhoneBook.updatePhoneBook(id, request.getLastName(), request.getFirstName(), request.getPhone());
    }

    public void deletePhoneBook(Long id, CRUDPhoneBook request) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Deleting contact: "+id);
        crudPhoneBook.deletePhoneBook(id);
    }

    public List<PhoneBook> getPhoneBook() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Getting contacts...");
        return crudPhoneBook.getPhoneBook();
    }

}

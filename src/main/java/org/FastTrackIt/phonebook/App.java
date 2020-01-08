package org.FastTrackIt.phonebook;

import org.FastTrackIt.phonebook.service.PhoneBookService;

import java.io.IOException;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        //Dto request = new Dto();
        //request.setFirstName("John");
        //request.setLastName("Lennon");
        //request.setPhone("326423");
        //CRUDPhoneBook crudPhoneBook = new CRUDPhoneBook();
        //List<PhoneBook> phoneBooks = crudPhoneBook.getPhoneBook();

        //System.out.println(phoneBooks);
        PhoneBookService phoneBookService = new PhoneBookService();
        phoneBookService.deletePhoneBooks(new  long[]{16,17,18});
    }
}

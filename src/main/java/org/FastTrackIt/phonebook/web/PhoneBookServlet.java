package org.FastTrackIt.phonebook.web;

import org.FastTrackIt.phonebook.config.ObjectMapperConfiguration;
import org.FastTrackIt.phonebook.domain.PhoneBook;
import org.FastTrackIt.phonebook.service.PhoneBookService;
import org.FastTrackIt.phonebook.transfer.Dto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Long.*;

@WebServlet("/phone_book_mains")
public class PhoneBookServlet extends HttpServlet {

    private PhoneBookService phoneBookService = new PhoneBookService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);

        Dto request = ObjectMapperConfiguration.getObjectMapper().readValue(req.getReader(), Dto.class);

        try {
            phoneBookService.createPhoneBook(request);
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(500, "Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);
        // Shows the values in the URL
        //System.out.println(Arrays.toString(new String[]{req.getQueryString()}));
        long[] a= getArrayOfId(getDeletingIdCount(req), req);
            try {
                if (a.length == 1){
                    phoneBookService.deletePhoneBook(a);
                } else {
                    phoneBookService.deletePhoneBooks(a);
                }
            } catch (SQLException | ClassNotFoundException e) {
                resp.sendError(500, "Internal server error: " + e.getMessage());
            }
        }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);

        String id = req.getParameter("id");
        Dto request = ObjectMapperConfiguration.getObjectMapper().readValue(req.getReader(), Dto.class);

        try {
            phoneBookService.updatePhoneBook(parseLong(id), request);
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(500, "Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);
        String firstName=req.getParameter("firstName");
        //String lastName=req.getParameter("lastName");

        try {
            if(firstName != null){
                PhoneBook contactByFirstName = phoneBookService.getContactByFirstName(firstName);
                String response =
                        ObjectMapperConfiguration.getObjectMapper().writeValueAsString(contactByFirstName);

                resp.getWriter().print(response);
            }else {
                List<PhoneBook> phoneBooks = phoneBookService.getPhoneBooks();
                String response =
                        ObjectMapperConfiguration.getObjectMapper().writeValueAsString(phoneBooks);

                resp.getWriter().print(response);
            }
        } catch (SQLException | ClassNotFoundException e){
                resp.sendError(500, "Internal server error: " + e.getMessage());
            }
        }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "content-type");
    }
    private int getDeletingIdCount(HttpServletRequest req){
        String x = req.getQueryString();
        Pattern p = Pattern.compile("(?<=id=)\\d+");
        Matcher m = p.matcher(x);
        int j=0;
        while (m.find()){
            j++;
        }
        return j;
    }
        private long[] getArrayOfId (int x, HttpServletRequest req){
        long[]a=new long[x];
        int k=0;
        String id = req.getQueryString();
        Pattern ps = Pattern.compile("(?<=id=)\\d+");
        Matcher ms = ps.matcher(id);
        while (ms.find()){
            a[k]=parseLong(ms.group());
            k++;
        }
        return a;
    }
}


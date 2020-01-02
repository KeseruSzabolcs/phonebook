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

            String id = req.getParameter("id");

            try {
                phoneBookService.deletePhoneBook(Long.parseLong(id));
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
            phoneBookService.updatePhoneBook(Long.parseLong(id), request);
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(500, "Internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);

        try {
            List<PhoneBook> phoneBooks = phoneBookService.getPhoneBook();

            String response =
                    ObjectMapperConfiguration.getObjectMapper().writeValueAsString(phoneBooks);

            resp.getWriter().print(response);
        } catch (SQLException | ClassNotFoundException e) {
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
}


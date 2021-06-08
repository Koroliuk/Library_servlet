package ua.training.controller;

import ua.training.controller.command.*;
import ua.training.controller.command.admin.*;
import ua.training.controller.command.librarian.GetReaderBooks;
import ua.training.controller.command.librarian.LibrarianHome;
import ua.training.controller.command.reader.OrderBook;
import ua.training.controller.command.reader.ReaderHome;
import ua.training.model.service.BookService;
import ua.training.model.service.OrderService;
import ua.training.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {
    private final Map<String, Command> commandMap = new HashMap<>();

    public void init() {
        commandMap.put("signup", new Signup(new UserService()));
        commandMap.put("login", new Login(new UserService()));
        commandMap.put("logout", new Logout());
        commandMap.put("search", new Search(new BookService()));
        commandMap.put("reader/home", new ReaderHome(new OrderService(), new UserService()));
        commandMap.put("reader/orderBook", new OrderBook(new UserService(), new BookService(), new OrderService()));
        commandMap.put("librarian/home", new LibrarianHome(new OrderService(), new UserService()));
        commandMap.put("librarian/getReaderBooks", new GetReaderBooks(new OrderService(), new UserService()));
        commandMap.put("admin/home", new AdminHome(new UserService(), new BookService()));
        commandMap.put("admin/addBook", new AddBook(new BookService()));
        commandMap.put("admin/editBook", new EditBook(new BookService()));
        commandMap.put("admin/deleteBook", new DeleteBook(new UserService(), new BookService()));
        commandMap.put("admin/addLibrarian", new AddLibrarian(new UserService()));
        commandMap.put("admin/deleteLibrarian", new DeleteLibrarian(new UserService(), new BookService()));
        commandMap.put("admin/blockUser", new BlockUser(new UserService()));
        commandMap.put("admin/unblockUser", new UnBlockUser(new UserService()));
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/" , "");
        Command command = commandMap.getOrDefault(path, (r) -> "/index.jsp");
        String page = command.execute(request);
        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", "/app"));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}

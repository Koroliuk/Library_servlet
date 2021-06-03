package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.model.entity.Book;
import ua.training.model.entity.User;
import ua.training.model.service.BookService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteLibrarian implements Command {
    private final UserService userService;
    private final BookService bookService;

    public DeleteLibrarian(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String librarianId = request.getParameter("id");
        if (librarianId == null || librarianId.equals("")) {
            return "/user/admin/home.jsp";
        }
        userService.delete(Long.parseLong(librarianId));
        List<User> userList = userService.findAll();
        List<Book> bookList = bookService.findAll();
        request.setAttribute("userList", userList);
        request.setAttribute("bookList", bookList);
        return "/user/admin/home.jsp";
    }
}

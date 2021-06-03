package ua.training.controller.command;

import ua.training.model.entity.Book;
import ua.training.model.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Search implements Command {
    private final BookService bookService;

    public Search(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String keyWords = request.getParameter("keyWords");
        List<Book> bookList;
        if (keyWords == null || keyWords.equals("")) {
            bookList = bookService.findAll();
        } else {
            bookList = bookService.findAllByKeyWords(keyWords);
        }
        request.setAttribute("bookList", bookList);
        return "/search.jsp";
    }
}

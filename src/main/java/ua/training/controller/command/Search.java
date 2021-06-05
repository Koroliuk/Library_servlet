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
        String keyWords = request.getParameter("keyWords").trim();
        String pageString = request.getParameter("page");
        String sortBy = request.getParameter("sortBy");
        String sortType = request.getParameter("sortType");
        List<Book> bookList;
        if (pageString == null || pageString.equals("")) {
            return "/search?page=1";
        }
        int page = Integer.parseInt(pageString.trim());
        if ((sortBy != null && !sortBy.equals("")) && (sortType == null || sortType.equals(""))) {
            return "/search?page=1&sortBy=id&sortType=inc&keyWords="+keyWords;
        }
        if (sortBy == null) {
            sortBy = "id";
        }
        if (sortType == null) {
            sortType = "free";
        }
        bookList = bookService.findAllByKeyWord(keyWords, sortBy, sortType, page);
        long amountOfPages = (bookService.getBookAmountWithKeyWord(keyWords)-1)/4+1;
        request.setAttribute("bookList", bookList);
        return "/search.jsp?page="+pageString+"&sortBy="+sortBy+"&sortType="+sortType+"&keyWords="+keyWords+"&amountOfPages="+amountOfPages;
    }
}

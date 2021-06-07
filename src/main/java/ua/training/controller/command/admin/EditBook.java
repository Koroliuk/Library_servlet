package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.model.entity.Author;
import ua.training.model.entity.Book;
import ua.training.model.entity.Edition;
import ua.training.model.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EditBook implements Command {
    private final BookService bookService;

    public EditBook(BookService bookService) {
        this.bookService = bookService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("action", "edit");

        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String authorsString = request.getParameter("authors");
        String description = request.getParameter("description");
        String language = request.getParameter("bookLanguage");
        String editionName = request.getParameter("edition");
        String publicationDateString = request.getParameter("publicationDate");
        String stringPrice = request.getParameter("price");
        String stringCount = request.getParameter("count");

        if (id == null || id.equals("")) {
            return "/user/admin/bookForm.jsp?bookError";
        }
        boolean condition1 = title == null || description == null || title.equals("") || description.equals("");
        boolean condition2 = language == null || editionName == null || language.equals("") || editionName.equals("");
        boolean condition3 = stringPrice == null || stringPrice.equals("") || stringCount == null || stringCount.equals("");
        boolean condition4 = authorsString == null || authorsString.equals("") || publicationDateString == null || publicationDateString.equals("");
        if (condition1 || condition2 || condition3 || condition4) {
            Optional<Book> optionalBook = bookService.findById(Long.parseLong(id));
            if (optionalBook.isPresent()) {
                request.setAttribute("book", optionalBook.get());
                return "/user/admin/bookForm.jsp";
            } else {
                return "/user/admin/bookForm.jsp?bookError";
            }
        }
        LocalDate publicationData = LocalDate.parse(publicationDateString);
        float price = Float.parseFloat(stringPrice);
        int count = Integer.parseInt(stringCount);
        boolean condition5 = publicationData.isAfter(LocalDate.now()) || publicationData.isEqual(LocalDate.now());
        boolean condition6 = price <= 0 || count <= 0;
        if (condition5 || condition6) {
            return "/user/admin/bookForm.jsp?validError";
        }
        List<String> authorNames = Arrays.asList(authorsString.split(","));
//        Optional<Book> optionalBook = bookService.findByTitleAndAuthorsNames(title, authorNames);
//        if (optionalBook.isPresent()) {
//            return "/user/admin/bookForm.jsp?createError";
//        }
//
//        Edition edition = new Edition.Builder()
//                .name(editionName)
//                .build();
//
//        List<Author> authors = new ArrayList<>();
//        for (String authorName: authorNames) {
//            Author author = new Author.Builder()
//                    .name(authorName)
//                    .build();
//            authors.add(author);
//        }
//
//        Book book = new Book.Builder()
//                .id(Long.parseLong(id))
//                .title(title)
//                .description(description)
//                .language(language)
//                .edition(edition)
//                .publicationDate(publicationData)
//                .price(price)
//                .count(count)
//                .authors(authors)
//                .build();
//
//        bookService.updateBook(book);
//        Optional<Book> optionalBook1 = bookService.findById(Long.parseLong(id));
//        optionalBook1.ifPresent(value -> request.setAttribute("book", value));
        return "/user/admin/bookForm.jsp?id="+id+"&successCreation=true";
    }
}

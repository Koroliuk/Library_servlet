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
        String titleUa = request.getParameter("titleUa");
        String authorsStringUa = request.getParameter("authorsUa");
        String descriptionUa = request.getParameter("descriptionUa");
        String languageUa = request.getParameter("bookLanguageUa");
        String editionNameUa = request.getParameter("editionUa");
        String titleEn = request.getParameter("titleEn");
        String authorsStringEn = request.getParameter("authorsEn");
        String descriptionEn = request.getParameter("descriptionEn");
        String languageEn = request.getParameter("bookLanguageEn");
        String editionNameEn = request.getParameter("editionEn");
        String publicationDateString = request.getParameter("publicationDate");
        String stringPrice = request.getParameter("price");
        String currency = request.getParameter("currency");
        String stringCount = request.getParameter("count");

        List<String> params = Arrays.asList(titleUa, titleEn, authorsStringUa, authorsStringEn, descriptionUa, descriptionEn,
                languageUa, languageEn, editionNameUa, editionNameEn, currency, stringPrice, stringCount, publicationDateString);
        for (String param : params) {
            if (param == null || param.equals("")) {
                Optional<Book> optionalBook1 = bookService.findByIdAll(Long.parseLong(id));
                optionalBook1.ifPresent(value -> request.setAttribute("book", value));
                return "/user/admin/bookForm.jsp";
            }
        }
        LocalDate publicationData = LocalDate.parse(publicationDateString);
        float price = Float.parseFloat(stringPrice);
        int count = Integer.parseInt(stringCount);
        boolean condition5 = publicationData.isAfter(LocalDate.now()) || publicationData.isEqual(LocalDate.now());
        boolean condition6 = price <= 0 || count <= 0;
        if (condition5 || condition6) {
            Optional<Book> optionalBook1 = bookService.findByIdAll(Long.parseLong(id));
            optionalBook1.ifPresent(value -> request.setAttribute("book", value));
            return "/user/admin/bookForm.jsp?validError=true";
        }
        List<String> authorNamesUa = Arrays.asList(authorsStringUa.split(","));
        List<String> authorNamesEn = Arrays.asList(authorsStringEn.split(","));
        float priceUa;
        if (currency.equals("uan")) {
            priceUa = price;
        } else {
            priceUa = price*30;
        }
        Edition edition = new Edition.Builder()
                .name(editionNameUa)
                .anotherName(editionNameEn)
                .build();

        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < authorNamesUa.size(); i++) {
            Author author = new Author.Builder()
                    .name(authorNamesUa.get(i))
                    .anotherName(authorNamesEn.get(i))
                    .build();
            authors.add(author);
        }

        Book book = new Book.Builder()
                .id(Long.parseLong(id))
                .title(titleUa)
                .anotherTitle(titleEn)
                .description(descriptionUa)
                .anotherDescription(descriptionEn)
                .language(languageUa)
                .anotherLanguage(languageEn)
                .edition(edition)
                .publicationDate(publicationData)
                .price(priceUa)
                .count(count)
                .authors(authors)
                .build();

        bookService.updateBook(book);
        Optional<Book> optionalBook1 = bookService.findByIdAll(Long.parseLong(id));
        optionalBook1.ifPresent(value -> request.setAttribute("book", value));
        return "/user/admin/bookForm.jsp?id="+id+"&successCreation=true";
    }
}

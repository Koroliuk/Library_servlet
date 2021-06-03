package ua.training.model.service;

import ua.training.model.dao.*;
import ua.training.model.entity.Author;
import ua.training.model.entity.Book;
import ua.training.model.entity.Edition;

import java.util.*;

public class BookService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public void createBook(Book book) {
        try (BookDao bookDao = daoFactory.createBookDao()) {

            Edition edition = getEditionOrNew(book.getEdition());
            book.setEdition(edition);

            List<Author> authorList = book.getAuthors();
            for (int i = 0; i < authorList.size(); i++) {
                Author author = getAuthorOrNew(authorList.get(i));
                authorList.set(i, author);
            }
            book.setAuthors(authorList);

            bookDao.create(book);

            for (Author author : book.getAuthors()) {
                bookDao.setAuthorship(book, author);
            }
        }
    }

    public void updateBook(Book book) {
        try (BookDao bookDao = daoFactory.createBookDao();
            AuthorDao authorDao = daoFactory.createAuthorDao()) {

            Edition edition = getEditionOrNew(book.getEdition());
            book.setEdition(edition);

            List<Author> currAuthors = authorDao.getAuthorsByBookId(book.getId());
            List<Author> newAuthors = book.getAuthors();
            for (Author author: currAuthors) {
                if (!newAuthors.contains(author)) {
                    bookDao.unSetAuthorship(book, author);
                }
            }

            for (Author authorI: newAuthors) {
                Author author = getAuthorOrNew(authorI);
                if (!currAuthors.contains(author)) {
                    bookDao.setAuthorship(book, author);
                }
            }
            book.setAuthors(newAuthors);

            bookDao.update(book);
        }
    }

    public void deleteBook(long id) {
        try (BookDao bookDao = daoFactory.createBookDao();
            AuthorDao authorDao = daoFactory.createAuthorDao()) {
            Optional<Book> optionalBook = bookDao.findById(id);
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                List<Author> authors = authorDao.getAuthorsByBookId(id);
                book.setAuthors(authors);
                for (Author author: book.getAuthors()) {
                    bookDao.unSetAuthorship(book, author);
                }
                bookDao.delete(book.getId());
            }
        }
    }

    public Optional<Book> findByTitleAndAuthorsNames(String title, List<String> authorNames) {
        try (AuthorDao authorDao = daoFactory.createAuthorDao();
            BookDao bookDao = daoFactory.createBookDao()) {
            List<Author> authors = new ArrayList<>();
            for (String authorName: authorNames) {
                Optional<Author> optionalAuthor = authorDao.findByName(authorName);
                if (!optionalAuthor.isPresent()) {
                    return Optional.empty();
                } else {
                    authors.add(optionalAuthor.get());
                }
            }
            return bookDao.findByTitleAndAuthorsNames(title, authors);
        }
    }

    public Optional<Book> findById(long id) {
        try (BookDao bookDao = daoFactory.createBookDao();
            AuthorDao authorDao = daoFactory.createAuthorDao()) {
            Optional<Book> optionalBook = bookDao.findById(id);
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                book.setAuthors(authorDao.getAuthorsByBookId(id));
                return Optional.of(book);
            }
            return Optional.empty();
        }
    }

    public List<Book> findAll() {
        try (BookDao bookDao = daoFactory.createBookDao();
            AuthorDao authorDao = daoFactory.createAuthorDao()) {
            List<Book> bookList = bookDao.findAll();
            for (Book book : bookList) {
                List<Author> authors = authorDao.getAuthorsByBookId(book.getId());
                book.setAuthors(authors);
            }
            return bookList;
        }
    }

    public List<Book> findAllByKeyWords(String keyWords) {
        try (BookDao bookDao = daoFactory.createBookDao()) {
            String[] keyWordList = keyWords.split("[^a-zA-ZА-Яа-я0-9]+");
            Map<Book, Integer> bookMap = new HashMap<>();
            for (String keyWord: keyWordList) {
                List<Book> bookList1 = bookDao.findByKeyWordInTitle(keyWord);
                List<Book> bookList2 = bookDao.findByKeyWordInAuthors(keyWord);
                addBooksToMapFromList(bookList1, bookMap);
                addBooksToMapFromList(bookList2, bookMap);
            }
            Comparator<Book> comparator = Comparator.comparing(bookMap::get);
            List<Book> books = new ArrayList<>(bookMap.keySet());
            books.sort(comparator);
            return books;
        }
    }

    private void addBooksToMapFromList(List<Book> bookList, Map<Book, Integer> bookMap) {
        try (AuthorDao authorDao = daoFactory.createAuthorDao()) {
            for (Book book: bookList) {
                List<Author> authorList = authorDao.getAuthorsByBookId(book.getId());
                book.setAuthors(authorList);
                if (!bookMap.containsKey(book)) {
                    bookMap.put(book, 1);
                } else {
                    bookMap.put(book, bookMap.get(book)+1);
                }
            }
        }
    }

    public Edition getEditionOrNew(Edition edition) {
        try (EditionDao editionDao = daoFactory.createEditionDao()) {
            Optional<Edition> optionalEdition = editionDao.findByName(edition.getName());
            if (optionalEdition.isPresent()) {
                edition = optionalEdition.get();
            } else {
                editionDao.create(edition);
                optionalEdition = editionDao.findByName(edition.getName());
                if (optionalEdition.isPresent()) {
                    edition = optionalEdition.get();
                }
            }
            return edition;
        }
    }

    public Author getAuthorOrNew(Author author) {
        try (AuthorDao authorDao = daoFactory.createAuthorDao()) {
            Optional<Author> optionalAuthor = authorDao.findByName(author.getName());
            if (optionalAuthor.isPresent()) {
                author = optionalAuthor.get();
            } else {
                authorDao.create(author);
                optionalAuthor = authorDao.findByName(author.getName());
                if (optionalAuthor.isPresent()) {
                    author = optionalAuthor.get();
                }
            }
            return author;
        }
    }
}

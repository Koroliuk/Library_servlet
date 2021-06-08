package ua.training.model.service;

import ua.training.model.dao.*;
import ua.training.model.entity.Author;
import ua.training.model.entity.Book;
import ua.training.model.entity.Edition;

import java.sql.SQLException;
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
        } catch (SQLException e) {
            e.printStackTrace();
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
                bookDao.delete(book.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Book> findByTitleAndAuthorsNames(String title, List<String> authorNames1, List<String> authorNames2) {
        try (AuthorDao authorDao = daoFactory.createAuthorDao();
            BookDao bookDao = daoFactory.createBookDao()) {
            List<Author> authors = new ArrayList<>();
            for (int i = 0; i < authorNames1.size(); i++) {
                Optional<Author> optionalAuthor = authorDao.findByNames(authorNames1.get(i), authorNames2.get(i));
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
            Optional<Book> optionalBook = bookDao.findByIdWithLocaled(id);
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                book.setAuthors(authorDao.getAuthorsByBookIdLocaled(id));
                return Optional.of(book);
            }
            return Optional.empty();
        }
    }

    public Optional<Book> findByIdAll(long id) {
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
                List<Author> authors = authorDao.getAuthorsByBookIdLocaled(book.getId());
                book.setAuthors(authors);
            }
            return bookList;
        }
    }

    public List<Book> findAllByKeyWord(String keyWord, String sortBy, String sortType, int page) {
        try (BookDao bookDao = daoFactory.createBookDao();
             AuthorDao authorDao = daoFactory.createAuthorDao()) {
            List<Book> bookList = bookDao.findByKeyWord(keyWord, sortBy, sortType, page);
            for (Book book : bookList) {
                List<Author> authorList = authorDao.getAuthorsByBookIdLocaled(book.getId());
                book.setAuthors(authorList);
            }
            return bookList;
        }
    }

    public Edition getEditionOrNew(Edition edition) {
        try (EditionDao editionDao = daoFactory.createEditionDao()) {
            Optional<Edition> optionalEdition = editionDao.findByNames(edition.getName(), edition.getAnotherName());
            if (optionalEdition.isPresent()) {
                edition = optionalEdition.get();
            } else {
                editionDao.create(edition);
                optionalEdition = editionDao.findByNames(edition.getName(), edition.getAnotherName());
                if (optionalEdition.isPresent()) {
                    edition = optionalEdition.get();
                }
            }
            return edition;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Author getAuthorOrNew(Author author) {
        try (AuthorDao authorDao = daoFactory.createAuthorDao()) {
            Optional<Author> optionalAuthor = authorDao.findByNames(author.getName(), author.getAnotherName());
            if (!optionalAuthor.isPresent()) {
                authorDao.create(author);
                optionalAuthor = authorDao.findByNames(author.getName(), author.getAnotherName());
                if (optionalAuthor.isPresent()) {
                    author = optionalAuthor.get();
                }
            } else {
                author = optionalAuthor.get();

            }
            return author;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getBookAmountWithKeyWord(String keyWord) {
        try (BookDao bookDao = daoFactory.createBookDao()) {
            return bookDao.getBookAmountWithKeyWord(keyWord);
        }
    }
}

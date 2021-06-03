package ua.training.model.dao.impl;

import ua.training.model.dao.BookDao;
import ua.training.model.dao.mapper.BookMapper;
import ua.training.model.entity.Author;
import ua.training.model.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCBookDao implements BookDao {
    private final Connection connection;

    public JDBCBookDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Book entity) {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.CREATE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setLong(3, entity.getEdition().getId());
            statement.setString(4, entity.getLanguage());
            statement.setObject(5, entity.getPublicationDate());
            statement.setFloat(6, entity.getPrice());
            statement.setInt(7, entity.getCount());
            if (statement.executeUpdate() > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        entity.setId(resultSet.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(SQLConstants.GET_PARTIAL_BOOK_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    BookMapper mapper = new BookMapper();
                    Book book = mapper.extractFromResultSet(resultSet);
                    return Optional.of(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> findByTitleAndAuthorsNames(String title, List<Author> authors) {
        StringBuilder stringNamesArray = new StringBuilder();
        stringNamesArray.append("{");
        for (Author author: authors) {
            stringNamesArray.append("\"")
                    .append(author.getName())
                    .append("\"")
                    .append(",");
        }
        stringNamesArray.deleteCharAt(stringNamesArray.length()-1);
        stringNamesArray.append("}");
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.GET_PARTIAL_BOOK_BY_TITLE_AND_AUTHORS)) {
            statement.setString(1, title);
            statement.setString(2, stringNamesArray.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    BookMapper mapper = new BookMapper();
                    Book book = mapper.extractFromResultSet(resultSet);
                    return Optional.of(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findByKeyWord(String keyWord, String sortBy, String sortType, int page) {
        List<Book> bookList = new ArrayList<>();
        String query = chooseSortingQuery(sortBy, sortType);
        try (PreparedStatement statement =
                     connection.prepareStatement(query)) {
            statement.setString(1, keyWord);
            statement.setString(2, keyWord);
            int offsetPosition = (page-1)*4;
            statement.setInt(3, 4);
            statement.setInt(4, offsetPosition);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    BookMapper mapper = new BookMapper();
                    Book book = mapper.extractFromResultSet(resultSet);
                    bookList.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public long getAmountOfBooks() {
        String query = "SELECT COUNT(*) FROM book";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLConstants.GET_ALL_BOOKS);
            while (resultSet.next()) {
                BookMapper mapper = new BookMapper();
                Book book = mapper.extractFromResultSet(resultSet);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public void update(Book entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQLConstants.UPDATE_PARTIAL_BOOK)) {
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setString(3, entity.getLanguage());
            statement.setLong(4, entity.getEdition().getId());
            statement.setObject(5, entity.getPublicationDate());
            statement.setFloat(6, entity.getPrice());
            statement.setInt(7, entity.getCount());
            statement.setLong(8, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement statement = connection.prepareStatement(SQLConstants.DELETE_BOOK)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAuthorship(Book book, Author author) {
        try (PreparedStatement statement = connection.prepareStatement(SQLConstants.SET_AUTHORSHIP)) {
            statement.setLong(1, author.getId());
            statement.setLong(2, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unSetAuthorship(Book book, Author author) {
        try (PreparedStatement statement = connection.prepareStatement(SQLConstants.UNSET_AUTHORSHIP)) {
            statement.setLong(1, book.getId());
            statement.setLong(2, author.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String chooseSortingQuery(String sortBy, String sortType) {
        String query;
        if (sortType.equals("dec")) {
            if (sortBy.equals("title")) {
                query = SQLConstants.GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_TITLE_DEC;
            } else if (sortBy.equals("author")) {
                query = SQLConstants.GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_AUTHOR_DEC;
            } else if (sortBy.equals("edition")) {
                query = SQLConstants.GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_EDITION_DEC;
            } else if (sortBy.equals("date")) {
                query = SQLConstants.GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_DATE_DEC;
            } else {
                query = SQLConstants.GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_ID_DEC;
            }
        } else {
            if (sortBy.equals("title")) {
                query = SQLConstants.GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_TITLE_INC;
            } else if (sortBy.equals("author")) {
                query = SQLConstants.GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_AUTHOR_INC;
            } else if (sortBy.equals("edition")) {
                query = SQLConstants.GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_EDITION_INC;
            } else if (sortBy.equals("date")) {
                query = SQLConstants.GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_DATE_INC;
            } else {
                query = SQLConstants.GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_ID_INC;
            }
        }
        return query;
    }
}

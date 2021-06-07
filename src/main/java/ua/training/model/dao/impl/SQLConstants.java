package ua.training.model.dao.impl;

public abstract class SQLConstants {

    public static final String CREATE_USER = "INSERT INTO users (login, password_hash, user_role, is_blocked)" +
            " VALUES (?, ?, ?::\"role\", ?)";
    public static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    public static final String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM users";
    public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

    public static final String CREATE_AUTHOR = "INSERT INTO author (full_name) VALUES (?)";
    public static final String GET_AUTHOR_BY_ID = "SELECT * FROM author WHERE id = ?";
    public static final String GET_AUTHOR_BY_NAME = "SELECT * FROM author WHERE full_name_ua = ? AND full_name_en = ?";
    public static final String GET_AUTHORS_BY_BOOK_ID = "SELECT * FROM authorship INNER JOIN author ON " +
            "author_id = author.id WHERE book_id = ?;";

    public static final String CREATE_EDITION = "INSERT INTO edition (edition_name_ua, edition_name_en) VALUES (?, ?)";
    public static final String GET_EDITION_BY_NAME = "SELECT * FROM edition WHERE edition_name_ua = ? AND edition_name_en = ?";

    public static final String CREATE_BOOK = "INSERT INTO book (title, description, edition_id, language, " +
            "publication_date, price, count) values (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_PARTIAL_BOOK = "UPDATE book SET (title, description, language, edition_id," +
            " publication_date, price, count) = (?, ?, ?, ?, ?, ?, ?) WHERE id = ?";
    public static final String GET_PARTIAL_BOOK_BY_ID = "SELECT * FROM book INNER JOIN edition ON " +
            "edition_id = edition.id WHERE book.id = ?;";
    public static final String GET_PARTIAL_BOOK_BY_TITLE_AND_AUTHORS = "SELECT * FROM book INNER JOIN edition ON" +
            " edition_id = edition.id WHERE title = ? AND (SELECT CAST(ARRAY(SELECT full_name FROM authorship " +
            "INNER JOIN author ON author_id = author.id WHERE book_id = book.id) AS VARCHAR)) = ?;";
    public static final String GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_ID_INC = "SELECT DISTINCT book.id, title, description, " +
            "language, edition_id, edition_name, publication_date, price, count FROM book INNER JOIN edition " +
            "ON edition_id = edition.id INNER JOIN authorship ON book.id = authorship.book_id INNER JOIN author " +
            "ON author_id = author.id WHERE full_name LIKE '%' || ? || '%' OR title LIKE '%' || ? || '%'" +
            "ORDER BY book.id LIMIT ? OFFSET ?;";
    public static final String GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_ID_DEC = "SELECT DISTINCT book.id, title, description, " +
            "language, edition_id, edition_name, publication_date, price, count FROM book INNER JOIN edition " +
            "ON edition_id = edition.id INNER JOIN authorship ON book.id = authorship.book_id INNER JOIN author " +
            "ON author_id = author.id WHERE full_name LIKE '%' || ? || '%' OR title LIKE '%' || ? || '%'" +
            "ORDER BY book.id DESC LIMIT ? OFFSET ?;";
    public static final String GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_TITLE_INC = "SELECT DISTINCT book.id, title, description, " +
            "language, edition_id, edition_name, publication_date, price, count FROM book INNER JOIN edition " +
            "ON edition_id = edition.id INNER JOIN authorship ON book.id = authorship.book_id INNER JOIN author " +
            "ON author_id = author.id WHERE full_name LIKE '%' || ? || '%' OR title LIKE '%' || ? || '%'" +
            "ORDER BY title LIMIT ? OFFSET ?;";
    public static final String GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_TITLE_DEC = "SELECT DISTINCT book.id, title, description, " +
            "language, edition_id, edition_name, publication_date, price, count FROM book INNER JOIN edition " +
            "ON edition_id = edition.id INNER JOIN authorship ON book.id = authorship.book_id INNER JOIN author " +
            "ON author_id = author.id WHERE full_name LIKE '%' || ? || '%' OR title LIKE '%' || ? || '%'" +
            "ORDER BY title DESC LIMIT ? OFFSET ?;";
    public static final String GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_EDITION_INC = "SELECT DISTINCT book.id, title, description, " +
            "language, edition_id, edition_name, publication_date, price, count FROM book INNER JOIN edition " +
            "ON edition_id = edition.id INNER JOIN authorship ON book.id = authorship.book_id INNER JOIN author " +
            "ON author_id = author.id WHERE full_name LIKE '%' || ? || '%' OR title LIKE '%' || ? || '%'" +
            "ORDER BY edition_name LIMIT ? OFFSET ?;";
    public static final String GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_EDITION_DEC = "SELECT DISTINCT book.id, title, description, " +
            "language, edition_id, edition_name, publication_date, price, count FROM book INNER JOIN edition " +
            "ON edition_id = edition.id INNER JOIN authorship ON book.id = authorship.book_id INNER JOIN author " +
            "ON author_id = author.id WHERE full_name LIKE '%' || ? || '%' OR title LIKE '%' || ? || '%'" +
            "ORDER BY edition_name DESC LIMIT ? OFFSET ?;";
    public static final String GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_DATE_INC = "SELECT DISTINCT book.id, title, description, " +
            "language, edition_id, edition_name, publication_date, price, count FROM book INNER JOIN edition " +
            "ON edition_id = edition.id INNER JOIN authorship ON book.id = authorship.book_id INNER JOIN author " +
            "ON author_id = author.id WHERE full_name LIKE '%' || ? || '%' OR title LIKE '%' || ? || '%'" +
            "ORDER BY publication_date LIMIT ? OFFSET ?;";
    public static final String GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_DATE_DEC = "SELECT DISTINCT book.id, title, description, " +
            "language, edition_id, edition_name, publication_date, price, count FROM book INNER JOIN edition " +
            "ON edition_id = edition.id INNER JOIN authorship ON book.id = authorship.book_id INNER JOIN author " +
            "ON author_id = author.id WHERE full_name LIKE '%' || ? || '%' OR title LIKE '%' || ? || '%'" +
            "ORDER BY publication_date DESC LIMIT ? OFFSET ?;";
    public static final String GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_AUTHOR_INC = "SELECT DISTINCT book.id, title, description, " +
            "language, edition_id, edition_name, publication_date, price, count, (CAST(ARRAY(SELECT full_name FROM author INNER JOIN authorship a ON author.id = a.author_id WHERE book_id = book.id ORDER BY full_name) AS VARCHAR)) FROM book INNER JOIN edition " +
            "ON edition_id = edition.id INNER JOIN authorship ON book.id = authorship.book_id INNER JOIN author " +
            "ON author_id = author.id WHERE full_name LIKE '%' || ? || '%' OR title LIKE '%' || ? || '%'" +
            "ORDER BY (CAST(ARRAY(SELECT full_name FROM author INNER JOIN authorship a ON author.id = a.author_id " +
            "WHERE book_id = book.id ORDER BY full_name) AS VARCHAR)) LIMIT ? OFFSET ?;";
    public static final String GET_PARTIAL_BOOKS_BY_KEYWORD_SORTED_BY_AUTHOR_DEC = "SELECT DISTINCT book.id, title, description, " +
            "language, edition_id, edition_name, publication_date, price, count, (CAST(ARRAY(SELECT full_name FROM author INNER JOIN authorship a ON author.id = a.author_id WHERE book_id = book.id ORDER BY full_name) AS VARCHAR)) FROM book INNER JOIN edition " +
            "ON edition_id = edition.id INNER JOIN authorship ON book.id = authorship.book_id INNER JOIN author " +
            "ON author_id = author.id WHERE full_name LIKE '%' || ? || '%' OR title LIKE '%' || ? || '%'" +
            "ORDER BY (CAST(ARRAY(SELECT full_name FROM author INNER JOIN authorship a ON author.id = a.author_id WHERE book_id = book.id ORDER BY full_name) AS VARCHAR)) DESC LIMIT ? OFFSET ?;";
    public static final String GET_ALL_BOOKS = "SELECT * FROM book INNER JOIN edition ON edition_id = edition.id";
    public static final String DELETE_BOOK = "DELETE FROM book WHERE id = ?";
    public static final String SET_AUTHORSHIP = "INSERT INTO authorship (author_id, book_id) VALUES (?, ?)";
    public static final String UNSET_AUTHORSHIP = "DELETE FROM authorship WHERE book_id = ? AND author_id = ?";
    public static final String UNSET_AUTHORSHIP_BY_BOOK_ID = "DELETE FROM authorship WHERE book_id = ?";

    public static final String CREATE_ORDER = "INSERT INTO orders (user_id, book_id, start_date, end_date, status) " +
            "VALUES (?, ?, ?, ?, ?::\"order_status\")";
    public static final String UPDATE_ORDER = "UPDATE orders SET (user_id, book_id, start_date, end_date, status) = " +
            "(?, ?, ?, ?, ?::\"order_status\") WHERE id = ?";
    public static final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?";
    public static final String GET_ALL_ORDERS = "SELECT * FROM orders";

    public static String crateGetBookByKeyWord(String inputQuery, String sort, String sortType, int page) {
        StringBuilder result = new StringBuilder(inputQuery);
        if (sort.equals("title")) {
            result.append(" ").append("ORDER BY title");
        } else if (sort.equals("author")) {
            result.append(" ").append("ORDER BY ");
        } else if (sort.equals("edition")) {
            result.append(" ").append("ORDER BY edition_name");
        } else if (sort.equals("date")) {
            result.append(" ").append("ORDER BY publication_date");
        } else {
            result.append(" ").append("ORDER BY id");
        }
        if (sortType.equals("dec")) {
            result.append(" ").append("DESC");
        }
        int offsetPosition = (page - 1) * 7;
        result.append(" ")
                .append("LIMIT").append(" ")
                .append(7).append(" ")
                .append("OFFSET").append(" ")
                .append(offsetPosition);
        return result.toString();
    }
}

package ua.training.model.dao.mapper;

import ua.training.model.entity.Book;
import ua.training.model.entity.Edition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookMapper implements ObjectMapper<Book> {

    @Override
    public Book extractFromResultSet(ResultSet resultSet) throws SQLException {
        EditionMapper editionMapper = new EditionMapper();
        Edition edition = editionMapper.extractFromResultSetWithId(resultSet, "edition_id");
        return new Book.Builder()
                .id(resultSet.getInt("id"))
                .title(resultSet.getString("title_ua"))
                .anotherLanguage(resultSet.getString("title_en"))
                .description(resultSet.getString("description"))
                .description(resultSet.getString("description"))
                .language(resultSet.getString("language"))
                .edition(edition)
                .publicationDate(LocalDate.parse(resultSet.getString("publication_date")))
                .price(resultSet.getFloat("price"))
                .count(resultSet.getInt("count"))
                .build();
    }
}

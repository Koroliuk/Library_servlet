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
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .language(resultSet.getString("language"))
                .edition(edition)
                .publicationDate(LocalDate.parse(resultSet.getString("publication_date")))
                .price(resultSet.getFloat("price"))
                .count(resultSet.getInt("count"))
                .build();
    }

    public Book extractFromResultSetSting(String string) {
        String stringParams = string.substring(1, string.length()-1);
        String[] params = stringParams.split(",");
        Edition edition = new Edition.Builder()
                .id(Long.parseLong(params[3]))
                .name(params[4])
                .build();
        return new Book.Builder()
                .id(Long.parseLong(params[0]))
                .title(params[1])
                .description(params[2])
                .edition(edition)
                .language(params[5])
                .publicationDate(LocalDate.parse(params[6]))
                .price(Float.parseFloat(params[7]))
                .count(Integer.parseInt(params[8]))
                .build();
    }
}

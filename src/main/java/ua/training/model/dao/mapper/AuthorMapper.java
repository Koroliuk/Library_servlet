package ua.training.model.dao.mapper;

import ua.training.model.entity.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements ObjectMapper<Author> {

    @Override
    public Author extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Author.Builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("full_name"))
                .build();
    }

    public Author extractFromResultSetWithId(ResultSet resultSet, String id) throws SQLException {
        return new Author.Builder()
                .id(resultSet.getInt(id))
                .name(resultSet.getString("full_name"))
                .build();
    }
}

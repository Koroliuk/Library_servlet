package ua.training.model.dao;

import ua.training.model.entity.Edition;

import java.util.Optional;

public interface EditionDao extends GenericDao<Edition> {
    Optional<Edition> findByName(String name);
}

package com.service;

import com.model.Town;

import java.util.List;

public interface TownService {
    List<Town> getListOfTowns();

    Town save(Town town);

    Town getById(Long id);

    void update(Town town);

    void delete(Town town);
}

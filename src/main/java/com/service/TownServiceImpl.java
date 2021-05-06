package com.service;

import com.model.Town;
import com.repository.TownRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Log4j2
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Override
    public List<Town> getListOfTowns() {
        log.info("IN TownServiceImpl getAll");
        return townRepository.findAll();
    }

    @Override
    public Town save(Town town) {
        log.info("IN TownServiceImpl update " + town);
        return townRepository.save(town);
    }

    @Override
    public Town getById(Long id) {
        log.info("IN TownServiceImpl getById " + id);
        return townRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void update(Town town) {
        log.info("IN TownServiceImpl update " + town);
        townRepository.save(town);
    }

    @Override
    public void delete(Town town) {
        log.info("IN TownServiceImpl delete " + town);
        townRepository.delete(town);
    }
}

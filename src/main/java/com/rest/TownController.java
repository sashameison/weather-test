package com.rest;

import com.model.Town;
import com.service.TownServiceImpl;
import com.util.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/towns")
public class TownController {

    private final TownServiceImpl townService;

    private final Template restTemplate;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    public TownController(TownServiceImpl townService, Template restTemplate) {
        this.townService = townService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("{name}")
    public ResponseEntity<Town> getTemperature(@PathVariable("name") String name) {
        Town town = restTemplate.getRestTemplate()
                .getForObject("http://api.openweathermap.org/data/2.5/weather?q=" + name + "&appid=" + apiKey, Town.class);
        return new ResponseEntity<>(town, HttpStatus.OK);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Town>> getAllTowns() {
        List<Town> towns = townService.getListOfTowns();

        if (towns.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(towns, HttpStatus.OK);
    }


    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Town> getTown(@PathVariable("id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Town town = townService.getById(id);
            return new ResponseEntity<>(town, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Town> createTown(@RequestBody Town town) {

        if (town == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Town savedTown = townService.save(town);
        return new ResponseEntity<>(savedTown, HttpStatus.OK);
    }


    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Town> updateTown(@RequestBody Town town) {

        if (town == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        townService.update(town);

        return new ResponseEntity<>(town, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Town> deleteTownById(@PathVariable("id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Town town = townService.getById(id);

        if (town == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        townService.delete(town);
        return new ResponseEntity<>(town, HttpStatus.OK);
    }

}

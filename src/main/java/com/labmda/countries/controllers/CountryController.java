package com.labmda.countries.controllers;

import com.labmda.countries.models.Country;
import com.labmda.countries.repositories.CountryRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryRepositories countryid;

    private List<Country> findCountries(List<Country> myList, CheckCountry lambda)
    {
        List <Country> tempList = new ArrayList<>();

        for (Country c:myList)
        {
            if (lambda.test(c))
            {
                tempList.add(c);
            }
        }

        return tempList;
    }

    // http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllNames()
    {
        List<Country> countryList = new ArrayList<>();
        countryid.findAll().iterator().forEachRemaining(countryList::add);

        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    //http://localhost:2019/names/start/u
    @GetMapping(value = "/names/start/{var}", produces = {"application/json"})
    public ResponseEntity<?> listByStart(@PathVariable char var)
    {
        List<Country> countryList = new ArrayList<>();
        countryid.findAll().iterator().forEachRemaining(countryList::add);

        List<Country> returnList = findCountries(countryList, (c) -> Character.toLowerCase(c.getName().charAt(0)) == Character.toLowerCase(var));
        return new ResponseEntity<>(returnList, HttpStatus.OK);
    }

    // http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> listPopulation()
    {
        long total = 0;
        List<Country> countryList = new ArrayList<>();
        countryid.findAll().iterator().forEachRemaining(countryList::add);

        for (Country c:countryList )
        {
            total = total + c.getPopulation();
        }
        return new ResponseEntity<>(("The total population is: " + total), HttpStatus.OK);
    }

    // http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> listMinPopulation()
    {
        List<Country> countryList = new ArrayList<>();
        countryid.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> ((int) c1.getPopulation() - (int) c2.getPopulation()));
        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

    // http://localhost:2019/population/max
    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> listMaxPopulation()
    {
        List<Country> countryList = new ArrayList<>();
        countryid.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> ((int) c2.getPopulation() - (int) c1.getPopulation()));
        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

    // http://localhost:2019/population/median
    @GetMapping(value = "/population/median", produces = {"application/json"})
    public ResponseEntity<?> listMedianPopulation()
    {
        List<Country> countryList = new ArrayList<>();
        countryid.findAll().iterator().forEachRemaining(countryList::add);

        int length = countryList.size();
        if (length % 2 == 0) // even
        {
            return new ResponseEntity<>(countryList.get(length/2), HttpStatus.OK);
        } else { // odd
            int middle1 = (int) (length/2 + .5);
            int middle2 = (int) (length/2 - .5);
            List <Country> middleTwoCountries = new ArrayList<>();
            middleTwoCountries.add(countryList.get(middle1));
            middleTwoCountries.add(countryList.get(middle2));
            return new ResponseEntity<>(middleTwoCountries, HttpStatus.OK);
        }
    }
}
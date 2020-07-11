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

        List<Country> returnList = findCountries(countryList, (e) -> Character.toLowerCase(e.getName().charAt(0)) == Character.toLowerCase(var));
        return new ResponseEntity<>(returnList, HttpStatus.OK);
    }

}

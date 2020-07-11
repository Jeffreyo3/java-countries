package com.labmda.countries.repositories;

import com.labmda.countries.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepositories extends CrudRepository<Country, Long>
{
}

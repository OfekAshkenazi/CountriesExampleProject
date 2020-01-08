package com.ofek.countries.data.countries;

import com.ofek.countries.data.countries.managers.CountriesApiManager;
import com.ofek.countries.domain.objects.DomainCountryObj;
import com.ofek.countries.domain.repositories.CountriesRepo;

import java.util.List;

import io.reactivex.Single;

/**
 * the repository should apply the data layer's logic if any logic exists .
 * as it a very simple app without any complicated logic it might seems to be unnecessary layer
 * for example, in the repository I would add saving results from the api to cache file. or apply a sophisticated retry logic.
 * if the was any logic I would add unit tests for this class, but because there's no logic in here and it's an example app I didn't write test for this class
 */
public class CountriesRepoImp implements CountriesRepo {

    private final CountriesApiManager countriesApiManager;

    public CountriesRepoImp(CountriesApiManager countriesApiManager) {
        this.countriesApiManager = countriesApiManager;
    }

    @Override
    public Single<List<DomainCountryObj>> getCountriesList() {
        return countriesApiManager.getCountriesList();
    }

    @Override
    public Single<DomainCountryObj> getCountryByCode(String countryCode) {
        return countriesApiManager.getCountryByCode(countryCode);
    }
}

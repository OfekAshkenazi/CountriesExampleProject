package com.ofek.countries.data.countries.managers;

import com.ofek.countries.data.countries.CountryObjectsMappers;
import com.ofek.countries.domain.objects.DomainCountryObj;

import java.util.List;

import io.reactivex.Single;

/**
 * an api manager which uses restcountries.eu api to get information about countries
 * the manager layer has api specific implementation, in this case restcountries.eu,
 * if for example I would like to change the api which I receives the countries from
 * it would only requires a new implementation of the {@link CountriesApiManager} which is specific to the new api.
 * all other classes can stay untouched.
 */
public class CountriesApiManagerImp implements CountriesApiManager {

    private final RestCountriesEuService restCountriesEuService;

    public CountriesApiManagerImp(RestCountriesEuService restCountriesEuService) {
        this.restCountriesEuService = restCountriesEuService;
    }

    @Override
    public Single<List<DomainCountryObj>> getCountriesList() {
        return null;
    }

    @Override
    public Single<DomainCountryObj> getCountryByCode(String countryCode) {
        return restCountriesEuService.getCountryByCountryCode(countryCode.toLowerCase()).map(CountryObjectsMappers::mapCountryDTOToDomainCountry);
    }
}

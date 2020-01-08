package com.ofek.countries.data.countries.managers;

import com.ofek.countries.domain.objects.DomainCountryObj;

import java.util.List;

import io.reactivex.Single;

public interface CountriesApiManager {

    Single<List<DomainCountryObj>> getCountriesList();

    Single<DomainCountryObj> getCountryByCode(String countryCode);
}

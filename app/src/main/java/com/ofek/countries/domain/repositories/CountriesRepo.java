package com.ofek.countries.domain.repositories;

import com.ofek.countries.domain.objects.DomainCountryObj;

import java.util.List;

import io.reactivex.Single;

public interface CountriesRepo {
    Single<List<DomainCountryObj>> getCountriesList();

    Single<DomainCountryObj> getCountryByCode(String countryCode);
}

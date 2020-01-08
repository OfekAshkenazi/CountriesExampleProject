package com.ofek.countries.domain.di.usecases;

import com.ofek.countries.domain.objects.DomainCountryObj;
import com.ofek.countries.domain.repositories.CountriesRepo;
import com.ofek.countries.domain.usecases.GetCountriesList;
import com.ofek.countries.domain.usecases.GetCountryByCode;

import java.util.List;

import io.reactivex.SingleTransformer;

public interface UseCasesProvider {
    GetCountriesList provideGetCountriesList(SingleTransformer<List<DomainCountryObj>, List<DomainCountryObj>> transformer, CountriesRepo countriesRepo);

    GetCountryByCode provideGetCountryByCode(SingleTransformer<DomainCountryObj, DomainCountryObj> transformer, CountriesRepo countriesRepo);
}

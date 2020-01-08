package com.ofek.countries.domain.di.usecases;

import com.ofek.countries.domain.objects.DomainCountryObj;
import com.ofek.countries.domain.repositories.CountriesRepo;
import com.ofek.countries.domain.usecases.GetCountriesList;
import com.ofek.countries.domain.usecases.GetCountryByCode;

import java.util.List;

import io.reactivex.SingleTransformer;

class UseCasesProviderImp implements UseCasesProvider {
    @Override
    public GetCountriesList provideGetCountriesList(SingleTransformer<List<DomainCountryObj>, List<DomainCountryObj>> transformer, CountriesRepo countriesRepo) {
        return new GetCountriesList(transformer, countriesRepo);
    }

    @Override
    public GetCountryByCode provideGetCountryByCode(SingleTransformer<DomainCountryObj, DomainCountryObj> transformer, CountriesRepo countriesRepo) {
        return new GetCountryByCode(transformer,countriesRepo);
    }
}

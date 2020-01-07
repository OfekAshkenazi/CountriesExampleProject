package com.ofek.countries.domain.usecases;

import com.ofek.countries.domain.common.BseSingleUseCase;
import com.ofek.countries.domain.objects.DomainCountryObj;
import com.ofek.countries.domain.repositories.CountriesRepo;

import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;

public class GetCountriesList extends BseSingleUseCase<DomainCountryObj> {


    private final CountriesRepo countriesRepo;

    protected GetCountriesList(SingleTransformer<DomainCountryObj, DomainCountryObj> transformer, CountriesRepo countriesRepo) {
        super(transformer);
        this.countriesRepo = countriesRepo;
    }

    @Override
    protected Single<DomainCountryObj> createSourceSingle(Map<String, Object> params) {
        return null;
    }
}

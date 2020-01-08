package com.ofek.countries.domain.usecases;

import com.ofek.countries.domain.common.BaseSingleUseCase;
import com.ofek.countries.domain.objects.DomainCountryObj;
import com.ofek.countries.domain.repositories.CountriesRepo;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;

public class GetCountriesList extends BaseSingleUseCase<List<DomainCountryObj>> {


    private final CountriesRepo countriesRepo;

    public GetCountriesList(SingleTransformer<List<DomainCountryObj>, List<DomainCountryObj>> transformer, CountriesRepo countriesRepo) {
        super(transformer);
        this.countriesRepo = countriesRepo;
    }

    @Override
    protected Single<List<DomainCountryObj>> createSourceSingle(Map<String, Object> params) {
        return countriesRepo.getCountriesList();
    }

    public Single<List<DomainCountryObj>> getCountries(){
        return createStream(null);
    }
}

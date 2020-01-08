package com.ofek.countries.domain.usecases;

import com.ofek.countries.domain.common.BaseSingleUseCase;
import com.ofek.countries.domain.objects.DomainCountryObj;
import com.ofek.countries.domain.repositories.CountriesRepo;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;

/**
 * A use case to get a full information about a country by a ISO - 2/3 letters country code
 */
public class GetCountryByCode extends BaseSingleUseCase<DomainCountryObj> {

    private static final String CODE_KEY = "country_code";
    private final CountriesRepo countriesRepo;
    
    protected GetCountryByCode(SingleTransformer<DomainCountryObj, DomainCountryObj> transformer, CountriesRepo countriesRepo) {
        super(transformer);
        this.countriesRepo = countriesRepo;
    }

    @Override
    protected Single<DomainCountryObj> createSourceSingle(Map<String, Object> params) {
        // the original country code inserted to the map in the "getCountryByCode()" method in this use case
        String countryCode = (String) params.get(CODE_KEY);
        return countriesRepo.getCountryByCode(countryCode);
    }

    public Single<DomainCountryObj> getCountryByCode(String countryCode) {
        Map<String, Object> params = new HashMap<>(1);
        params.put(CODE_KEY,countryCode);
        return createStream(params);
    }

}

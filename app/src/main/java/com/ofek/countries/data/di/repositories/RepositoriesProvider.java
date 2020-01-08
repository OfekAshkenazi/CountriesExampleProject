package com.ofek.countries.data.di.repositories;

import com.ofek.countries.data.countries.managers.CountriesApiManager;
import com.ofek.countries.domain.repositories.CountriesRepo;

/**
 * see {@link com.ofek.countries.domain.di.usecases.UseCasesProvider} for explanation about the provider classes
 */
public interface RepositoriesProvider {

    CountriesRepo provideCountriesRepo(CountriesApiManager countriesApiManager);

}

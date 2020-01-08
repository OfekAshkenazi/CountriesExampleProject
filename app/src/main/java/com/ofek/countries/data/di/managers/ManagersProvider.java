package com.ofek.countries.data.di.managers;

import com.ofek.countries.data.countries.managers.CountriesApiManager;

/**
 * see {@link com.ofek.countries.domain.di.usecases.UseCasesProvider} for explanation about the provider classes
 */
public interface ManagersProvider {
    CountriesApiManager provideApiManager();
}

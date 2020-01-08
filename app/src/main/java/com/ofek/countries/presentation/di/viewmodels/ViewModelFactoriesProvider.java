package com.ofek.countries.presentation.di.viewmodels;

import androidx.lifecycle.ViewModelProvider;

import com.ofek.countries.domain.usecases.GetCountriesList;
import com.ofek.countries.domain.usecases.GetCountryByCode;

/**
 * see {@link com.ofek.countries.domain.di.usecases.UseCasesProvider} for explanation about the provider classes
 */
public interface ViewModelFactoriesProvider {


    ViewModelProvider.Factory provideCountriesListVMFactory(GetCountriesList getCountriesList);

    ViewModelProvider.Factory provideBoardingListVMFactry(GetCountryByCode getCountryByCode);
}

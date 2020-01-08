package com.ofek.countries.presentation.di.viewmodels;

import androidx.lifecycle.ViewModelProvider;

import com.ofek.countries.domain.usecases.GetCountriesList;
import com.ofek.countries.domain.usecases.GetCountryByCode;
import com.ofek.countries.presentation.boarders_list.BoardingCountriesScreenViewModel;
import com.ofek.countries.presentation.countries_list.CountriesListViewModel;

/**
 * see {@link com.ofek.countries.domain.di.usecases.UseCasesProvider} for explanation about the provider classes
 */
class ViewModelFactoriesProviderImp implements ViewModelFactoriesProvider {
    @Override
    public ViewModelProvider.Factory provideCountriesListVMFactory(GetCountriesList getCountriesList) {
        return new CountriesListViewModel.Factrory(getCountriesList);
    }

    @Override
    public ViewModelProvider.Factory provideBoardingListVMFactry(GetCountryByCode getCountryByCode) {
        return new BoardingCountriesScreenViewModel.Factrory(getCountryByCode);
    }
}

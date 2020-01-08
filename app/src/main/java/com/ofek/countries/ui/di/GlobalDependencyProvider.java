package com.ofek.countries.ui.di;

import androidx.lifecycle.ViewModelProvider;

import com.ofek.countries.data.countries.managers.CountriesApiManager;
import com.ofek.countries.data.di.managers.ManagersModule;
import com.ofek.countries.data.di.repositories.RepositoriesModule;
import com.ofek.countries.domain.di.common.DomainCommonModule;
import com.ofek.countries.domain.di.usecases.UseCasesModule;
import com.ofek.countries.domain.objects.DomainCountryObj;
import com.ofek.countries.domain.repositories.CountriesRepo;
import com.ofek.countries.domain.usecases.GetCountriesList;
import com.ofek.countries.domain.usecases.GetCountryByCode;
import com.ofek.countries.presentation.di.viewmodels.ViewModelFactoriesModule;

import java.util.List;

import io.reactivex.SingleTransformer;

/**
 * creates the view model factories instances and provides the dependencies to the factories
 * by looking at this class you can see the dependency tree of the whole application
 */
public class GlobalDependencyProvider {

    public static ViewModelProvider.Factory getCountriesListViewModelFactory() {
        CountriesApiManager apiManager = ManagersModule.getManagersProvider().provideApiManager();
        CountriesRepo countriesRepo = RepositoriesModule.getRepositoriesProvider().provideCountriesRepo(apiManager);
        SingleTransformer<List<DomainCountryObj>,List<DomainCountryObj>> transformer = DomainCommonModule.getDomainCommonProvider().getSingleTransformer();
        GetCountriesList getCountriesList = UseCasesModule.getUseCasesProvider().provideGetCountriesList(transformer,countriesRepo);
        ViewModelProvider.Factory facory = ViewModelFactoriesModule.getFactoriesProvider().provideCountriesListVMFactory(getCountriesList);
        return facory;
    }

    public static ViewModelProvider.Factory getBoardingListViewModelFactory() {
        CountriesApiManager apiManager = ManagersModule.getManagersProvider().provideApiManager();
        CountriesRepo countriesRepo = RepositoriesModule.getRepositoriesProvider().provideCountriesRepo(apiManager);
        SingleTransformer<DomainCountryObj, DomainCountryObj> transformer = DomainCommonModule.getDomainCommonProvider().getSingleTransformer();
        GetCountryByCode getCountryByCode = UseCasesModule.getUseCasesProvider().provideGetCountryByCode(transformer,countriesRepo) ;
        ViewModelProvider.Factory factory = ViewModelFactoriesModule.getFactoriesProvider().provideBoardingListVMFactry(getCountryByCode);
        return factory;
    }
}

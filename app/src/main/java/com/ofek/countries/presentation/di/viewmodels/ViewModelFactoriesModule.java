package com.ofek.countries.presentation.di.viewmodels;

/**
 * see {@link com.ofek.countries.domain.di.usecases.UseCasesModule} for explanation about the module classes
 */
public class ViewModelFactoriesModule {

    private static ViewModelFactoriesProvider managersProvider;

    public static void injectProvider(ViewModelFactoriesProvider provider) {
        managersProvider = provider;
    }

    public static ViewModelFactoriesProvider getFactoriesProvider(){
        if (managersProvider == null) {
            injectProvider(new ViewModelFactoriesProviderImp());
        }
        return managersProvider;
    }
}

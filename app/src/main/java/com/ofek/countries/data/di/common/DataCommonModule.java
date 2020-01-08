package com.ofek.countries.data.di.common;

/**
 * see {@link com.ofek.countries.domain.di.usecases.UseCasesModule} for explanation about the module classes
 */
public class DataCommonModule {

    private static DataCommonProvider managersProvider;

    public static void injectProvider(DataCommonProvider provider) {
        managersProvider = provider;
    }

    public static DataCommonProvider getManagersProvider(){
        if (managersProvider == null) {
            injectProvider(new DataCommonProviderImp());
        }
        return managersProvider;
    }
}

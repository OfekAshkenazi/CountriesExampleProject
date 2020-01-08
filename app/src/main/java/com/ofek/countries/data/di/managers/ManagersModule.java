package com.ofek.countries.data.di.managers;
/**
 * see {@link com.ofek.countries.domain.di.usecases.UseCasesModule} for explanation about the module classes
 */

public class ManagersModule {

    private static ManagersProvider managersProvider;

    public static void injectProvider(ManagersProvider provider) {
        managersProvider = provider;
    }

    public static ManagersProvider getManagersProvider(){
        if (managersProvider == null) {
            injectProvider(new ManagersProviderImp());
        }
        return managersProvider;
    }
}

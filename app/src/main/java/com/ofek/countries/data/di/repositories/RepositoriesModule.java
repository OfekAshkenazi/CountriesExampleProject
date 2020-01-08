package com.ofek.countries.data.di.repositories;
/**
 * see {@link com.ofek.countries.domain.di.usecases.UseCasesModule} for explanation about the module classes
 */
public class RepositoriesModule {
    
    private static RepositoriesProvider repositoriesProvider;
    
    public static void injectProvider(RepositoriesProvider provider) {
        repositoriesProvider = provider;
    }
    
    public static RepositoriesProvider getRepositoriesProvider(){
        if (repositoriesProvider == null) {
            injectProvider(new RepositoriesProviderImp());
        }
        return repositoriesProvider;
    }
}

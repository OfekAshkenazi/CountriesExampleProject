package com.ofek.countries.domain.di.usecases;

/**
 * the class which  holds the provider instance and responsible for the dependency injection
 * changing the provider instance by the "injectProvider" method allows control on the dependency injection by the provider
 * different providers will be used for different purposes(i.e test provider which provides mocks)
 */
public class UseCasesModule {
    private static UseCasesProvider useCasesProvider;

    /**
     * this method allows injection of costume provider such as test provider
     * @param provider
     */
    public static void injectProvider(UseCasesProvider provider) {
        useCasesProvider = provider;
    }

    /**
     * returns the current use cases provider, if use cases provider not already injected the method injects a default provider
     * @return {@link UseCasesProvider} instance
     */
    public static UseCasesProvider getUseCasesProvider(){
        if (useCasesProvider == null) {
            injectProvider(new UseCasesProviderImp());
        }
        return useCasesProvider;
    }
}

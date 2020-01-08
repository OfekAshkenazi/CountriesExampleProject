package com.ofek.countries.domain.di.common;

/**
 * the class which  holds the provider instance and responsible for the dependency injection
 * changing the provider instance by the "injectProvider" method allows control on the dependency injection by the provider
 * different providers will be used for different purposes(i.e test provider which provides mocks)
 */
public class DomainCommonModule {
    private static DomainCommonProvider domainCommonProvider;
    /**
     * returns the current domain common provider, if use domain common provider not already injected the method injects a default provider
     * @return {@link DomainCommonProvider} instance
     */
    public static void injectProvider(DomainCommonProvider provider) {
        domainCommonProvider = provider;
    }

    /**
     * returns the current domain common provider, if domain common provider not already injected the method injects a default provider
     * @return {@link DomainCommonProvider} instance
     */
    public static DomainCommonProvider getDomainCommonProvider() {
        if (domainCommonProvider == null) {
            injectProvider(new DomainCommonProviderImp());
        }
        return domainCommonProvider;
    }
}

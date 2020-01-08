package com.ofek.countries.domain.usecases;

import com.ofek.countries.domain.di.DomainCommonProviderTestImp;
import com.ofek.countries.domain.di.common.DomainCommonModule;
import com.ofek.countries.domain.objects.DomainCountryObj;
import com.ofek.countries.domain.repositories.CountriesRepo;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import io.reactivex.Single;

/**
 * as it is an example app I'll write only one example unit test for this use case
 */
@RunWith(JUnit4.class)
public class GetCountryByCodeTests {


    @BeforeClass
    public static void prepareTests(){
        // prepares the dependency injection environment requires for the tests
        DomainCommonModule.injectProvider(new DomainCommonProviderTestImp());
    }

    /**
     * an example unit test for this use case
     */
    @Test
    public void verify_country_code_parameter_transferred_to_the_repository(){

        CountriesRepo mockedCountriesRepo = Mockito.mock(CountriesRepo.class);
        GetCountryByCode useCaseUnderTest = new GetCountryByCode(DomainCommonModule.getDomainCommonProvider().getSingleTransformer(),mockedCountriesRepo);

        String exampleCountryCode = "IL" ;
        Mockito.when(mockedCountriesRepo.getCountryByCode(exampleCountryCode)).thenReturn(Single.just(new DomainCountryObj()));
        useCaseUnderTest.getCountryByCode(exampleCountryCode);
        // verifies the method in the repository called once with the example country code as parameter
        Mockito.verify(mockedCountriesRepo,Mockito.times(1)).getCountryByCode(exampleCountryCode);
    }
}

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

import java.util.List;

import io.reactivex.Single;
/**
 * as it is an example app I'll write only one example unit test for this use case
 */
@RunWith(JUnit4.class)
public class GetCountriesListFragmentTests {

    @BeforeClass
    public static void prepareTests(){
        // prepares the dependency injection environment requires for the tests
        DomainCommonModule.injectProvider(new DomainCommonProviderTestImp());
    }

    /**
     * an example unit test for this use case
     */
    @Test
    public void verify_no_errors_when_item_emitted(){
        CountriesRepo repo = Mockito.mock(CountriesRepo.class);
        GetCountriesList useCaseUnderTest = new GetCountriesList(DomainCommonModule.getDomainCommonProvider().getSingleTransformer(),repo);
        List<DomainCountryObj> countriesListMock  = Mockito.mock(List.class);

        Mockito.when(repo.getCountriesList()).thenReturn(Single.just(countriesListMock));

        useCaseUnderTest.getCountries().test().assertValue(countriesListMock).assertNoErrors();
    }
}

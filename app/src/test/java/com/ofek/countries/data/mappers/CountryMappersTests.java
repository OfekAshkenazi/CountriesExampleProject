package com.ofek.countries.data.mappers;


import com.ofek.countries.data.countries.CountryObjectsMappers;
import com.ofek.countries.data.countries.dto.CountryDTO;
import com.ofek.countries.domain.objects.DomainCountryObj;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * an example unit test class for mapping methods in {@link com.ofek.countries.data.countries.CountryObjectsMappers}
 */
public class CountryMappersTests {


    /**
     * an example unit test for this mapper
     */
    @Test
    public void verify_dto_country_mapped_correctly(){
        CountryDTO expected = new CountryDTO();
        expected.setName("israel");
        expected.setNativeName("ישראל");
        expected.setArea(20123123d);
        List<String> dummyBordersList = new ArrayList<>();
        expected.setBorders(dummyBordersList);

        DomainCountryObj actual = CountryObjectsMappers.mapCountryDTOToDomainCountry(expected);

        Assert.assertEquals(actual.getArea(),expected.getArea(),0);
        Assert.assertEquals(actual.getBorderCountries(),expected.getBorders());
        Assert.assertEquals(actual.getEnglishName(),expected.getName());
        Assert.assertEquals(actual.getNativeName(),expected.getNativeName());

    }
}

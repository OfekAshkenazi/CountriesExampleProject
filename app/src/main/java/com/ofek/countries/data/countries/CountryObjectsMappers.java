package com.ofek.countries.data.countries;

import com.ofek.countries.data.countries.dto.CountryDTO;
import com.ofek.countries.domain.objects.DomainCountryObj;

public class CountryObjectsMappers {
    /**
     * a mapper to map dto object to domain object
     * @param countryDTO
     * @return
     */
    public static DomainCountryObj mapCountryDTOToDomainCountry(CountryDTO countryDTO) {
        DomainCountryObj countryObj = new DomainCountryObj();
        countryObj.setArea(countryDTO.getArea());
        countryObj.setEnglishName(countryDTO.getName());
        countryObj.setNativeName(countryDTO.getNativeName());
        countryObj.setBorderCountries(countryDTO.getBorders());
        return countryObj;
    }
}

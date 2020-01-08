package com.ofek.countries.presentation.mappers;

import com.ofek.countries.domain.objects.DomainCountryObj;
import com.ofek.countries.presentation.objects.UiCountry;

public class UiCountryMappers {


    public static UiCountry mapDomainCountryToUiCountry(DomainCountryObj domainCountryObj) {
        UiCountry uiCountry = new UiCountry();
        uiCountry.setArea(domainCountryObj.getArea());
        uiCountry.setBoarders(domainCountryObj.getBorderCountries());
        uiCountry.setEnglishName(domainCountryObj.getEnglishName());
        uiCountry.setNativeName(domainCountryObj.getNativeName());
        return uiCountry;
    }
}

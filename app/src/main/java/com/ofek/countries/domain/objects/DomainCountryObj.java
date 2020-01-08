package com.ofek.countries.domain.objects;

import java.util.List;

public class DomainCountryObj {

    private String englishName;
    private String nativeName;
    private Double area;
    private List<String> borderCountries;

    public List<String> getBorderCountries() {
        return borderCountries;
    }

    public void setBorderCountries(List<String> borderCountries) {
        this.borderCountries = borderCountries;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }
}

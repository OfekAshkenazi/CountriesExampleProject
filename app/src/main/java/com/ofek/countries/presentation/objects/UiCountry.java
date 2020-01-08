package com.ofek.countries.presentation.objects;

import java.util.List;

public class UiCountry {
    private String englishName;
    private String nativeName;
    private double area;
    private List<String> boarders;

    public String getEnglishName() {
        return englishName;
    }

    public UiCountry setEnglishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public String getNativeName() {
        return nativeName;
    }

    public UiCountry setNativeName(String nativeName) {
        this.nativeName = nativeName;
        return this;
    }

    public double getArea() {
        return area;
    }

    public UiCountry setArea(double area) {
        this.area = area;
        return this;
    }

    public List<String> getBoarders() {
        return boarders;
    }

    public UiCountry setBoarders(List<String> boarders) {
        this.boarders = boarders;
        return this;
    }
}

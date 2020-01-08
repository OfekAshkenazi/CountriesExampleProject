package com.ofek.countries.presentation.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UiCountry implements Parcelable {
    private String englishName;
    private String nativeName;
    private double area;
    private List<String> boarders;

    protected UiCountry(Parcel in) {
        englishName = in.readString();
        nativeName = in.readString();
        area = in.readDouble();
        boarders = in.createStringArrayList();
    }

    public UiCountry() {
    }

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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(englishName);
        dest.writeString(nativeName);
        dest.writeDouble(area);
        dest.writeStringList(boarders);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UiCountry> CREATOR = new Creator<UiCountry>() {
        @Override
        public UiCountry createFromParcel(Parcel in) {
            return new UiCountry(in);
        }

        @Override
        public UiCountry[] newArray(int size) {
            return new UiCountry[size];
        }
    };

}

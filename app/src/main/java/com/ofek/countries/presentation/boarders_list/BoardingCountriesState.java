package com.ofek.countries.presentation.boarders_list;

import androidx.annotation.Nullable;

import com.ofek.countries.domain.objects.DomainCountryObj;

import java.util.ArrayList;
import java.util.List;

/**
 * state instances should be immutable, thus all it's members are final.
 * in order to change values in the state a new instance should be created by the builder class
 */
public class BoardingCountriesState {
    private final String countryName;
    @Nullable
    private final List<DomainCountryObj> countriesList;
    private final boolean loading;

    private BoardingCountriesState(String countryName, List<DomainCountryObj> countriesList, boolean loading) {
        this.countryName = countryName;
        this.countriesList = countriesList;
        this.loading = loading;
    }

    /**
     * initializes the first state with default values
     * @return
     */
    public static BoardingCountriesState createDefualtState() {
        return new BoardingCountriesState("",null,false);
    }


    /**
     * creates a new builder instance from the current instance
     * @return
     */
    public Builder newBuilder(){
        return new Builder(this);
    }

    public String getCountryName() {
        return countryName;
    }

    public List<DomainCountryObj> getCountriesList() {
        if (countriesList != null) {
            // in order the maintain the countries list immutable we're using a copy
            return new ArrayList<>(countriesList);
        } else {
            return null;
        }
    }

    public boolean isLoading() {
        return loading;
    }


    /**
     * the builder is the only way to create a new state as new state should always copy the values from the previous state and change only whats necessary
     */
    public static class Builder {
        private String countryName;
        private List<DomainCountryObj> countriesList;
        private boolean loading;

        private Builder(BoardingCountriesState toCopy) {
            this.countriesList = toCopy.countriesList;
            this.countryName = toCopy.countryName;
            this.loading = toCopy.loading;
        }

        public Builder setCountryName(String countryName) {
            this.countryName = countryName;
            return this;
        }

        public Builder setCountriesList(List<DomainCountryObj> countriesList) {
            this.countriesList = countriesList;
            return this;
        }

        public Builder setLoading(boolean loading) {
            this.loading = loading;
            return this;
        }

        public BoardingCountriesState build() {
            return new BoardingCountriesState(countryName,countriesList,loading);
        }
    }
}

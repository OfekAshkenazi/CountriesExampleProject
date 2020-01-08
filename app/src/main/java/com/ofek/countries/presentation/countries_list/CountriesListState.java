package com.ofek.countries.presentation.countries_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ofek.countries.domain.objects.DomainCountryObj;

import java.util.ArrayList;
import java.util.List;

/**
 * state instances should be immutable, thus all it's members are final.
 * in order to change values in the state a new instance should be created by the builder class
 */
public class CountriesListState {
    @Nullable
    private final List<DomainCountryObj> countriesList;
    private final boolean loading;
    @NonNull
    private final Order sortOrder;
    @NonNull
    private final SortBy sortingType;
    public enum SortBy {
        NAME,
        AREA_SIZE
    }

    public enum Order {
        ASCENDING,
        DESCENDING
    }

    private CountriesListState(List<DomainCountryObj> countriesList, boolean loading, Order sortOrder, SortBy sortingType) {
        this.countriesList = countriesList;
        this.loading = loading;
        this.sortOrder = sortOrder;
        this.sortingType = sortingType;
    }

    /**
     * initializes the first state with default values
     * @return
     */
    public static CountriesListState createDefaultState() {
        return new CountriesListState(null,false, Order.ASCENDING, SortBy.NAME);
    }


    /**
     * creates a new builder instance from the current instance 
     * @return
     */
    public Builder newBuilder(){
        return new Builder(this);
    }

    @Nullable
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

    @NonNull
    public Order getSortOrder() {
        return sortOrder;
    }

    @NonNull
    public SortBy getSortingType() {
        return sortingType;
    }

    /**
     * the builder is the only way to create a new state as new state should always copy the values from the previous state and change only whats necessary
     */
    public static class Builder {
        @Nullable
        private List<DomainCountryObj> countriesList;
        private boolean loading;
        @NonNull
        private Order sortOrder;
        @NonNull
        private SortBy sortingType;

        private Builder(CountriesListState toCopy) {
            this.countriesList = toCopy.countriesList;
            this.loading = toCopy.loading;
            this.sortingType = toCopy.sortingType;
            this.sortOrder = toCopy.sortOrder;
        }

        public Builder setCountriesList(@Nullable List<DomainCountryObj> countriesList) {
            this.countriesList = countriesList;
            return this;
        }

        public Builder setLoading(boolean loading) {
            this.loading = loading;
            return this;
        }

        public Builder setSortOrder(@NonNull Order sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public Builder setSortingType(@NonNull SortBy sortingType) {
            this.sortingType = sortingType;
            return this;
        }

        public CountriesListState build() {
            return new CountriesListState(countriesList,loading, sortOrder,sortingType);
        }
    }
}

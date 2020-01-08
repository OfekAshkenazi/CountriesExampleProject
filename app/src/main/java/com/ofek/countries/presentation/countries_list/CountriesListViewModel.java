package com.ofek.countries.presentation.countries_list;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.common.collect.Lists;
import com.ofek.countries.domain.objects.DomainCountryObj;
import com.ofek.countries.domain.usecases.GetCountriesList;
import com.ofek.countries.presentation.common.errors.CountryListError;
import com.ofek.countries.presentation.common.errors.PresentationError;
import com.ofek.countries.presentation.mappers.UiCountryMappers;
import com.ofek.countries.presentation.objects.UiCountry;
import com.ofek.countries.ui.countries_list.CountriesListFragment;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CountriesListViewModel extends ViewModel {

    private static final String TAG = "CountriesListViewModel";
    private final GetCountriesList  getCountriesList;
    final MutableLiveData<CountriesListState> stateLiveData = new MutableLiveData<>();
    final MutableLiveData<PresentationError> errorLiveData = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public CountriesListViewModel(GetCountriesList getCountriesList) {
        this.getCountriesList = getCountriesList;
        initializeState();
    }




    public static class Factrory implements ViewModelProvider.Factory {
        private final GetCountriesList getCountriesList;

        public Factrory(GetCountriesList getCountriesList) {
            this.getCountriesList = getCountriesList;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (CountriesListViewModel.class.isAssignableFrom(modelClass)) {
                try {
                    return (T) CountriesListViewModel.class.getConstructor(GetCountriesList.class).newInstance(getCountriesList);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                    throw new IllegalStateException(e);
                }
            } else {
                throw new IllegalStateException("the requested view model is not a class or sub class of BoardingCountriesScreenViewModel");
            }

        }
    }


    private void initializeState() {
        stateLiveData.setValue(CountriesListState.createDefaultState());
    }


    public void loadCountries(){
        getCountriesList.getCountries()
                .flatMapObservable(Observable::fromIterable)
                .sorted((country1,country2)->{
                    // add the sorting to the new data
                    if (stateLiveData.getValue().getSortingType().equals(CountriesListState.SortBy.NAME)) {
                        return country1.getEnglishName().compareTo(country2.getEnglishName());
                    } else {
                        return country1.getArea().compareTo(country2.getArea());
                    }

                })
                .toList()
                .flatMap(countriesList->{
                    // also if the order was descending the list should be reversed
                    if (stateLiveData.getValue().getSortOrder().equals(CountriesListState.Order.DESCENDING)) {
                        return Single.just(countriesList).map(Lists::reverse);
                    } else {
                        return Single.just(countriesList);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<DomainCountryObj>>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                compositeDisposable.add(d);
                CountriesListState newState = stateLiveData.getValue().newBuilder().setLoading(true).build();
                stateLiveData.setValue(newState);
            }

            @Override
            public void onSuccess(List<DomainCountryObj> countriesList) {
                CountriesListState newState = stateLiveData.getValue().newBuilder().setLoading(false).setCountriesList(countriesList).build();
                stateLiveData.setValue(newState);
                if (disposable != null && disposable.isDisposed()) {
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ",e );
                CountriesListState newState = stateLiveData.getValue().newBuilder().setCountriesList(null).setLoading(false).build();
                stateLiveData.setValue(newState);
                errorLiveData.setValue(new CountryListError());
                if (disposable != null && disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        });
    }

    /**
     * changes the sorting of the countries list.
     * @param sortBy
     * @param sortOrder
     */
    public void changeSorting(CountriesListState.SortBy sortBy, CountriesListState.Order sortOrder) {
        CountriesListState currentState = stateLiveData.getValue();
        // if countries list isn't available then the order cannot change
        if (currentState.getCountriesList() == null) {
            return;
        }
        Single<List<DomainCountryObj>> countries = Single.just(currentState.getCountriesList());
        // if only the sort order changes the list isn't re-sorted, it's just get reversed.
        if (currentState.getSortingType().equals(sortBy) && !currentState.getSortOrder().equals(sortOrder)) {
            countries = countries.map(Lists::reverse);
        }
        else {
            if (CountriesListState.SortBy.NAME.equals(sortBy)) {
                    countries = countries.flatMapObservable(Observable::fromIterable)
                        .sorted((country1,country2)-> country1.getEnglishName().compareTo(country2.getEnglishName()) ).toList();
            }
            if (CountriesListState.SortBy.AREA_SIZE.equals(sortBy)) {
                countries = countries.flatMapObservable(Observable::fromIterable)
                        .sorted((country1,country2)-> Double.compare(country1.getArea(),country2.getArea()) ).toList();
            }
            if (CountriesListState.Order.DESCENDING.equals(sortOrder)) {
                countries = countries.map(Lists::reverse);
            }
        }
        countries
                .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<DomainCountryObj>>() {

                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<DomainCountryObj> domainCountryObjs) {
                        CountriesListState newState = stateLiveData.getValue().newBuilder().setSortOrder(sortOrder).setSortingType(sortBy).setCountriesList(domainCountryObjs).build();
                        stateLiveData.setValue(newState);
                        if (disposable != null && disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ",e );

                        if (disposable != null && disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                });
    }

    public void observeLoadingChanges(LifecycleOwner owner, Observer<Boolean> observer) {
        LiveData<Boolean> mediatorLiveData = Transformations.switchMap(stateLiveData, state -> new MutableLiveData<>(state.isLoading()));
        mediatorLiveData.observe(owner,observer);
    }

    public void observeCountriesList(LifecycleOwner owner, Observer<List<UiCountry>> observer) {
        LiveData<List<UiCountry>> mediatorLiveData = Transformations.map(stateLiveData, state -> {
            if (state.getCountriesList() != null) {
                // maps the countries to ui countries
                return Observable.fromIterable(state.getCountriesList())
                        .map(UiCountryMappers::mapDomainCountryToUiCountry).toList().blockingGet();
            } else {
                return null;
            }
        });
        mediatorLiveData.observe(owner,observer);
    }


    public void observeSortTypeChanges(LifecycleOwner owner, Observer<CountriesListState.SortBy> observer) {
        LiveData<CountriesListState.SortBy> mediatorLiveData = Transformations.switchMap(stateLiveData, state -> new MutableLiveData<>(state.getSortingType()));
        mediatorLiveData.observe(owner,observer);
    }
    public void observeSortOrderChanges(LifecycleOwner owner, Observer<CountriesListState.Order> observer) {
        LiveData<CountriesListState.Order> mediatorLiveData = Transformations.switchMap(stateLiveData, state -> new MutableLiveData<>(state.getSortOrder()));
        mediatorLiveData.observe(owner,observer);
    }

    public void observerErrors(LifecycleOwner lifecycleOwner, Observer<PresentationError> observer) {
        errorLiveData.observe(lifecycleOwner,observer);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}

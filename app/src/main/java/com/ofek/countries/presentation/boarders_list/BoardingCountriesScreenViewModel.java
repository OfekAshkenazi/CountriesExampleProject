package com.ofek.countries.presentation.boarders_list;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ofek.countries.domain.objects.DomainCountryObj;
import com.ofek.countries.domain.usecases.GetCountryByCode;
import com.ofek.countries.presentation.common.errors.BoardingListLoadingError;
import com.ofek.countries.presentation.common.errors.PresentationError;
import com.ofek.countries.presentation.mappers.UiCountryMappers;
import com.ofek.countries.presentation.objects.UiCountry;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * a view model for the boarding countries screen
 */
public class BoardingCountriesScreenViewModel extends ViewModel {

    private static final String TAG = "";
    private final GetCountryByCode getCountryByCode;
    final MutableLiveData<BoardingCountriesState> stateLiveData = new MutableLiveData<>();
    final MutableLiveData<PresentationError> errorLiveData = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BoardingCountriesScreenViewModel(GetCountryByCode getCountryByCode) {
        this.getCountryByCode = getCountryByCode;
        initializeLiveData();
    }


    /**
     * initialize the state live data with it's the default value
     */
    private void initializeLiveData() {
        stateLiveData.setValue(BoardingCountriesState.createDefualtState());
    }

    public static class Factrory implements ViewModelProvider.Factory {
        private final GetCountryByCode getCountryByCode;

        public Factrory(GetCountryByCode getCountryByCode) {
            this.getCountryByCode = getCountryByCode;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (BoardingCountriesScreenViewModel.class.isAssignableFrom(modelClass)) {
                try {
                    return (T) BoardingCountriesScreenViewModel.class.getConstructor(GetCountryByCode.class).newInstance(getCountryByCode);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                    throw new IllegalStateException(e);
                }
            } else {
                throw new IllegalStateException("the requested view model is not a class or sub class of BoardingCountriesScreenViewModel");
            }

        }
    }

    /**
     * upddates the state with the country name should be presented
     *
     * @param countryName the selected country name
     */
    public void updateCountryName(String countryName) {
        BoardingCountriesState newState = stateLiveData.getValue().newBuilder().setCountryName(countryName).build();
        stateLiveData.setValue(newState);
    }

    /**
     * the function loads the information of the countries in the list and updates the state
     * <p>
     * note:
     * when I've implemented this I haven't noticed that countries has an api call which accepts a list of country codes so I've implemented it one by one
     * when I noticed it was after a lot of work so I just left it as it is because it's an example app
     *
     * @param boardingsCountriesList
     */
    public void loadBoardingCountries(List<String> boardingsCountriesList) {
        Observable.fromIterable(boardingsCountriesList)
                // loads each country by its country code
                .flatMap(countryCode -> getCountryByCode.getCountryByCode(countryCode).toObservable())
                // then grouping the result of all countries to a single list
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<DomainCountryObj>>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        disposable = d;
                        // updates the state to loading state
                        BoardingCountriesState newState = stateLiveData.getValue().newBuilder().setLoading(true).build();
                        stateLiveData.setValue(newState);
                    }

                    @Override
                    public void onSuccess(List<DomainCountryObj> countriesList) {
                        // updating the state with the loaded countries
                        BoardingCountriesState newState = stateLiveData.getValue().newBuilder().setLoading(false).setCountriesList(countriesList).build();
                        stateLiveData.setValue(newState);
                        if (disposable != null && disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        // removes the loading state
                        BoardingCountriesState newState = stateLiveData.getValue().newBuilder().setLoading(false).build();
                        stateLiveData.setValue(newState);
                        errorLiveData.setValue(new BoardingListLoadingError());
                        if (disposable != null && disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                });
    }

    public void observeErrorLiveData(LifecycleOwner owner, Observer<PresentationError> observer) {
        errorLiveData.observe(owner,observer);
    }

    public void observeCountryNameChanges(LifecycleOwner lifecycleOwner, Observer<String> observer) {
        Transformations.map(stateLiveData, BoardingCountriesState::getCountryName)
                .observe(lifecycleOwner,observer);
    }

    public void observeLoadingChanges(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        Transformations.map(stateLiveData, BoardingCountriesState::isLoading)
                .observe(lifecycleOwner,observer);
    }

    public void observeBoardingList(LifecycleOwner lifecycleOwner, Observer<List<UiCountry>> observer) {
        Transformations.map(stateLiveData,state-> {
            if (state.getCountriesList() != null) {
                // maps the countries to ui countries
                List<UiCountry> countries = Observable.fromIterable(state.getCountriesList())
                        .map(UiCountryMappers::mapDomainCountryToUiCountry).toList().blockingGet();
                return countries;
            } else {
                return null;
            }
        }).observe(lifecycleOwner,observer);
    }
    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}

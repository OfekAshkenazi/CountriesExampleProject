package com.ofek.countries.ui.countries_list;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofek.countries.R;
import com.ofek.countries.presentation.countries_list.CountriesListState;
import com.ofek.countries.presentation.countries_list.CountriesListViewModel;
import com.ofek.countries.presentation.objects.UiCountry;
import com.ofek.countries.ui.CountriesListAdapter;
import com.ofek.countries.ui.di.GlobalDependencyProvider;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountriesListFragment extends Fragment {


    private ViewModelProvider.Factory factory = GlobalDependencyProvider.getCountriesListViewModelFactory();

    public static CountriesListFragment newInstance() {

        Bundle args = new Bundle();

        CountriesListFragment fragment = new CountriesListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CountriesListFragment() {
        // Required empty public constructor
    }


    private CountriesListViewModel countriesListViewModel;
    private RecyclerView recyclerView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // using activity to maintain view model instance on rotation change
        countriesListViewModel = ViewModelProviders.of(requireActivity(),factory).get(CountriesListViewModel.class);
        countriesListViewModel.loadCountries();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.countries_rv);
        countriesListViewModel.observeCountriesList(this,this::onCountryListChanged);
        countriesListViewModel.observeLoadingChanges(this,this::onLoadingChanged);
        countriesListViewModel.observeSortOrderChanges(this,this::onSortOrderChanged);
        countriesListViewModel.observeSortTypeChanges(this,this::onSortTypeChanged);
    }

    private void onSortTypeChanged(CountriesListState.SortBy sortBy) {

    }

    private void onSortOrderChanged(CountriesListState.Order order) {

    }

    private void onLoadingChanged(Boolean aBoolean) {

    }

    private void onCountryListChanged(List<UiCountry> uiCountries) {
        if (uiCountries != null) {
            recyclerView.setAdapter(new CountriesListAdapter(uiCountries));
        }
    }


}

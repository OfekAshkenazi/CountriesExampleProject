package com.ofek.countries.ui.countries_list;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ofek.countries.R;
import com.ofek.countries.presentation.common.errors.CountryListError;
import com.ofek.countries.presentation.countries_list.CountriesListState;
import com.ofek.countries.presentation.countries_list.CountriesListViewModel;
import com.ofek.countries.presentation.objects.UiCountry;
import com.ofek.countries.ui.CountriesListAdapter;
import com.ofek.countries.ui.di.GlobalDependencyProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountriesListFragment extends Fragment implements CountryListError.CountriesListErrorHandler {


    private ViewModelProvider.Factory factory = GlobalDependencyProvider.getCountriesListViewModelFactory();
    private TextView nameTv;
    private TextView areaTv;
    private SwipeRefreshLayout swipeToRefreshLay;
    private View loadingFailedTv;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        loadingFailedTv = view.findViewById(R.id.loading_failed_message_tv);
        nameTv = view.findViewById(R.id.name_tv);
        nameTv.setOnClickListener(v -> {
            // if the name is already selected the order should change
            if (CountriesListState.SortBy.NAME.equals(v.getTag())) {
                // sets the order to the opposite of the current selected state.
                CountriesListState.Order order = v.isSelected() ? CountriesListState.Order.DESCENDING : CountriesListState.Order.ASCENDING;
                countriesListViewModel.changeSorting(CountriesListState.SortBy.NAME,order);
            } else {
                // otherwise, change the sorting to name and maintain the current order
                CountriesListState.Order order = v.isSelected() ? CountriesListState.Order.ASCENDING : CountriesListState.Order.DESCENDING;
                countriesListViewModel.changeSorting(CountriesListState.SortBy.NAME,order);
            }
        });
        areaTv = view.findViewById(R.id.arear_tv);
        areaTv.setOnClickListener(v -> {
            // if the area is already selected, the order should change
            if (CountriesListState.SortBy.AREA_SIZE.equals(v.getTag())) {
                // sets the order to the opposite of the current selected state.
                CountriesListState.Order order = v.isSelected() ? CountriesListState.Order.DESCENDING : CountriesListState.Order.ASCENDING;
                countriesListViewModel.changeSorting(CountriesListState.SortBy.AREA_SIZE,order);
            } else {
                // otherwise, change the sorting to area and maintain the current order
                CountriesListState.Order order = v.isSelected() ? CountriesListState.Order.ASCENDING : CountriesListState.Order.DESCENDING;
                countriesListViewModel.changeSorting(CountriesListState.SortBy.AREA_SIZE,order);
            }
        });
        swipeToRefreshLay = view.findViewById(R.id.swipe_to_refresh);
        swipeToRefreshLay.setOnRefreshListener(() -> countriesListViewModel.loadCountries());
        countriesListViewModel.observeCountriesList(this,this::onCountryListChanged);
        countriesListViewModel.observeLoadingChanges(this,this::onLoadingChanged);
        countriesListViewModel.observeSortOrderChanges(this,this::onSortOrderChanged);
        countriesListViewModel.observeSortTypeChanges(this,this::onSortTypeChanged);
        // passing this fragment as error handler. if the
        countriesListViewModel.observerErrors(this,presentationError -> presentationError.handle(this));
    }

    private void onSortTypeChanged(CountriesListState.SortBy sortBy) {
        nameTv.setTag(sortBy);
        areaTv.setTag(sortBy);
        if (CountriesListState.SortBy.NAME.equals(sortBy)) {
            // name filtering is selected, changes the color to selected color
            nameTv.setTextColor(Color.GREEN);
            nameTv.setTag(sortBy);
        } else {
            // name filtering is not selected, changes the color to unselected color
            nameTv.setTextColor(Color.BLACK);
        }
        if (CountriesListState.SortBy.AREA_SIZE.equals(sortBy)) {
            // area filtering is selected, changes the color to selected color
            areaTv.setTextColor(Color.GREEN);
        } else {
            // area filtering is not selected, changes the color to unselected color
            areaTv.setTextColor(Color.BLACK);
        }
    }

    private void onSortOrderChanged(CountriesListState.Order order) {
        // changing the selection according to the order
        if (CountriesListState.Order.ASCENDING.equals(order)) {
            nameTv.setSelected(true);
            areaTv.setSelected(true);
        } else {
            nameTv.setSelected(false);
            areaTv.setSelected(false);
        }
    }

    private void onLoadingChanged(Boolean aBoolean) {
        if (aBoolean) {
            loadingFailedTv.setVisibility(View.GONE);
        }
        swipeToRefreshLay.setRefreshing(aBoolean);
    }

    private void onCountryListChanged(List<UiCountry> uiCountries) {
        // if the countries list is null an empty array list will clean the previous shown list
        if (uiCountries != null) {
            recyclerView.setAdapter(new CountriesListAdapter(uiCountries, (CountriesListAdapter.InteractionListener) requireActivity()));
        } else {
            recyclerView.setAdapter(new CountriesListAdapter(new ArrayList<>(), null));
        }
    }


    @Override
    public void onCountriesListLoadingError() {
        loadingFailedTv.setVisibility(View.VISIBLE);
    }
}

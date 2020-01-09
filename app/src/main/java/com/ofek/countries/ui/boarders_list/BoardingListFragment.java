package com.ofek.countries.ui.boarders_list;


import android.content.Context;
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
import com.ofek.countries.presentation.boarders_list.BoardingCountriesScreenViewModel;
import com.ofek.countries.presentation.common.errors.BoardingListLoadingError;
import com.ofek.countries.presentation.objects.UiCountry;
import com.ofek.countries.ui.CountriesListAdapter;
import com.ofek.countries.ui.di.GlobalDependencyProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardingListFragment extends Fragment implements BoardingListLoadingError.BoardingListLoadingErrorHandler {


    private static final String COUNTRY_KEY = "country_name";
    private ViewModelProvider.Factory factory = GlobalDependencyProvider.getBoardingListViewModelFactory();
    private BoardingCountriesScreenViewModel boardingListVM;
    private SwipeRefreshLayout swipeToRefreshLay;
    private View loadingFailedTv;
    private TextView countryNameTv;
    private RecyclerView countriesRv;
    private View noBoardersTv;

    public static BoardingListFragment newInstance(UiCountry country) {

        Bundle args = new Bundle();
        BoardingListFragment fragment = new BoardingListFragment();
        args.putParcelable(COUNTRY_KEY,country);
        fragment.setArguments(args);
        return fragment;
    }
    public BoardingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        boardingListVM  = ViewModelProviders.of(requireActivity(),factory).get(BoardingCountriesScreenViewModel.class);
        if (getArguments() == null || getArguments().getParcelable(COUNTRY_KEY) == null) {
            throw new IllegalStateException("Please provide country as extra parcelable to this fragment");
        }
        UiCountry country = getArguments().getParcelable(COUNTRY_KEY);
        boardingListVM.updateCountryName(country.getEnglishName());
        boardingListVM.loadBoardingCountries(country.getBoarders());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_boarding_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countryNameTv = view.findViewById(R.id.country_name_tv);
        noBoardersTv = view.findViewById(R.id.no_boarders_tv);
        countriesRv = view.findViewById(R.id.countries_rv);
        countriesRv.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        swipeToRefreshLay = view.findViewById(R.id.swipe_to_refresh);
        swipeToRefreshLay.setOnRefreshListener(() -> {
            UiCountry country = getArguments().getParcelable(COUNTRY_KEY);
            if (country != null) {
                boardingListVM.loadBoardingCountries(country.getBoarders());
            }
        });
        loadingFailedTv = view.findViewById(R.id.loading_failed_message_tv);

        // starting to observe only after the view has created to avoid fragment's lifecycle issues.
        boardingListVM.observeBoardingList(this,this::onBoardingListChanged);
        boardingListVM.observeLoadingChanges(this,this::onLoadingChanged);
        boardingListVM.observeCountryNameChanges(this,this::onCountryNameChanged);
        boardingListVM.observeErrorLiveData(this,presentationError -> presentationError.handle(this));
    }

    private void onCountryNameChanged(String s) {
        countryNameTv.setText(s);
    }

    private void onLoadingChanged(Boolean aBoolean) {
        if (aBoolean) {
            loadingFailedTv.setVisibility(View.GONE);
            noBoardersTv.setVisibility(View.GONE);
        }
        swipeToRefreshLay.setRefreshing(aBoolean);
    }

    private void onBoardingListChanged(List<UiCountry> uiCountries) {
        // if the countries list is null an empty array list will clean the previous shown list
        if (uiCountries != null) {
            // if countries list returned empty it means the country doesn't have boarders
            countriesRv.setAdapter(new CountriesListAdapter(uiCountries,null));
            if (uiCountries.isEmpty()) {
                noBoardersTv.setVisibility(View.VISIBLE);
            }
        } else {
            countriesRv.setAdapter(new CountriesListAdapter(new ArrayList<>(),null));
        }
    }

    @Override
    public void onBoardingCountriesLoadingFailed() {
        loadingFailedTv.setVisibility(View.VISIBLE);
    }
}

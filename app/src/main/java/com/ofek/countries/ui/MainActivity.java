package com.ofek.countries.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ofek.countries.R;
import com.ofek.countries.presentation.objects.UiCountry;
import com.ofek.countries.ui.boarders_list.BoardingListFragment;
import com.ofek.countries.ui.countries_list.CountriesListFragment;

public class MainActivity extends AppCompatActivity implements CountriesListAdapter.InteractionListener {


    private static final String BOARDING_LIST_FRAG_BACKSTACK_TAG = "boarding_list_frag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,CountriesListFragment.newInstance()).commit();
    }

    /**
     * callback to the country selection from the list
     * @param uiCountry
     */
    @Override
    public void onCountrySelected(UiCountry uiCountry) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, BoardingListFragment.newInstance(uiCountry)).addToBackStack(BOARDING_LIST_FRAG_BACKSTACK_TAG).commit();
    }
}

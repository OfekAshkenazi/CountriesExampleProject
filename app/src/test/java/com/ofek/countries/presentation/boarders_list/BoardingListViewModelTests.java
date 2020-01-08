package com.ofek.countries.presentation.boarders_list;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.ofek.countries.domain.usecases.GetCountryByCode;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * an example for view model testing
 */
public class BoardingListViewModelTests {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Test
    public void verify_name_changed(){
        BoardingCountriesScreenViewModel viewModel = new BoardingCountriesScreenViewModel(Mockito.mock(GetCountryByCode.class));
        BoardingCountriesState expectedState = BoardingCountriesState.createDefualtState().newBuilder().setCountryName("israel").build();
        ArgumentCaptor<BoardingCountriesState> captur = ArgumentCaptor.forClass(BoardingCountriesState.class);

        Observer observer = Mockito.mock(Observer.class);
        viewModel.stateLiveData.observeForever(observer);
        viewModel.updateCountryName("israel");
        Mockito.verify(observer,Mockito.atLeast(1)).onChanged(captur.capture());
        // first state is the default state, second state is the actual state which needs testing
        Assert.assertEquals(captur.getAllValues().get(1).getCountryName(),expectedState.getCountryName());
    }
}

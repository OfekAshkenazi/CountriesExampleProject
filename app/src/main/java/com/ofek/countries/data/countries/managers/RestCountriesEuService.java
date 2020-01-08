package com.ofek.countries.data.countries.managers;


import com.ofek.countries.data.countries.dto.CountryDTO;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * a retrofit service for rescountries.eu REST api
 */
public interface RestCountriesEuService {

    @GET("all")
    Single<List<CountryDTO>> getCountriesList();

    @GET("alpha/{code}")
    Single<CountryDTO> getCountryByCountryCode(@Path("code") String code);
}

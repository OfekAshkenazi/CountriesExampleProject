package com.ofek.countries.data.di.managers;

import com.ofek.countries.data.common.Constants;
import com.ofek.countries.data.countries.managers.CountriesApiManager;
import com.ofek.countries.data.countries.managers.CountriesApiManagerImp;
import com.ofek.countries.data.countries.managers.RestCountriesEuService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * see {@link com.ofek.countries.domain.di.usecases.UseCasesProvider} for explanation about the provider classes
 */
class ManagersProviderImp implements ManagersProvider {
    @Override
    public CountriesApiManager provideApiManager() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor()).build();
        Retrofit.Builder retrofit= new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.RESTCOUNTRIES_EU_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        return new CountriesApiManagerImp(retrofit.build().create(RestCountriesEuService.class));
    }
}

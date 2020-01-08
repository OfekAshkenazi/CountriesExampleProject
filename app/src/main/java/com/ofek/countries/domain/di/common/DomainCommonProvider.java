package com.ofek.countries.domain.di.common;

import com.ofek.countries.domain.objects.DomainCountryObj;

import java.util.List;

import io.reactivex.SingleTransformer;

public interface DomainCommonProvider {


    <T> SingleTransformer<T,T> getSingleTransformer();
}

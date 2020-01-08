package com.ofek.countries.domain.di.common;

import com.ofek.countries.domain.common.AsyncTransformers;

import io.reactivex.SingleTransformer;

class DomainCommonProviderImp implements DomainCommonProvider {
    @Override
    public <T> SingleTransformer<T, T> getSingleTransformer() {
        return AsyncTransformers.getSingleTransformer();
    }
}

package com.ofek.countries.domain.di;


import com.ofek.countries.domain.common.SynchronoiusTransformers;
import com.ofek.countries.domain.di.common.DomainCommonProvider;

import io.reactivex.SingleTransformer;

public class DomainCommonProviderTestImp implements DomainCommonProvider {
    @Override
    public <T> SingleTransformer<T, T> getSingleTransformer() {
        return SynchronoiusTransformers.getSingleTransformer();
    }
}

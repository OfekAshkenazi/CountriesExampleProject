package com.ofek.countries.domain.common;

import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;

public abstract class BseSingleUseCase<T> {

    private final SingleTransformer<T,T> transformer;

    protected BseSingleUseCase(SingleTransformer<T, T> transformer) {
        this.transformer = transformer;
    }

    protected Single<T> createSingle(Map<String,Object> params) {
        return createSourceSingle(params).compose(transformer);
    }

    protected abstract Single<T> createSourceSingle(Map<String, Object> params);
}

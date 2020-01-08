package com.ofek.countries.domain.common;

import io.reactivex.SingleTransformer;

/**
 * provides transformers which performs operation on the upstream thread
 * usually used for unit tests because unit tests most of the time should avoid running on background threads
 */
public class SynchronoiusTransformers {

    public static  <T> SingleTransformer<T,T> getSingleTransformer(){
        return upstream -> upstream;
    }
}

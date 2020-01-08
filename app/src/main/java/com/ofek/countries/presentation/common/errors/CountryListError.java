package com.ofek.countries.presentation.common.errors;

import java.util.concurrent.atomic.AtomicBoolean;

public class CountryListError implements PresentationError {
    private final AtomicBoolean handled = new AtomicBoolean(false);
    @Override
    public void handle(BasePresentationErrorHandler handlingProtocol) {
        if (!handled.get() && handlingProtocol instanceof CountriesListErrorHandler) {
            ((CountriesListErrorHandler) handlingProtocol).onCountriesListLoadingError();
        }
    }

    public interface CountriesListErrorHandler extends BasePresentationErrorHandler {
        void onCountriesListLoadingError();
    }
}

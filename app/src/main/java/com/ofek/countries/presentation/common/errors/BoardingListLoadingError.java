package com.ofek.countries.presentation.common.errors;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * in order to handle this error the handler must implement {@link BoardingListLoadingErrorHandler} interface
 * then it will receive a callback when this error accrues
 */
public class BoardingListLoadingError implements PresentationError {
    /**
     * this boolean determines if this error already handled
     */
    private final AtomicBoolean handled = new AtomicBoolean(false);

    @Override
    public void handle(BasePresentationErrorHandler handlingProtocol) {
        // in order to prevent multiple handlers to handle the same error before calling the method on the handler
        // we first checking the error didn't handled yet
        if (!handled.get() && handlingProtocol instanceof BoardingListLoadingErrorHandler) {
            ((BoardingListLoadingErrorHandler) handlingProtocol).onBoardingCountriesLoadingFailed();
            handled.set(true);
        }
    }

    public interface BoardingListLoadingErrorHandler extends BasePresentationErrorHandler{
        void onBoardingCountriesLoadingFailed();
    }
}

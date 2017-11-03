package com.droidodds.web.controller;

import com.droidodds.exception.InvalidCardsInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Laszlo_Sisa
 */
@Component
class OddsExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OddsExceptionHandler.class);

    ResponseEntity<?> handleException(final Exception exception) {
        if (exception instanceof InvalidCardsInputException) {
            LOGGER.error("Rejecting request with reason: {}", exception.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
           LOGGER.error("Unknown error occurred: {}", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

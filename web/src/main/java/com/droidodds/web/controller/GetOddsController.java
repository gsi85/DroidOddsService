package com.droidodds.web.controller;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import com.droidodds.service.OddsCalculatorService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Laszlo_Sisa
 */
@RestController
public class GetOddsController {

    public static final String REQUEST_MAPPING = "/getOdds";

    @Autowired
    private OddsCalculatorService oddsCalculatorService;
    @Autowired
    private OddsExceptionHandler oddsExceptionHandler;

    @GetMapping(value = REQUEST_MAPPING, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Odds handleOddsRequest(@RequestParam(value = "cardsInHand") Set<Card> cardsInHand, @RequestParam(value = "cardsOnDeck", required = false) Set<Card> cardsOnDeck) {
        return oddsCalculatorService.calculateOdds(cardsInHand, cardsOnDeck);
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Card.class, new CardsEditorSupport());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(final Exception exception) {
        return oddsExceptionHandler.handleException(exception);
    }

}

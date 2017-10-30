package com.droidodds.web.controller;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import com.droidodds.service.OddsCalculatorService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Laszlo_Sisa
 */
@RestController
public class GetOddsController {

    @Autowired
    private OddsCalculatorService oddsCalculatorService;

    @GetMapping(value = "/getOdds", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Odds handleOddsRequest(@RequestParam(value = "cardsInHand") Set<Card> cardsInHand, @RequestParam(value = "cardsOnDeck") Set<Card> cardsOnDeck) {
        return oddsCalculatorService.calculateOdds(cardsInHand, cardsOnDeck);
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Card.class, new CardsEditorSupport());
    }

}

package com.droidodds.web.controller;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.card.Rank;
import com.droidodds.domain.card.Suit;
import java.beans.PropertyEditorSupport;

/**
 * @author Laszlo_Sisa
 */
public class CardsEditorSupport extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String suitShortName = text.substring(0, 1).toUpperCase();
        String rankShortName = text.substring(1).toUpperCase();
        setValue(new Card(Rank.getByShortName(rankShortName), Suit.getByShortName(suitShortName)));
    }


    @Override
    public String getAsText() {
        Card card = (Card) getValue();
        return card != null ? card.toString() : null;
    }
}

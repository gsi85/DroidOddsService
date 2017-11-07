package com.droidodds;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.droidodds.application.OddsServiceApplication;
import com.droidodds.web.controller.GetOddsController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Laszlo_Sisa
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {OddsServiceApplication.class})
@AutoConfigureMockMvc
public class OddsServiceAcceptanceTest {

    private static final String THREE_CARDS_ON_DECK = "?cardsInHand=S7&cardsInHand=CK&cardsOnDeck=D7&cardsOnDeck=SJ&cardsOnDeck=HA";
    private static final String EXPECTED_THREE_CARDS_ON_DECK_RESULT = "{\"winCount\":708218,\"splitCount\":21623,\"totalDealCount\":1070190}";

    private static final String FOUR_CARDS_ON_DECK = "?cardsInHand=C5&cardsInHand=D10&cardsOnDeck=H10&cardsOnDeck=HJ&cardsOnDeck=SJ&cardsOnDeck=S5";
    private static final String EXPECTED_FOUR_CARDS_ON_DECK_RESULT = "{\"winCount\":34198,\"splitCount\":2154,\"totalDealCount\":45540}";

    private static final String FIVE_CARDS_ON_DECK = "?cardsInHand=DQ&cardsInHand=C5&cardsOnDeck=s5&cardsOnDeck=sJ&cardsOnDeck=h9&cardsOnDeck=h5&cardsOnDeck=s3";
    private static final String EXPECTED_FIVE_CARDS_ON_DECK_RESULT = "{\"winCount\":916,\"splitCount\":3,\"totalDealCount\":990}";

    @Value("classpath:expected_for_three_on_deck.json")
    private Resource expectThreeOnDeckResult;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testOddsServiceWithThreeCardsOnDeck() throws Exception {
        mockMvc.perform(get(GetOddsController.REQUEST_MAPPING + THREE_CARDS_ON_DECK))
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED_THREE_CARDS_ON_DECK_RESULT));
    }

    @Test
    public void testOddsServiceWithFourCardsOnDeck() throws Exception {
        mockMvc.perform(get(GetOddsController.REQUEST_MAPPING + FOUR_CARDS_ON_DECK))
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED_FOUR_CARDS_ON_DECK_RESULT));
    }

    @Test
    public void testOddsServiceWithFiveCardsOnDeck() throws Exception {
        mockMvc.perform(get(GetOddsController.REQUEST_MAPPING + FIVE_CARDS_ON_DECK))
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED_FIVE_CARDS_ON_DECK_RESULT));
    }

}

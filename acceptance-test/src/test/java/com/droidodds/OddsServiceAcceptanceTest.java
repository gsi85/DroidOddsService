package com.droidodds;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author Laszlo_Sisa
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {OddsServiceApplication.class})
@AutoConfigureMockMvc
public class OddsServiceAcceptanceTest {

    private static final String THREE_CARDS_ON_DECK = "?cardsInHand=S7&cardsInHand=CK&cardsOnDeck=D7&cardsOnDeck=SJ&cardsOnDeck=HA";
    private static final String EXPECTED_THREE_CARDS_ON_DECK_RESULT = "{\"winCount\":695754,\"splitCount\":1836,\"totalDealCount\":1070190}";

    @Value("classpath:expected_for_three_on_deck.json")
    private Resource expectThreeOnDeckResult;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testOddsService() throws Exception {
        mockMvc.perform(get(GetOddsController.REQUEST_MAPPING + THREE_CARDS_ON_DECK))
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED_THREE_CARDS_ON_DECK_RESULT));
    }

}

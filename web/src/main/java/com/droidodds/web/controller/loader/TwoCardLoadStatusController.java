package com.droidodds.web.controller.loader;

import com.droidodds.loader.TwoKnownCardsDbLoaderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Laszlo_Sisa
 */
@RestController
public class TwoCardLoadStatusController {

    private static final String REQUEST_MAPPING = "/getLoadStatus";

    @Autowired
    private TwoKnownCardsDbLoaderStatus twoKnownCardsDbLoaderStatus;

    @GetMapping(value = REQUEST_MAPPING, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TwoLoadStatus handleRequest() {
        return new TwoLoadStatus(twoKnownCardsDbLoaderStatus.getCalculatedCounter(), twoKnownCardsDbLoaderStatus.getInProgressHand());
    }

}

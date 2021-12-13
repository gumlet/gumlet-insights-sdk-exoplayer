package com.gumlet.insights.calls.api;

import com.google.common.truth.Truth;
import com.gumlet.insights.ViewerSessionEvent;
import com.gumlet.insights.enums.Events;

import org.junit.Test;

public class ApiParameterBasicStabilityTest {
    @Test
    public void isValidPropertyId() {
        String propertyId = "dfjsl12D5";
        boolean result = ApiParameterBasicStability.isValidPropertyId(propertyId);
        Truth.assertThat(result).isTrue();
    }

    @Test
    public void isValidIdsProvidedInApiCall() {
        ViewerSessionEvent viewerSessionEvent = new ViewerSessionEvent();
        viewerSessionEvent.setEventId("4ce2255e-baaf-433d-a6ae-6da4796187db");
        viewerSessionEvent.setSessionId("241ae45b-8275-475a-a593-1e7bdd252977");
        viewerSessionEvent.setPlaybackId("bc6ce7fd-63f1-43ae-b7b1-c5cbd96723fe");
        viewerSessionEvent.setPlayerInstanceId("e8af4a58-e2dd-4d39-903e-6e3b22c78df");
        viewerSessionEvent.setUserId("fa4e0cf0f2190c20");

        boolean result = ApiParameterBasicStability.isValidIdsProvidedInApiCall(viewerSessionEvent);

        Truth.assertThat(result).isTrue();
    }

    @Test
    public void timingWithEventAndPreviousEventCorrect() {
        ViewerSessionEvent viewerSessionEvent = new ViewerSessionEvent();
        viewerSessionEvent.setEvent(Events.EVENT_PLAYBACK_UPDATE);
        viewerSessionEvent.setPreviousEvent(Events.EVENT_PLAYBACK_STARTED);
        viewerSessionEvent.setMillisFromPreviousEvent(1500L);
        viewerSessionEvent.setPlaybackTimeInstantMillis(3000L);
        viewerSessionEvent.setTimestamp(System.currentTimeMillis());

        boolean result = ApiParameterBasicStability.timingWithEventAndPreviousEventCorrect(viewerSessionEvent);

        Truth.assertThat(result).isTrue();
    }

    @Test
    public void scalingIsCorrect() {

        ViewerSessionEvent viewerSessionEvent = new ViewerSessionEvent();
        viewerSessionEvent.setEvent(Events.EVENT_PLAYBACK_UPDATE);
        viewerSessionEvent.setPreviousEvent(Events.EVENT_PLAYBACK_STARTED);
        viewerSessionEvent.setVideoUpscalePercentage(4.0);
        viewerSessionEvent.setVideoDownscalePercentage(0.0);

        boolean result = ApiParameterBasicStability.scalingIsCorrect(viewerSessionEvent);

        Truth.assertThat(result).isTrue();
    }

    @Test
    public void seekIsCorrect() {
        ViewerSessionEvent viewerSessionEvent = new ViewerSessionEvent();
        viewerSessionEvent.setEvent(Events.EVENT_SEEKED);
        viewerSessionEvent.setFrom(3000L);
        viewerSessionEvent.setTo(12000L);

        boolean result = ApiParameterBasicStability.seekIsCorrect(viewerSessionEvent);
        Truth.assertThat(result).isTrue();
    }

    @Test
    public void rebufferIsCorrect() {

        ViewerSessionEvent viewerSessionEvent = new ViewerSessionEvent();
        viewerSessionEvent.setEvent(Events.EVENT_REBUFFER_END);
        viewerSessionEvent.setMillisFromPreviousEvent(3009L);
        boolean result = ApiParameterBasicStability.rebufferIsCorrect(viewerSessionEvent);
        Truth.assertThat(result).isTrue();
    }
}
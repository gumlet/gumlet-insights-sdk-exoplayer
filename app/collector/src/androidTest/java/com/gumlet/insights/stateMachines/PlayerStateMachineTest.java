package com.gumlet.insights.stateMachines;


import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.common.truth.Truth;
import com.gumlet.insights.GumletInsights;
import com.gumlet.insights.GumletInsightsConfig;
import com.gumlet.insights.data.DeviceInformationProvider;

import org.junit.Test;

public class PlayerStateMachineTest {

    @Test
    public void transitionState() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        GumletInsightsConfig config = new GumletInsightsConfig("234fhSjIO9",context);
        String userAgent = "Unknown";
        DeviceInformationProvider provider = new DeviceInformationProvider(context,userAgent);
        GumletInsights insights = new GumletInsights(config,context,provider);
        PlayerStateMachine stateMachine = new PlayerStateMachine(config,insights);
    }

    @Test
    public void isTransitionAllowed() {

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        GumletInsightsConfig config = new GumletInsightsConfig("234fhSjIO9",context);
        String userAgent = "Unknown";
        DeviceInformationProvider provider = new DeviceInformationProvider(context,userAgent);
        GumletInsights insights = new GumletInsights(config,context,provider);

        PlayerStateMachine stateMachine = new PlayerStateMachine(config,insights);

        boolean isAllowed = stateMachine.isTransitionAllowed(PlayerStates.PLAYING,PlayerStates.BUFFERING);

        Truth.assertThat(isAllowed).isTrue();
    }
}
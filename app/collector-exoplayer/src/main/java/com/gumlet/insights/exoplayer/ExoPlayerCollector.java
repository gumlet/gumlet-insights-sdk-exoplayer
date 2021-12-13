package com.gumlet.insights.exoplayer;

import android.content.Context;

import com.google.android.exoplayer2.ExoPlayer;
import com.gumlet.insights.Collector;
import com.gumlet.insights.DefaultCollector;
import com.gumlet.insights.GumletInsights;
import com.gumlet.insights.GumletInsightsConfig;
import com.gumlet.insights.adapters.PlayerAdapter;
import com.gumlet.insights.exoplayer.features.ExoPlayerFeatureFactory;
import com.gumlet.insights.features.FeatureFactory;

import org.jetbrains.annotations.NotNull;

public class ExoPlayerCollector extends DefaultCollector<ExoPlayer>
        implements Collector<ExoPlayer> {

    public ExoPlayerCollector(GumletInsightsConfig gumletInsightsConfig, Context context) {
        super(
                Companion.createAnalytics(
                        gumletInsightsConfig, context, ExoUtil.getUserAgent(context)));
    }

    @Deprecated
    public ExoPlayerCollector(GumletInsightsConfig gumletInsightsConfig) {
        this(gumletInsightsConfig, gumletInsightsConfig.getContext());
    }

    @NotNull
    @Override
    protected PlayerAdapter createAdapter(
            ExoPlayer exoPlayer, @NotNull GumletInsights analytics) {
        FeatureFactory featureFactory = new ExoPlayerFeatureFactory(analytics, exoPlayer);
        return new ExoPlayerAdapter(
                exoPlayer,
                analytics.getConfig(),
                analytics.getPlayerStateMachine(),
                featureFactory);
    }
}

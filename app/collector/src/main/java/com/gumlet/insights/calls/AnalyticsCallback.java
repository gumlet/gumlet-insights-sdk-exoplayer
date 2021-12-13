package com.gumlet.insights.calls;

import com.gumlet.insights.data.EventData;

public interface AnalyticsCallback {
    void onPlayerAttached();
    void onPlayerDetached();
    void onPlayerStartup(EventData data);
    void onPlayerPauseExit(EventData data);
    void onPlayerPlayExit(EventData data);
    void onPlayerRebuffering(EventData data);
    void onPlayerError(EventData data);
    void onPlayerSeekCompleted(EventData data);
    void onPlayerHeartBeat(EventData data);
    void onBufferingStarted(EventData data);
    void onBufferingEnd(EventData data);
}

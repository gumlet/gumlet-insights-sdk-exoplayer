package com.gumlet.insights.calls.events;

public interface PlayerEvents extends BaseEvent{
    void onPlayerInitSuccess();
    void onPlayerEventSuccess(String requestId);
}

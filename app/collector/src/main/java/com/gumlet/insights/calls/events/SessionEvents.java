package com.gumlet.insights.calls.events;

public interface SessionEvents extends BaseEvent{
    void onSessionEventSuccess();
    void onSessionEventSuccess(String event);
}

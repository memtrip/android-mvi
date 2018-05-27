package com.consistence.pinyin.audio.stream;

public enum StreamingAction {

    ACTION_NOTIFICATION_MAIN("ACTION_NOTIFICATION_MAIN"),
    ACTION_NOTIFICATION_PLAY("ACTION_NOTIFICATION_PLAY"),
    ACTION_NOTIFICATION_PAUSE("ACTION_NOTIFICATION_PAUSE"),
    ACTION_NOTIFICATION_RETRY("ACTION_NOTIFICATION_RETRY"),
    ACTION_NOTIFICATION_SHOW("ACTION_NOTIFICATION_SHOW"),
    ACTION_NOTIFICATION_SEEK("ACTION_NOTIFICATION_SEEK");

    private final String value;

    StreamingAction(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

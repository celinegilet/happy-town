package com.happytown.core.use_cases;

public interface NotificationProvider {

    void notifier(String to, String subject, String body) throws NotificationException;

}

package com.happytown.domain.use_cases;

public interface NotificationPort {

    void notifier(String to, String subject, String body) throws NotificationException;

}

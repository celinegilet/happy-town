package com.happytown.domain.use_cases;

import java.util.Map;

public interface NotificationPort {

    void notifier(String to, String subject, String templatePath, Map<String, String> templateArgs) throws NotificationException;

}

package com.happytown.core.use_cases;

import java.util.Map;

public interface NotificationProvider {

    void notifier(String to, String subject, String templatePath, Map<String, String> templateArgs) throws NotificationException;

}

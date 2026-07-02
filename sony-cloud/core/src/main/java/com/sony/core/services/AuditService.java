package com.sony.core.services;

public interface AuditService {

    void logPageEvent(String eventType, String path);

}
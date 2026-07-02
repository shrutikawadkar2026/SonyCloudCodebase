package com.sony.core.services.impl;

import com.sony.core.services.AuditService;

import org.osgi.service.component.annotations.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = AuditService.class)

public class AuditServiceImpl implements AuditService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(AuditServiceImpl.class);

    @Override
    public void logPageEvent(String eventType, String path) {

        LOGGER.info(" PAGE AUDIT EVENT ");
        LOGGER.info("EVENT TYPE : {}", eventType);
        LOGGER.info("PAGE PATH  : {}", path);


    }
}
package com.sony.core.services.impl;

import com.sony.core.services.ReplicationService;

import org.osgi.service.component.annotations.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = ReplicationService.class)

public class ReplicationServiceImpl implements ReplicationService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ReplicationServiceImpl.class);

    @Override
    public void processReplication(String path) {

        LOGGER.info("PAGE PUBLISHED SUCCESSFULLY : {}", path);

    }
}
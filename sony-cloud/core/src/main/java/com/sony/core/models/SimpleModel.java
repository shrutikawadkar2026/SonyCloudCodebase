package com.sony.core.models;

import com.sony.core.services.SimpleService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import javax.annotation.PostConstruct;

@Model(
        adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class SimpleModel {

    @OSGiService
    private SimpleService simpleService;

    private String message;


    @PostConstruct
    protected void init() {
        if (simpleService != null) {
            message = simpleService.processFile();
        } else {
            message = "Service not available";
        }
    }

    public String getMessage() {
        return message;
    }
}
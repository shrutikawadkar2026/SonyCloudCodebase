package com.sony.core.services.impl;


import com.sony.core.services.GreetingServices;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = GreetingServices.class)
public class GreetingServicesImpl implements GreetingServices {


    private static final Logger log = LoggerFactory.getLogger(GreetingServicesImpl.class);

    @Override
    public String getGreetings(String name) {
        log.info("The getGreeting Called for {}",name);

        return "Hello "+name+" Welcome to AEM";
    }
}

package com.sony.core.services;

import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONObject;

public interface TVDetailsService {

    JSONObject getTVDetails(
            String tvModelNo,
            ResourceResolver resolver
    );
}
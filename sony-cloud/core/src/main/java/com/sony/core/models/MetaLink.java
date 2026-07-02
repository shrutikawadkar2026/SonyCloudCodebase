package com.sony.core.models;

import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = org.apache.sling.api.resource.Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class MetaLink {

    @ValueMapValue
    private String metalinkText;

    @ValueMapValue
    private String metalinkPath;

    public String getMetalinkText() {
        return metalinkText;
    }

    public String getMetalinkPath() {
        return metalinkPath;
    }
}
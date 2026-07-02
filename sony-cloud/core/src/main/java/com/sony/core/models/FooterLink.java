package com.sony.core.models;

import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = org.apache.sling.api.resource.Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class FooterLink {

    @ValueMapValue
    private String linkText;

    @ValueMapValue
    private String linkPath;

    public String getLinkText() {
        return linkText;
    }

    public String getLinkPath() {
        return linkPath;
    }
}
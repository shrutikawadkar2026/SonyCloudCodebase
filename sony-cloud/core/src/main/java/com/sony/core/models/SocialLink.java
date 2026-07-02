package com.sony.core.models;

import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = org.apache.sling.api.resource.Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class SocialLink {

    @ValueMapValue
    private String icon;

    @ValueMapValue
    private String iconurl;

    public String getIcon() {
        return icon;
    }

    public String getIconurl() {
        return iconurl;
    }
}
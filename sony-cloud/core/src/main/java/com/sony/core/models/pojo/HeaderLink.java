package com.sony.core.models.pojo;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderLink {

    @ValueMapValue
    private String label;

    @ValueMapValue
    private String url;

    public String getLabel() { return label; }
    public String getUrl() { return url; }
}

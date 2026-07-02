package com.sony.core.models.pojo;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class FooterInner {

    @ValueMapValue
    private String text;

    @ValueMapValue
    private String url;

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
}
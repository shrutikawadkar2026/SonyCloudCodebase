package com.sony.core.models.pojo;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class NumberedItem {

    @ValueMapValue
    private String index;

    @ValueMapValue
    private String text;


    public String getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }
}

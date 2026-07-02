package com.sony.core.models;

import com.sony.core.models.pojo.PromotionItem;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.*;

import java.util.List;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class PromotionsModel {

    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String componentType;

    @ValueMapValue
    private String componentId;

    @ValueMapValue
    private String notes;

    @ChildResource
    private List<PromotionItem> promotions;


    public List<PromotionItem> getPromotions() {
        return promotions;
    }

    public String getHeading() {
        return heading;
    }

    public String getComponentType() {
        return componentType;
    }

    public String getComponentId() {
        return componentId;
    }

    public String getNotes() {
        return notes;
    }
}


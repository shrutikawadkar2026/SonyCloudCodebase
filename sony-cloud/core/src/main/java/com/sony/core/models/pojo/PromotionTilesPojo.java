package com.sony.core.models.pojo;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PromotionTilesPojo {
    @ValueMapValue
    private String eyebrow;

    @ValueMapValue
    private String tileTitle;

    @ValueMapValue
    private String image;

    @ValueMapValue
    private String link;

    public String getEyebrow() {
        return eyebrow;
    }

    public String getTileTitle() {
        return tileTitle;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }
}

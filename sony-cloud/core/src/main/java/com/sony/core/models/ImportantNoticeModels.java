package com.sony.core.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ImportantNoticeModels {

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String icon;

    @ValueMapValue
    private String ctaText;

    @ValueMapValue
    private String ctaLink;

    @ChildResource
    private List<NoticeItem>notices;

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public String getCtaText() {
        return ctaText;
    }

    public String getCtaLink() {
        return ctaLink;
    }

    public List<NoticeItem> getNotices() {
        return notices;
    }
}

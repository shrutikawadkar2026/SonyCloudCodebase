package com.sony.core.models.pojo;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class Section {

    @ValueMapValue
    private String sectionTitle;

    @ChildResource(name = "links")
    private List<FooterInner> links;

    public String getSectionTitle() {
        return sectionTitle;
    }

    public List<FooterInner> getLinks() {
        return links;
    }
}
package com.sony.core.models;

import com.sony.core.models.pojo.Section;
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
public class FooterModel {

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String placeholder;

    @ValueMapValue
    private String buttonText;

    @ValueMapValue
    private String locationLink;

    @ChildResource(name = "sections")
    private List<Section> sections;

    public String getFindStoreTitle() {
        return title;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getLocationLink() {
        return locationLink;
    }

    public List<Section> getSections() {
        return sections;
    }
}
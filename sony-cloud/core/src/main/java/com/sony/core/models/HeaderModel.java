package com.sony.core.models;

import com.sony.core.models.pojo.HeaderLink;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderModel {

    @ValueMapValue
    private String logo;

    @ValueMapValue
    private String Logolink;

    @ValueMapValue
    private String searchPlaceholder;

    @ChildResource(name = "links")
    private List<HeaderLink> links;


    public String getLogo() {
        return logo;
    }

    public String getLogolink() {
        return Logolink;
    }

    public String getSearchPlaceholder() { return searchPlaceholder; }
    public List<HeaderLink> getLinks() { return links; }
}

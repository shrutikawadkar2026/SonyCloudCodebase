package com.sony.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PrimaryTout {

    @ValueMapValue
    private String primaryToutDesktopImage;

    @ValueMapValue
    private String primaryToutNameMobileImage;
    @ValueMapValue
    private String buttonText;

    @ValueMapValue
    private String buttonPath;

    public String getPrimaryToutDesktopImage() {
        return primaryToutDesktopImage;
    }

    public String getPrimaryToutNameMobileImage() {
        return primaryToutNameMobileImage;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getButtonPath() {

        //buttonPath += ".html";
        return buttonPath;
    }
}

package com.sony.core.models;


import java.util.List;

import javax.annotation.PostConstruct;

import com.sony.core.models.FooterLink;
import com.sony.core.models.MetaLink;
import com.sony.core.models.SocialLink;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class SonyFooter {

    // Country / Region
    @ValueMapValue
    private String countryText;

    @ValueMapValue
    private String countryLink;

    // Navigation Links (Multifield)
    @ChildResource
    private List<FooterLink> links;

    // Social Links (Multifield)
    @ChildResource
    private List<SocialLink> socialLinks;

    @ChildResource
    private List<MetaLink> metaLinks;

    // Brand Section
    @ValueMapValue
    private String brandlink;

    @ValueMapValue
    private String brandimage;

    // Copyright
    @ValueMapValue
    private String copyright;


    // ================= GETTERS =================

    public String getCountryText() {
        return countryText;
    }

    public String getCountryLink() {
        return countryLink;
    }

    public List<FooterLink> getLinks() {
        return links;
    }

    public List<SocialLink> getSocialLinks() {
        return socialLinks;
    }

    public String getBrandlink() {
        return brandlink;
    }

    public String getBrandimage() {
        return brandimage;
    }

    public String getCopyright() {
        return copyright;
    }
}
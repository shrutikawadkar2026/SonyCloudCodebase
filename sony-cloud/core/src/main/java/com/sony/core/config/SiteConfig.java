package com.sony.core.config;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(
        label = "Site Configuration"
)
public @interface SiteConfig {

    @Property(label = "Support Email")
    String supportEmail();

    @Property(label = "Country")
    String country();
}
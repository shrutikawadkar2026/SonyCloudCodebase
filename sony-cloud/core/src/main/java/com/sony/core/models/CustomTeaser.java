package com.sony.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.wcm.core.components.models.Teaser;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;

@Model(adaptables = SlingHttpServletRequest.class,
adapters = {Teaser.class, ComponentExporter.class},
        resourceType = "sony/components/content/customteaser",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CustomTeaser implements Teaser {
    @Self
    @Via(type = ResourceSuperType.class)
    private Teaser delegate;

    @Override
    public String getTitle() {
        return "custom sling model";
    }

    @Override
    public String getDescription() {
        return delegate.getDescription();
    }
}

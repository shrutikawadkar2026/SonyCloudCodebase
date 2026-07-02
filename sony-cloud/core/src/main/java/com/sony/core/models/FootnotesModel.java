package com.sony.core.models;

import com.sony.core.models.pojo.NumberedItem;
import com.sony.core.models.pojo.StarItem;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import java.util.List;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class FootnotesModel {

    @ValueMapValue
    private String label;

    @ValueMapValue
    private String targetId;

    @ValueMapValue
    private boolean expanded;

    @ValueMapValue
    private String closeLabel;

    // Multifield - Numbered
    @ChildResource(name = "numberedFootnotes")
    private List<NumberedItem> numberedFootnotes;

    // Multifield - Star
    @ChildResource(name = "starFootnotes")
    private List<StarItem> starFootnotes;

    // Getters

    public String getLabel() {
        return label;
    }

    public String getTargetId() {
        return targetId;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public String getCloseLabel() {
        return closeLabel;
    }

    public List<NumberedItem> getNumberedFootnotes() {
        return numberedFootnotes;
    }

    public List<StarItem> getStarFootnotes() {
        return starFootnotes;
    }
}
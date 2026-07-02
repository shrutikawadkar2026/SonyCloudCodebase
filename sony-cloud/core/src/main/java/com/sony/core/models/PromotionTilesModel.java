package com.sony.core.models;



import java.util.List;
import javax.inject.Inject;

import com.sony.core.models.pojo.PromotionTilesPojo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PromotionTilesModel {

    @Inject
    private String title;

    public String getTitle() {
        return title;
    }



    @ChildResource
    List<PromotionTilesPojo> tiles;

    public List<PromotionTilesPojo> getTiles() {
        return tiles;
    }
}

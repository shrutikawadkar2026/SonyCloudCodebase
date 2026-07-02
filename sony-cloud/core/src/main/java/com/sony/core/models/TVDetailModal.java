package com.sony.core.models;

import com.sony.core.services.TVDetailsService;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import org.json.JSONObject;

import javax.annotation.PostConstruct;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class TVDetailModal {

    @ValueMapValue
    private String tvModelNo;

    @OSGiService
    private TVDetailsService tvDetailsService;

    @SlingObject
    private ResourceResolver resolver;

    private String brand;
    private String screenSize;
    private String price;

    @PostConstruct
    protected void init() {

        try {

            JSONObject jsonObject =
                    tvDetailsService.getTVDetails(
                            tvModelNo,
                            resolver
                    );

            if (jsonObject != null) {

                brand =
                        jsonObject.optString("brand");

                screenSize =
                        jsonObject.optString("screenSize");

                price =
                        jsonObject.optString("price");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public String getTvModelNo() {
        return tvModelNo;
    }

    public String getBrand() {
        return brand;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public String getPrice() {
        return price;
    }
}
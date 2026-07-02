package com.sony.core.services.impl;

import com.sony.core.services.TVDetailsService;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.json.JSONObject;

import org.osgi.service.component.annotations.Component;

@Component(service = TVDetailsService.class)
public class TVDetailsServiceImpl implements TVDetailsService {

    @Override
    public JSONObject getTVDetails(
            String tvModelNo,
            ResourceResolver resolver) {

        JSONObject jsonObject = new JSONObject();

        try {

            String path =
                    "/content/dam/sony/tvdetails/"
                            + tvModelNo
                            + "/jcr:content/data/master";

            Resource resource =
                    resolver.getResource(path);

            System.out.println("SERVICE PATH = " + path);

            System.out.println("SERVICE RESOURCE = " + resource);

            if (resource != null) {

                jsonObject.put(
                        "tvModelNo",
                        tvModelNo
                );

                jsonObject.put(
                        "brand",
                        resource.getValueMap()
                                .get("brand", "")
                );

                jsonObject.put(
                        "screenSize",
                        resource.getValueMap()
                                .get("screenSize", "")
                );

                jsonObject.put(
                        "price",
                        resource.getValueMap()
                                .get("price", "")
                );

            } else {

                jsonObject.put(
                        "error",
                        "TV Model Not Found"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            jsonObject.put(
                    "error",
                    e.getMessage()
            );
        }

        return jsonObject;
    }
}
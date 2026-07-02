package com.sony.core.services.impl;

import com.day.cq.dam.api.AssetManager;
import com.sony.core.config.SimpleServiceConfig;
import com.sony.core.services.SimpleService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component(service = SimpleService.class, immediate = true)
@Designate(ocd = SimpleServiceConfig.class)
public class SimpleServiceImpl implements SimpleService {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleServiceImpl.class);

    private String filePath;
    private String description;
    private String statusMessage;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Activate
    @Modified
    protected void activate(SimpleServiceConfig config) {
        LOG.info("ACTIVATED");

        this.filePath = config.filePath();
        this.description = config.description();

        statusMessage = createFileInDAM();
    }

    private String createFileInDAM() {
        ResourceResolver resolver = null;

        try {
            resolver = resourceResolverFactory.getServiceResourceResolver(
                    Collections.singletonMap(
                            ResourceResolverFactory.SUBSERVICE,
                            "datawrite"
                    )
            );

            AssetManager assetManager = resolver.adaptTo(AssetManager.class);

            if (assetManager == null) {
                return "Error: AssetManager not available";
            }

            ByteArrayInputStream inputStream =
                    new ByteArrayInputStream(description.getBytes(StandardCharsets.UTF_8));


            assetManager.createAsset(filePath, inputStream, "text/plain", true);

            resolver.commit();

            LOG.info("File created in DAM at {}", filePath);

            return "File created in DAM: " + filePath;

        } catch (LoginException e) {
            LOG.error("Service user login failed", e);
            return "Error: Service user not configured";

        } catch (Exception e) {
            LOG.error("Error creating file in DAM", e);
            return "Error: " + e.getMessage();

        } finally {
            if (resolver != null && resolver.isLive()) {
                resolver.close();
            }
        }
    }

    @Override
    public String processFile() {
        return statusMessage;
    }
}
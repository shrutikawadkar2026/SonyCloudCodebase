package com.sony.core.listeners;

import com.day.cq.dam.api.Asset;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component(
        service = EventHandler.class,
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC,
        }
)

public class ReplicationEventHandler implements EventHandler {

    private static final Logger LOG =
            LoggerFactory.getLogger(ReplicationEventHandler.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void handleEvent(final Event event) {

        LOG.info("Event Type : {}", event.getTopic());

        ReplicationAction replicationAction =
                ReplicationAction.fromEvent(event);

        if (replicationAction == null) {
            return;
        }

        String path = replicationAction.getPath();

        ReplicationActionType type =
                replicationAction.getType();

        LOG.info("Replication Path : {}", path);





        if (type.equals(ReplicationActionType.ACTIVATE)
                && path != null
                && path.startsWith("/content/dam/assessment")) {

            LOG.info("Assessment Asset Published : {}", path);

            try (ResourceResolver resourceResolver =
                         resourceResolverFactory
                                 .getServiceResourceResolver(
                                         Collections.singletonMap(
                                                 ResourceResolverFactory.SUBSERVICE,
                                                 "datawrite"
                                         )
                                 )) {

                Resource assetResource =
                        resourceResolver.getResource(path);

                if (assetResource == null) {

                    LOG.error(
                            "Asset resource not found for path : {}",
                            path
                    );

                    return;
                }

                Asset asset =
                        assetResource.adaptTo(Asset.class);

                if (asset == null) {

                    LOG.error(
                            "Could not adapt resource to Asset : {}",
                            path
                    );

                    return;
                }

                String fileName = asset.getName();

                Resource metadataResource =
                        resourceResolver.getResource(
                                path + "/jcr:content/metadata"
                        );

                if (metadataResource == null) {

                    LOG.error(
                            "Metadata resource not found for asset : {}",
                            path
                    );

                    return;
                }

                ValueMap metadataProps =
                        metadataResource.getValueMap();

                String country =
                        metadataProps.get(
                                "pdfx:Country",
                                "Null"
                        );

                String language =
                        metadataProps.get(
                                "pdfx:Language",
                                "Null"
                        );

                String audienceType =
                        metadataProps.get(
                                "pdfx:Audience",
                                "Null"
                        );

                String fileType =
                        metadataProps.get(
                                "dc:format",
                                "Null"
                        );

                Resource varResource =
                        resourceResolver.getResource("/var");

                if (varResource == null) {

                    LOG.error("/var resource not found");
                    return;
                }

                Resource assessmentResource =
                        resourceResolver.getResource(
                                "/var/assessment"
                        );

                if (assessmentResource == null) {

                    Map<String, Object> assessmentProperties =
                            new HashMap<>();

                    assessmentProperties.put(
                            "jcr:primaryType",
                            "nt:unstructured"
                    );

                    assessmentResource =
                            resourceResolver.create(
                                    varResource,
                                    "assessment",
                                    assessmentProperties
                            );

                    LOG.info(
                            "/var/assessment node created successfully"
                    );
                }

                Resource fileResource =
                        resourceResolver.getResource(
                                "/var/assessment/" + fileName
                        );

                if (fileResource == null) {

                    Map<String, Object> fileProperties =
                            new HashMap<>();

                    fileProperties.put(
                            "jcr:primaryType",
                            "nt:unstructured"
                    );

                    fileResource =
                            resourceResolver.create(
                                    assessmentResource,
                                    fileName,
                                    fileProperties
                            );

                    LOG.info(
                            "File node created under /var/assessment/{}",
                            fileName
                    );
                }

                ModifiableValueMap properties =
                        fileResource.adaptTo(
                                ModifiableValueMap.class
                        );

                if (properties != null) {

                    properties.put(
                            "country",
                            country
                    );

                    properties.put(
                            "language",
                            language
                    );

                    properties.put(
                            "audienceType",
                            audienceType
                    );

                    properties.put(
                            "fileType",
                            fileType
                    );

                    properties.put(
                            "replicatedBy",
                            replicationAction.getUserId()
                    );

                    resourceResolver.commit();

                    LOG.info(
                            "Metadata stored successfully under /var/assessment/{}",
                            fileName
                    );

                } else {

                    LOG.error(
                            "Could not adapt file resource to ModifiableValueMap"
                    );
                }

            } catch (LoginException | PersistenceException e) {

                LOG.error(
                        "Error while storing metadata : {}",
                        e.getMessage(),
                        e
                );
            }
        }

        if (type.equals(ReplicationActionType.DEACTIVATE)) {

            LOG.info(
                    "Asset Deactivated : {}",
                    path
            );
        }
    }
}





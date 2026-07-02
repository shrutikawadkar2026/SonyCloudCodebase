package com.sony.core.listeners;

import com.sony.core.services.AuditService;

import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(
        service = ResourceChangeListener.class,
        property = {
                ResourceChangeListener.PATHS + "=/content/sony",
                ResourceChangeListener.CHANGES + "=ADDED",
                ResourceChangeListener.CHANGES + "=CHANGED",
                ResourceChangeListener.CHANGES + "=REMOVED"
        }
)

public class PageAuditListener implements ResourceChangeListener {

    @Reference
    private AuditService auditService;

    @Override
    public void onChange(List<ResourceChange> changes) {

        for (ResourceChange change : changes) {

            String path = change.getPath();

            if (!path.contains("jcr:content")) {

                auditService.logPageEvent(
                        change.getType().toString(),
                        path
                );

            }
        }
    }
}
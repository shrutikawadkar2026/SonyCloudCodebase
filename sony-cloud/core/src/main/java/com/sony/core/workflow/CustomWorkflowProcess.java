package com.sony.core.workflow;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.osgi.service.component.annotations.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = WorkflowProcess.class,
        property = {
                "process.label=Custom Workflow Process"
        }
)
public class CustomWorkflowProcess implements WorkflowProcess {

    private static final Logger LOG =
            LoggerFactory.getLogger(CustomWorkflowProcess.class);

    @Override
    public void execute(
            WorkItem workItem,
            WorkflowSession workflowSession,
            MetaDataMap metaDataMap)
            throws WorkflowException {

        try {


            String payloadPath =
                    workItem.getWorkflowData()

                            .getPayload()
                            .toString();

            LOG.info("Payload Path : {}", payloadPath);


            ResourceResolver resourceResolver =
                    workflowSession.adaptTo(ResourceResolver.class);


            Resource resource =
                    resourceResolver.getResource(payloadPath);

            if (resource != null) {


                ModifiableValueMap properties =
                        resource.adaptTo(ModifiableValueMap.class);


                properties.put("workflowStatus", "approved");

                resourceResolver.commit();

                LOG.info("Property Added Successfully");
            }

        } catch (Exception e) {

            LOG.error("Workflow Error", e);
        }
    }
}
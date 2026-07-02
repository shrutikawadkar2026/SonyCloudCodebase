package com.sony.core.workflow;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.exec.WorkItem;

import com.adobe.granite.workflow.metadata.MetaDataMap;

import org.osgi.service.component.annotations.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = WorkflowProcess.class,
        property = {
                "process.label=WorkPageVersion"
        }
)
public class WorkPageVersion implements WorkflowProcess {

    private static final Logger log =
            LoggerFactory.getLogger(WorkPageVersion.class);

    @Override
    public void execute(
            WorkItem workItem,

            WorkflowSession workflowSession,
            MetaDataMap metaDataMap) {

        log.info("Workflow Started");

        String path = workItem
                .getWorkflowData()
                .getPayload()
                .toString();

        log.info("Payload Path: {}", path);
    }
}
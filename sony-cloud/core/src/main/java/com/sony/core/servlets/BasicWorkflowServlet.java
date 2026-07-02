package com.sony.core.servlets;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;


@Component(service = Servlet.class,
        property = {
                "sling.servlet.methods=GET",
                "sling.servlet.paths=/bin/basicWorkflow"
        })
public class BasicWorkflowServlet extends SlingAllMethodsServlet {

    private final Logger LOG = LoggerFactory.getLogger(BasicWorkflowServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        ResourceResolver resourceResolver = request.getResourceResolver();
        String returnStatus = "Workflow in progress";
        try {

            WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);
            WorkflowModel workflowModel = workflowSession.getModel("/var/workflow/models/workpageversion");
            WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH","/content/sony/us/en/workflow");
            returnStatus = workflowSession.startWorkflow(workflowModel, workflowData).getState();

        } catch (WorkflowException e) {
            LOG.info("Exception Occurred:{}",e.getMessage());
        }
        response.setContentType("application/json");
        response.getWriter().write(returnStatus);
    }
}
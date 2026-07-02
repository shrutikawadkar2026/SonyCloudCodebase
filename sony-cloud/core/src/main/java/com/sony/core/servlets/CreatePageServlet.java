package com.sony.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;

import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/createPage")
public class CreatePageServlet extends SlingAllMethodsServlet {
    private static final String PARAM_PARENT_PATH = "/content/sony/us/en";
    private static final List<String> PARAM_PAGE_NAME = List.of("career", "Tset1", "Test2");
 //   private static final String PARAM_TITLE       = "Carrer";
    private static final String PARAM_TEMPLATE    = "/conf/sony/settings/wcm/templates/home-page";


    @Override
    protected void doGet( SlingHttpServletRequest request,  SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        ResourceResolver resourceResolver = request.getResourceResolver();

        if (PARAM_PARENT_PATH == null || PARAM_PAGE_NAME == null || PARAM_TEMPLATE.isEmpty()) {

            response.getWriter().write("Enter All required Fields... ");
            return;
        }

        PageManager pageManager = resourceResolver
                .adaptTo(PageManager.class);

        if (pageManager == null) {
            response.getWriter().write("PageManager not available!");
            return;

        }

        for (String pageName : PARAM_PAGE_NAME) {

            String fullPath = PARAM_PARENT_PATH + "/" + pageName;

            if (pageManager.getPage(fullPath) != null) {
                response.getWriter().write("Page already exists: " + fullPath + "\n");
                continue;
            }

            try {
                Page page = pageManager.create(
                        PARAM_PARENT_PATH,
                        pageName,
                        PARAM_TEMPLATE,
                        pageName
                );


                Resource contentResource = page.getContentResource();


                Map<String, Object> props = new HashMap<>();
                props.put("jcr:primaryType", "nt:unstructured");
                props.put("customTitle", "Node for " + pageName);

                resourceResolver.create(contentResource, "myNode", props);


                response.getWriter().write("Created: " + page.getPath() + "\n");

            } catch (WCMException e) {
                response.getWriter().write("Error creating " + pageName + ": " + e.getMessage() + "\n");
            }
        }


        resourceResolver.commit();
    }
}

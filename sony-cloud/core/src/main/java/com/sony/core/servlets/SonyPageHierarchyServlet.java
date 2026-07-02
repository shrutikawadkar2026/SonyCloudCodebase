package com.sony.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "sony/components/page",
        methods       = HttpConstants.METHOD_GET,
        selectors     = "hierarchy",
        extensions    = "txt"
)
public class SonyPageHierarchyServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        // The resource is the jcr:content node of the page you hit the URL on
        Resource jcrContent = request.getResource();

        // Go up one level to get the actual page node
        Resource pageResource = jcrContent.getParent();

        PageManager pageManager = request.getResourceResolver()
                .adaptTo(PageManager.class);

        if (pageManager == null || pageResource == null) {
            out.println("ERROR: Could not resolve page.");
            return;
        }

        Page rootPage = pageManager.getPage(pageResource.getPath());

        if (rootPage == null) {
            out.println("ERROR: Page not found at " + pageResource.getPath());
            return;
        }

        out.println("=======================================================");
        out.println("  SONY PAGE HIERARCHY REPORT");
        out.println("=======================================================");
        out.println();

        // Start recursive printing from the current page
        printPageHierarchy(rootPage, out, 0);

        out.println();
        out.println("=======================================================");
        out.println("  END OF REPORT");
        out.println("=======================================================");
    }

    /**
     * Recursively prints page title, path, and all components it contains.
     */
    private void printPageHierarchy(Page page, PrintWriter out, int depth) {

        // Build indent based on depth
        String indent      = "  ".repeat(depth);
        String childIndent = "  ".repeat(depth + 1);

        // ── Print page info ──────────────────────────────────────────────
        out.println(indent + "┌─────────────────────────────────────────────");
        out.println(indent + "│  PAGE TITLE : " + page.getTitle());
        out.println(indent + "│  PAGE PATH  : " + page.getPath());

        // ── Print components inside this page ────────────────────────────
        Resource jcrContent = page.getContentResource();

        if (jcrContent != null) {
            // Components live inside jcr:content/root (or parsys)
            Resource root = jcrContent.getChild("root");

            if (root != null) {
                out.println(indent + "│  COMPONENTS :");
                printComponents(root, out, childIndent);
            } else {
                out.println(indent + "│  COMPONENTS : (no root container found)");
            }
        } else {
            out.println(indent + "│  COMPONENTS : (no jcr:content)");
        }

        out.println(indent + "└─────────────────────────────────────────────");
        out.println();

        // ── Recurse into child pages ─────────────────────────────────────
        Iterator<Page> children = page.listChildren();
        while (children.hasNext()) {
            Page child = children.next();
            printPageHierarchy(child, out, depth + 1);
        }
    }


    private void printComponents(Resource container,
                                 PrintWriter out,
                                 String indent) {

        for (Resource child : container.getChildren()) {

            ValueMap props = child.getValueMap();
            String resourceType = props.get("sling:resourceType", String.class);

            if (resourceType != null) {
                // Extract just the component name (last segment of the path)
                String componentName = resourceType
                        .substring(resourceType.lastIndexOf("/") + 1);

                out.println(indent + "   ├── Component Name : " + componentName);
                out.println(indent + "   │   Full Type     : " + resourceType);
                out.println(indent + "   │   Node Path     : " + child.getPath());
                out.println(indent + "   │");
            }

            // Recurse into nested containers (responsive grid, parsys inside parsys)
            if (child.hasChildren()) {
                printComponents(child, out, indent + "  ");
            }
        }
    }
}
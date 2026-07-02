package com.sony.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.jcr.*;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;

@Component(service= Servlet.class,
property = {
        "sling.servlet.paths=/bin/lmn",
        "sling.servlet.method=get"
})
public class PracticeNodeCreatingServlet extends SlingSafeMethodsServlet {
    @Override
    protected void doGet( SlingHttpServletRequest request,  SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resourceResolver = request.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        String keyword = request.getParameter("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            response.getWriter().write("give path properly");
            return;
        }
        try {
            Node node = session.getNode(keyword);
            if (!(session.nodeExists(keyword))) {
                response.getWriter().write("node not exist");
                return;
            }
            response.getWriter().write("Parent node\n\n" + node.getName());
            NodeIterator childnodes = node.getNodes();
            while (childnodes.hasNext()) {
                Node childNode = childnodes.nextNode();
                response.getWriter().write("\n\n" + childNode.getName() + "\n\n" + childNode.getPath());


                //to read the proprties of node all the property use iterator
                PropertyIterator iterator= node.getProperties();
                while(iterator.hasNext())
                {
                    //for single property
                    Property property= iterator.nextProperty();
                    response.getWriter().write("\nproperty name: "+property.getName()+"    Value= "+property.getValue().getString());
                }

            }

            if(!(node.getName()=="testnode")) {

                //creating node using addNode method and we can set another properties also by using
                // setProperty
                Node creatednode = node.addNode("testnode", "nt:unstructured");
                creatednode.setProperty("jcr:title", "testtitle");
                creatednode.setProperty("countries",new String[]{"India","USA","Nepal"});

                session.save();
            }
            else{
                response.getWriter().write("Node already exist");
            }
            Node parent= session.getNode(keyword);
            Node child=session.getNode("demo");
                child.remove();


        } catch (RepositoryException e) {

        }
    }

}

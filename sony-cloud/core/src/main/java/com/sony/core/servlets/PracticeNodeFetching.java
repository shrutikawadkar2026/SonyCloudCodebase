package com.sony.core.servlets;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.jcr.*;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service= Servlet.class,
property={
        "sling.servlet.paths=/bin/practicefetchingnodes",
        "sling.servlet.methods=GET"
})
public class PracticeNodeFetching extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        ResourceResolver resourceResolver = request.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);

        String keyword = request.getParameter("keyword");

        if(keyword==null || keyword.trim().isEmpty()){
            response.getWriter().write("Provide path properly");
            return;
        }


       try {
           if(!(session.nodeExists(keyword))){
               response.getWriter().write("node not exist in this path");
               return;

           }
           Node node= session.getNode(keyword);
           response.getWriter().write("\nParent node: "+node.getName());
           NodeIterator childnodes= node.getNodes();
           while(childnodes.hasNext())
           {
               Node childnode= childnodes.nextNode();
               response.getWriter().write("\nnode name "+childnode.getName()+"\nNodePath "+childnode.getPath());
           }


       } catch (RepositoryException e) {
           response.getWriter().write("\nError occurred"+e.getMessage());
       }


    }
}

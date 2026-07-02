package com.sony.core.services.impl;


import com.sony.core.services.PracticeReadPropertiesService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.*;
import java.util.HashMap;
import java.util.Map;

@Component(service = PracticeReadPropertiesService.class)
public class PracticeReadPropertiesServiceImpl implements PracticeReadPropertiesService{
    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    @Override
    public String fetchingProperties(String path) {
        StringBuilder result=new StringBuilder();
        Map<String,Object>param=new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE,"datawrite");
        try{
            ResourceResolver resourceResolver= resourceResolverFactory.getServiceResourceResolver(param);
            Resource resource=resourceResolver.getResource(path);
            if(resource == null)
            {
                return"resource not found";
            }
            Node node=resource.adaptTo(Node.class);
            if(node==null){
                return "node not found";
            }
            PropertyIterator childproperty= node.getProperties();
            while(childproperty.hasNext()){
                Property property= childproperty.nextProperty();
                result.append(property.getName()).append("\n\n").append(property.getString()).append("\n\n");
            }

        } catch (LoginException e) {

        } catch (RepositoryException e) {

        }


   return result.toString();
    }

}

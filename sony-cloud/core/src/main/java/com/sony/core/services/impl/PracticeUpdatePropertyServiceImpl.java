//package com.sony.core.services.impl;
//
//
//import com.sony.core.services.PracticeUpdatePropertyServiceInterface;
//import org.apache.sling.api.resource.LoginException;
//import org.apache.sling.api.resource.Resource;
//import org.apache.sling.api.resource.ResourceResolver;
//import org.apache.sling.api.resource.ResourceResolverFactory;
//import org.osgi.service.component.annotations.Component;
//import org.osgi.service.component.annotations.Reference;
//
//import javax.jcr.Node;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component(service = PracticeUpdatePropertyServiceInterface.class)
//public class PracticeUpdatePropertyServiceImpl implements PracticeUpdatePropertyServiceInterface{
//
//    @Reference
//    ResourceResolverFactory resourceResolverFactory;
//
//    @Override
//    public String updateProperty(String path, String propertyName, String propertyValue) {
//        StringBuilder result=new StringBuilder();
//        Map<String,Object>param=new HashMap<>();
//        param.put(ResourceResolverFactory.SUBSERVICE,"datawrite");
//        try{
//            ResourceResolver resourceResolver=resourceResolverFactory.getServiceResourceResolver(param);
//            Resource resource=resourceResolver.getResource(path);
//            Node node=
//        } catch (LoginException e) {
//
//        }
//
//    }
//}

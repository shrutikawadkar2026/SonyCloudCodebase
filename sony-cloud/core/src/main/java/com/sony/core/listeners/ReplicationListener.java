//package com.sony.core.listeners;
//
//import com.day.cq.replication.ReplicationAction;
//
//import com.sony.core.services.ReplicationService;
//
//import org.osgi.service.component.annotations.Component;
//import org.osgi.service.component.annotations.Reference;
//
//import org.osgi.service.event.Event;
//import org.osgi.service.event.EventHandler;
//
//@Component(
//        service = EventHandler.class,
//        immediate = true,
//        property = {
//                "event.topics=com/day/cq/replication"
//        }
//)
//
//public class ReplicationListener implements EventHandler {
//
//    @Reference
//    private ReplicationService replicationService;
//
//    @Override
//    public void handleEvent(Event event) {
//
//        ReplicationAction action =
//                ReplicationAction.fromEvent(event);
//
//        if (action != null) {
//
//            String path = action.getPath();
//
//            replicationService.processReplication(path);
//
//        }
//    }
//}
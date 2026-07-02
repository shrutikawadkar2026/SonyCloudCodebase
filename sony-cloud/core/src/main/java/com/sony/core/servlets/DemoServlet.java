package com.sony.core.servlets;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.Query;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.result.SearchResult;
import com.day.cq.search.result.Hit;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import javax.servlet.Servlet;
import javax.jcr.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Component(service = Servlet.class, property = {
        "sling.servlet.paths=/bin/cfSearch",
        "sling.servlet.methods=GET"
})
public class DemoServlet extends SlingSafeMethodsServlet {

    @Reference
    private QueryBuilder queryBuilder;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword"); // Query param for keyword
        String modelPath = request.getParameter("model"); // Optional: Filter by CF Model

        Map<String, String> map = new HashMap<>();
        map.put("path", "/content/dam/products");
        map.put("type", "dam:Asset");

        // Predicates specifically for Content Fragments
        map.put("1_property", "jcr:content/contentFragment");
        map.put("1_property.value", "true");

        // Full-text search for the keyword
        if (keyword != null && !keyword.isEmpty()) {
            map.put("fulltext", keyword);
            // Optional: specify relative path to search within master variation data
            map.put("fulltext.relPath", "jcr:content/data/master");
        }

        // Optional: Filter by specific Content Fragment Model
        if (modelPath != null && !modelPath.isEmpty()) {
            map.put("2_property", "jcr:content/data/cq:model");
            map.put("2_property.value", modelPath);
        }

        Session session = request.getResourceResolver().adaptTo(Session.class);
        Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
        SearchResult result = query.getResult();

        // Build JSON output
        JsonObject jsonResponse = new JsonObject();
        JsonArray items = new JsonArray();

        for (Hit hit : result.getHits()) {
            try {
                JsonObject item = new JsonObject();
                item.addProperty("path", hit.getPath());
                item.addProperty("title", hit.getTitle());
                items.add(item);
            } catch (Exception ignored) {}
        }

        jsonResponse.add("results", items);
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
}


//@Override
//protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
//    try {
//        ResourceResolver resourceResolver = request.getResourceResolver();
//        Session session = resourceResolver.adaptTo(Session.class);
//
//        // 1. Basic Setup
//        Map<String, String> map = new HashMap<>();
//        map.put("path", "/content/dam/products");
//        map.put("type", "dam:Asset");
//        map.put("p.limit", "10"); // Use p.limit for QueryBuilder
//
//        // 2. Fulltext Search (Keyword)
//        String keyword = request.getParameter("keyword");
//        if (keyword != null && !keyword.isEmpty()) {
//            map.put("fulltext", keyword);
//            map.put("fulltext.relPath", "jcr:content/data/master");
//        }
//
//        // 3. Dynamic Property Filtering
//        // Example URL: /bin/productSearch?color=red&category=electronics
//        int propertyCount = 1;
//        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue()[0];
//
//            // Filter out 'keyword' since it's already handled, and ignore empty values
//            if (!key.equals("keyword") && value != null && !value.isEmpty()) {
//                // Creates predicates like: 1_property=color, 1_property.value=red
//                map.put(propertyCount + "_property", "jcr:content/data/master/" + key);
//                map.put(propertyCount + "_property.value", value);
//                propertyCount++;
//            }
//        }
//
//        // 4. Execute Query
//        Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
//        SearchResult result = query.getResult();
//
//        // 5. Build JSON Response
//        JsonObject jsonResponse = new JsonObject();
//        JsonArray items = new JsonArray();
//
//        for (Hit hit : result.getHits()) {
//            JsonObject item = new JsonObject();
//            item.addProperty("path", hit.getPath());
//            item.addProperty("title", hit.getTitle());
//            items.add(item);
//        }
//
//        jsonResponse.addProperty("totalMatches", result.getTotalMatches());
//        jsonResponse.add("results", items);
//
//        response.setContentType("application/json");
//        response.getWriter().write(new Gson().toJson(jsonResponse));
//
//    } catch (RepositoryException e) {
//        response.setStatus(500);
//        response.getWriter().write("{\"error\": \"Search failed\"}");
//    }
//}



//@Override
//protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
//    response.setContentType("application/json");
//    response.setCharacterEncoding("UTF-8");
//
//    JsonObject jsonResponse = new JsonObject();
//    JsonArray items = new JsonArray();
//    String keyword = request.getParameter("keyword");
//
//    // 1. Validation: Return HTTP 400 if keyword is null, empty, or just spaces
//    if (keyword == null || keyword.trim().isEmpty()) {
//        response.setStatus(400); // Bad Request
//        jsonResponse.addProperty("success", false);
//        jsonResponse.addProperty("message", "Invalid or empty keyword");
//        response.getWriter().write(new Gson().toJson(jsonResponse));
//        return;
//    }
//
//    // 2. Prevention: Remove manual wildcards to prevent injection/misuse
//    keyword = keyword.replace("*", "").replace("%", "").trim();
//
//    try {
//        ResourceResolver resourceResolver = request.getResourceResolver();
//        Session session = resourceResolver.adaptTo(Session.class);
//
//        Map<String, String> map = new HashMap<>();
//        map.put("path", "/content/dam/products");
//        map.put("type", "dam:Asset");
//        map.put("p.limit", "10");
//
//        // Use the cleaned keyword safely
//        map.put("nodename", "*" + keyword.toLowerCase() + "*");
//
//        int propertyCount = 1;
//        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
//            String key = entry.getKey();
//            String[] values = entry.getValue();
//
//            if (!key.equals("keyword") && values.length > 0 && !values[0].isEmpty()) {
//                map.put(propertyCount + "_property", "jcr:content/data/master/" + key);
//                map.put(propertyCount + "_property.value", values[0]);
//                propertyCount++;
//            }
//        }
//
//        Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
//        SearchResult result = query.getResult();
//
//        for (Hit hit : result.getHits()) {
//            try {
//                JsonObject item = new JsonObject();
//                item.addProperty("path", hit.getPath());
//                item.addProperty("title", hit.getTitle());
//                item.addProperty("name", hit.getTitle());
//                items.add(item);
//            } catch (RepositoryException e) {
//                // Ignore single hit errors
//            }
//        }
//
//        jsonResponse.addProperty("success", true);
//        jsonResponse.addProperty("totalResults", result.getTotalMatches());
//        jsonResponse.add("results", items);
//
//    } catch (Exception e) {
//        response.setStatus(500);
//        jsonResponse.addProperty("success", false);
//        jsonResponse.addProperty("message", "Error: " + e.getMessage());
//    }
//
//    response.getWriter().write(new Gson().toJson(jsonResponse));
//}

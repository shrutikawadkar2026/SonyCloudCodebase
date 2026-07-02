//package com.sony.core.services.impl;
//
//import com.sony.core.config.EmployeeConfig;
//import com.sony.core.services.EmployeeService;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import org.osgi.service.component.annotations.Activate;
//import org.osgi.service.component.annotations.Component;
//import org.osgi.service.metatype.annotations.Designate;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//@Component(service = EmployeeService.class)
//
//@Designate(ocd = EmployeeConfig.class)
//
//public class EmployeeServiceImpl implements EmployeeService {
//
//    private String employeeEndpoint;
//
//    @Activate
//    protected void activate(EmployeeConfig config) {
//
//        this.employeeEndpoint = config.employeeEndpoint();
//    }
//
//    @Override
//    public String getEmployeeData() {
//
//        try {
//
//            URL url = new URL(employeeEndpoint);
//
//            HttpURLConnection connection =
//                    (HttpURLConnection) url.openConnection();
//
//            connection.setRequestMethod("GET");
//
//            BufferedReader reader =
//                    new BufferedReader(
//                            new InputStreamReader(connection.getInputStream())
//                    );
//
//            StringBuilder response = new StringBuilder();
//
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//
//                response.append(line);
//            }
//
//            reader.close();
//
//            JSONObject mainObject =
//                    new JSONObject(response.toString());
//
//            JSONArray usersArray =
//                    mainObject.getJSONArray("users");
//
//            JSONArray employeeArray = new JSONArray();
//
//            for (int i = 0; i < usersArray.length(); i++) {
//
//                JSONObject userObject =
//                        usersArray.getJSONObject(i);
//
//                JSONObject companyObject =
//                        userObject.getJSONObject("company");
//
//                JSONObject employeeObject =
//                        new JSONObject();
//
//                employeeObject.put(
//                        "firstName",
//                        userObject.getString("firstName")
//                );
//
//                employeeObject.put(
//                        "lastName",
//                        userObject.getString("lastName")
//                );
//
//                employeeObject.put(
//                        "image",
//                        userObject.getString("image")
//                );
//
//                employeeObject.put(
//                        "department",
//                        companyObject.getString("department")
//                );
//
//                employeeObject.put(
//                        "title",
//                        companyObject.getString("title")
//                );
//
//                employeeArray.put(employeeObject);
//            }
//
//            JSONObject finalObject =
//                    new JSONObject();
//
//            finalObject.put("employees", employeeArray);
//
//            return finalObject.toString();
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//        return "{}";
//    }
//}
//
//
//
////package com.sony.core.services.impl;
////
////
////
////import com.sony.core.config.EmployeeConfig;
////import com.sony.core.services.EmployeeService;
////
////import org.osgi.service.component.annotations.Activate;
////import org.osgi.service.component.annotations.Component;
////import org.osgi.service.metatype.annotations.Designate;
////
////@Component(service = EmployeeService.class)
////
////@Designate(ocd = EmployeeConfig.class)
////
////public class EmployeeServiceImpl implements EmployeeService {
////
////    private String employeeEndpoint;
////
////    @Activate
////    protected void activate(EmployeeConfig config) {
////
////        this.employeeEndpoint = config.employeeEndpoint();
////    }
////
////    @Override
////    public String getEmployeeEndpoint() {
////
////        return employeeEndpoint;
////    }
////}

//package com.sony.core.servlets;
//
//
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//
//import javax.servlet.Servlet;
//import javax.servlet.ServletException;
//
//import org.apache.sling.api.SlingHttpServletRequest;
//import org.apache.sling.api.SlingHttpServletResponse;
//import org.apache.sling.api.servlets.SlingAllMethodsServlet;
//
//import org.osgi.service.component.annotations.Component;
//
//import org.apache.sling.servlets.annotations.SlingServletPaths;
//
//@Component(service = Servlet.class)
//@SlingServletPaths("/bin/authorsubmit")
//public class AuthorSubmitServlet extends SlingAllMethodsServlet {
//
//    @Override
//    protected void doPost(SlingHttpServletRequest request,
//                          SlingHttpServletResponse response)
//            throws ServletException, IOException {
//
//        String name = request.getParameter("author_name");
//        String dob = request.getParameter("author_dob");
//        String email = request.getParameter("author_email");
//        String gender = request.getParameter("author_gender");
//
//        try {
//
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            Connection con = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/aemsony",
//                    "root",
//                    "mysql@aem");
//
//            String query = "INSERT INTO authors(name,dob,email,gender) VALUES(?,?,?,?)";
//
//            PreparedStatement ps = con.prepareStatement(query);
//
//            ps.setString(1, name);
//            ps.setString(2, dob);
//            ps.setString(3, email);
//            ps.setString(4, gender);
//
//            ps.executeUpdate();
//
//            con.close();
//
//            response.getWriter().write("Data Saved Successfully");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
package com.todo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TodoApp extends HttpServlet {

    // Database credentials
    private static final String db = "jdbc:mysql://localhost:3306/jsptodo";
    private static final String user = "root";
    private static final String pw = "Nethalk@123";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the task from the request
        String todo = req.getParameter("task");

        // Set the content type of the response
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement selectStmt = null;
        ResultSet rs = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            conn = DriverManager.getConnection(db, user, pw);

            // If a task is provided, insert it into the database
            if (todo != null && !todo.trim().isEmpty()) {
                // SQL INSERT statement
                String sql = "INSERT INTO task (taskname) VALUES (?)";

                // Create a PreparedStatement
                pstmt = conn.prepareStatement(sql);

                // Set the value
                pstmt.setString(1, todo);

                // Execute the INSERT statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                    out.println("<h1>Task added successfully: " + todo + "</h1>");
                } else {
                    out.println("<h1>Failed to add task: " + todo + "</h1>");
                }
            }

            // SQL SELECT statement to retrieve all tasks
            String selectSql = "SELECT taskname FROM task";

            // Create a PreparedStatement for the SELECT statement
            selectStmt = conn.prepareStatement(selectSql);

            // Execute the SELECT statement
            rs = selectStmt.executeQuery();

            // Store the tasks in a List
            List<String> tasks = new ArrayList<>();
            while (rs.next()) {
                tasks.add(rs.getString("taskname"));
            }

            // Set the tasks as a request attribute
            req.setAttribute("tasks", tasks);

            // Forward the request to the JSP page
            req.getRequestDispatcher("/index.jsp").forward(req, resp);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<h1>Error: " + e.getMessage() + "</h1>");
        } finally {
            // Close the resources
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (selectStmt != null) {
                try {
                    selectStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>To Do List</title>
<link href="https://unpkg.com/tailwindcss@^1.0/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>
<div class="container mx-auto my-10">
    <h1 class="text-center text-3xl font-semibold mb-4">To Do List</h1>
    <div class="md:w-1/2 mx-auto">
        <div class="bg-white shadow-md rounded-lg p-6">
            <form method="get" action="todoApp">
                <div class="flex mb-4">
                    <input type="text" class="w-full px-4 py-2 mr-2 rounded-lg border-gray-300 focus:outline-none focus:border-blue-500" name="task" placeholder="Add new task" required>
                    <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" type="submit">Add</button>
                </div>
            </form>
            <ul>
                <%
                    // Retrieve the tasks from the request attribute
                    List<String> tasks = (List<String>) request.getAttribute("tasks");
                    if (tasks != null) {
                        for (String task : tasks) {
                            out.println("<li class='mb-2 px-4 py-2 border rounded'>" + task + "</li>");
                        }
                    } else {
                        out.println("<li class='mb-2 px-4 py-2 border rounded'>No tasks found.</li>");
                    }
                %>
            </ul>
        </div>
    </div>
</div>
</body>
</html>

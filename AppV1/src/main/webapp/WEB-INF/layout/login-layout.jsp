<%-- 
    Document   : loginLayout
    Created on : Sep 1, 2025, 10:46:33 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
    <head>
        <title><tiles:insertAttribute name="title" ignore="true" /></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>
    <body>
        <tiles:insertAttribute name="content" />
    </body>
</html>

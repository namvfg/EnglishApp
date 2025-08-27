<%-- 
    Document   : login
    Created on : Aug 15, 2025, 8:19:02 PM
    Author     : Admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url value="/login" var="action" />
<form method="post" action="${action}">
    <div class="form-floating mb-3 mt-3">
        <input type="text" class="form-control" id="email" placeholder="Enter email" name="email">
        <label for="email">Email</label>
    </div>

    <div class="form-floating mt-3 mb-3">
        <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="password">
        <label for="pwd">Password</label>
    </div>
    
    <div class="form-floating mt-3 mb-3">
        <input type="submit" value="Login" class="btn btn-info" />
    </div>
    
</form>


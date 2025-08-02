<%-- 
    Document   : lessons
    Created on : Aug 1, 2025, 12:13:00 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1 class="text-center text-success">
    Add category
</h1>
<c:url value="/categories" var="action"/>
<form:form method="post" action="${action}" modelAttribute="category">
    <form:errors path="*" element="div" cssClass="alert alert-danger" />
    <div class="form-floating mb-3 mt-3">
        <form:input path="name" type="text" class="form-control" id="name" placeholder="Type name" name="name"/>
        <label for="name">Name</label>
        <form:errors path="name" element="div" cssClass="text-danger" />
    </div>

    <div class="form-floating">
        <form:select path="categoryTypeId" class="form-select" id="categoryType" name="categoryType">
            <c:forEach items="${categoryTypes}" var="ct">
                <option value="${ct.id}">${ct.name}</option>
            </c:forEach>
        </form:select>
        <label for="categoryType" class="form-label">Select list (select one):</label>
    </div>
    <button class="btn btn-info mt-2 mb-2">Add</button>
</form:form>

<%-- 
    Document   : lessons
    Created on : Aug 1, 2025, 12:13:00 PM
    Author     : Admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1 class="text-center text-success">
    Category
</h1>
<c:url value="/categories" var="action"/>
<div class="row">
    <div class="col-3">
        <h3>Category Info</h3>
        <form:form method="post" action="${action}" modelAttribute="category">
            <form:errors path="*" element="div" cssClass="alert alert-danger" />
            <form:hidden path="id"/>
            <div class="form-floating mb-3 mt-3">
                <form:input path="name" type="text" class="form-control" id="name" placeholder="Type name" name="name"/>
                <label for="name">Name</label>
                <form:errors path="name" element="div" cssClass="text-danger" />
            </div>

            <div class="form-floating">
                <form:select path="categoryTypeId" class="form-select" id="categoryType">
                    <form:options items="${categoryTypes}" itemValue="id" itemLabel="name" />
                </form:select>
                <label for="categoryType" class="form-label">Select list:</label>
            </div>

            <div class="form-floating mt-2 mb-2">
                <button class="btn btn-info">
                    <c:choose>
                        <c:when test="${category.id == null}">Add</c:when>
                        <c:otherwise>Update Information</c:otherwise>
                    </c:choose>
                </button>
            </div>

        </form:form>
    </div>
    <div class="col-9 border border-3 rounded mt-3 overflow-auto p-0" style="height: 70vh">
        <c:choose>
            <c:when test="${category.id != null}">
                <nav class="navbar navbar-expand-sm bg-dark navbar-dark" >
                    <div class="container-fluid" >
                        <c:url value="/categories/${category.id}" var="filterAction"/>             
                        <a class="navbar-brand" href="${filterAction}">Lessons</a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="collapsibleNavbar">
                            <ul class="navbar-nav">
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">Skill</a>
                                    <ul class="dropdown-menu">
                                        <c:forEach items="${skills}" var="skill">
                                            <c:url value="/categories/${category.id}" var="filterAction">
                                                <c:param name="skill" value="${skill}" />
                                                <c:if test="${not empty param.kw}">
                                                    <c:param name="kw" value="${param.kw}" />
                                                </c:if>   
                                            </c:url>
                                            <li>
                                                <a class="dropdown-item" href="${filterAction}">${skill.label}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </li>
                                <c:url value="/categories/${category.id}/lessons" var="lessonAction" />
                                <li class="nav-item"><a class="nav-link" href="${lessonAction}">Add Lesson</a></li>
                            </ul>
                        </div>
                    </div>
                </nav>
                <div class="ps-2 pe-2">

                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Title</th>
                                <th>Skill</th>
                                <th>Lesson Type</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${lessons}" var="l">
                                <tr>
                                    <td>${l.id}</td>
                                    <td>${l.title}</td>
                                    <td>${l.lessonTypeId.skill.label}</td>
                                    <td>${l.lessonTypeId.name}</td>
                                    <td>
                                        <a href="<c:url value='/categories/${category.id}/lessons/${l.id}' />" class="btn btn-success mb-1">Chi tiết</a>
                                        <a href="<c:url value='/categories/${category.id}/lessons/${l.id}/delete' />" 
                                           class="btn btn-danger mb-1"
                                           onclick="return confirm('Bạn có chắc chắn muốn xóa lesson này không?');">
                                            Xóa
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="mt-2">
                        <ul class="pagination">
                            <li class="page-item">
                                <c:url value="/categories/${category.id}" var="filterAction"/> 
                                <a class="page-link" href="${filterAction}">Tất cả</a>
                            </li>
                            <c:if test="${pageQuantity > 1}">
                                <c:forEach begin="1" end="${pageQuantity}" var="i">
                                    <c:url value="/categories/${category.id}" var="pageAction">
                                        <c:param name="page" value="${i}" />
                                        <c:if test="${not empty param.kw}">
                                            <c:param name="kw" value="${param.kw}" />
                                        </c:if>
                                        <c:if test="${not empty param.skill}">
                                            <c:param name="skill" value="${param.skill}" />
                                        </c:if>
                                    </c:url>
                                    <li class="page-item ${i == param.page ? 'active' : ''}">
                                        <a href="${pageAction}" class="page-link">${i}</a>
                                    </li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </div>
</div>


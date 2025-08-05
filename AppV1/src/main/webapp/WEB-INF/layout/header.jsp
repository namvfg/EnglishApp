<%-- 
    Document   : header
    Created on : Jul 31, 2025, 9:46:09 PM
    Author     : Admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
        <c:url value="/" var="indexAction" />
        <a class="navbar-brand" href="${indexAction}">Netix Website</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${action}">Trang chủ</a>
                </li>
                <c:forEach items="${categoryTypes}" var="ct">
                    <c:url value="/" var="cateTypeAction">
                        <c:param name="cateTypeId" value="${ct.id}" />
                        <c:if test="${not empty param.kw}">
                            <c:param name="kw" value="${param.kw}" />
                        </c:if>                  
                    </c:url> 
                    <li class="nav-item">
                        <a class="nav-link" href="${cateTypeAction}">${ct.name}</a>
                    </li>
                </c:forEach>
            </ul>
            <form class="d-flex" action="${action}">
                <c:if test="${not empty param.cateTypeId}">
                    <input type="hidden" name="cateTypeId" value="${param.cateTypeId}">
                </c:if>
                <c:if test="${not empty param.skill}">
                    <input type="hidden" name="skill" value="${param.skill}">
                </c:if>
                <input class="form-control me-2" name="kw" type="text" placeholder="Nhập từ khóa ...">
                <button class="btn btn-primary" type="submit">Tìm</button>
            </form>
        </div>
    </div>
</nav>

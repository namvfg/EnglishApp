<%-- Document : header 
Created on : Jul 31, 2025, 9:46:09 PM 
Author : Admin --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- Navbar Bootstrap --%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <c:url value="/" var="indexAction" />
        <a class="navbar-brand fw-bold" href="${indexAction}">Netix Website</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarMain">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarMain">
            <!-- Left side -->
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <!-- Dropdown category type -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                        Category Type
                    </a>
                    <ul class="dropdown-menu">
                        <c:forEach items="${categoryTypes}" var="ct">
                            <c:url value="/" var="cateTypeAction">
                                <c:param name="cateTypeId" value="${ct.id}" />
                                <c:if test="${not empty param.kw}">
                                    <c:param name="kw" value="${param.kw}" />
                                </c:if>                  
                            </c:url> 
                            <li>
                                <a class="dropdown-item" href="${cateTypeAction}">${ct.name}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>

                <!-- Login/User Info -->
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal.name != null}">
                        <li class="nav-item">
                            <a class="nav-link text-info" href="<c:url value="/" />">
                                👤 ${pageContext.request.userPrincipal.name}
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/login' />">🔐 Login</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>

            <!-- Search form -->
            <form class="d-flex" role="search" action="${action}">
                <c:if test="${not empty param.cateTypeId}">
                    <input type="hidden" name="cateTypeId" value="${param.cateTypeId}">
                </c:if>
                <c:if test="${not empty param.skill}">
                    <input type="hidden" name="skill" value="${param.skill}">
                </c:if>
                <input class="form-control me-2" type="search" name="kw" placeholder="Nhập từ khóa..." aria-label="Search">
                <button class="btn btn-outline-light" type="submit">Tìm</button>
            </form>
        </div>
    </div>
</nav>
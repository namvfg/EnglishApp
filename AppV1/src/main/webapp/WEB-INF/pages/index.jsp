<%-- 
    Document   : index
    Created on : Jul 25, 2025, 9:03:47 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:url value="/" var="action" />
<section class="container">
    <div class="mt-2">
        <c:url value="/categories" var="categoriesAction" />
        <a href="${categoriesAction}" class="btn btn-info">Add Category</a>
    </div>
    <div class="mt-2">
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link" href="${action}">Tất cả</a>
            </li>
            <c:if test="${pageQuantity > 1}">
                <c:forEach begin="1" end="${pageQuantity}" var="i">
                    <c:url value="/" var="pageAction">
                        <c:param name="page" value="${i}" />
                        <c:if test="${not empty param.kw}">
                            <c:param name="kw" value="${param.kw}" />
                        </c:if>
                        <c:if test="${not empty param.cateTypeId}">
                            <c:param name="cateTypeId" value="${param.cateTypeId}" />
                        </c:if>
                    </c:url>
                    <li class="page-item ${i == param.page ? 'active' : ''}">
                        <a href="${pageAction}" class="page-link">${i}</a>
                    </li>
                </c:forEach>
            </c:if>
        </ul>
    </div>

    <table class="table table-hover">
        <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Created Date</th>
                <th>Updated Date</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${categories}" var="c">
                <tr>
                    <td>${c.id}</td>
                    <td>${c.name}</td>
                    <td>${c.createdDate}</td>
                    <td>${c.updatedDate}</td>
                    <td>
                        <a href="<c:url value="/categories/${c.id}"/>" class="btn btn-success mb-1">Update</a>
                        <button class="btn btn-danger mb-1">Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</section> 

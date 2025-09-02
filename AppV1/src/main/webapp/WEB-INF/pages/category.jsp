<%-- 
    Document   : lessons
    Created on : Aug 1, 2025, 12:13:00 PM
    Author     : Admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2 class="text-center text-primary mb-4">Quản lý Category & Lesson</h2>

<c:url value="/categories" var="action"/>
<div class="row">
    <!-- Left: Form Category -->
    <div class="col-lg-3 col-md-4 mb-3">
        <div class="card">
            <div class="card-header bg-info text-white fw-bold">
                ${category.id == null ? 'Thêm Category' : 'Cập nhật Category'}
            </div>
            <div class="card-body">
                <form:form method="post" action="${action}" modelAttribute="category">
                    <form:errors path="*" element="div" cssClass="alert alert-danger" />
                    <form:hidden path="id"/>

                    <div class="form-floating mb-3">
                        <form:input path="name" type="text" class="form-control" id="name" placeholder="Tên category"/>
                        <label for="name">Tên Category</label>
                        <form:errors path="name" element="div" cssClass="text-danger" />
                    </div>

                    <div class="form-floating mb-3">
                        <form:select path="categoryTypeId" class="form-select" id="categoryType">
                            <form:options items="${categoryTypes}" itemValue="id" itemLabel="name" />
                        </form:select>
                        <label for="categoryType">Loại danh mục</label>
                    </div>

                    <div class="d-grid">
                        <button class="btn btn-info" type="submit">
                            ${category.id == null ? 'Thêm' : 'Cập nhật'}
                        </button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>

    <!-- Right: Lesson list -->
    <div class="col-lg-9 col-md-8">
        <c:if test="${category.id != null}">
            <!-- Navbar filter -->
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark rounded mb-3">
                <div class="container-fluid">
                    <c:url value="/categories/${category.id}" var="filterAction"/>             
                    <a class="navbar-brand" href="${filterAction}">Lessons</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#lessonNavbar">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="lessonNavbar">
                        <ul class="navbar-nav me-auto">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                                    Bộ lọc kỹ năng
                                </a>
                                <ul class="dropdown-menu">
                                    <c:forEach items="${skills}" var="skill">
                                        <c:url value="/categories/${category.id}" var="filterAction">
                                            <c:param name="skill" value="${skill}" />
                                            <c:if test="${not empty param.kw}">
                                                <c:param name="kw" value="${param.kw}" />
                                            </c:if>   
                                        </c:url>
                                        <li><a class="dropdown-item" href="${filterAction}">${skill.label}</a></li>
                                    </c:forEach>
                                </ul>
                            </li>
                            <c:url value="/categories/${category.id}/lessons" var="lessonAction" />
                            <li class="nav-item">
                                <a class="nav-link" href="${lessonAction}"> Thêm Lesson</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <!-- Table -->
            <div class="table-responsive border rounded p-2">
                <table class="table table-hover table-striped align-middle mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Tiêu đề</th>
                            <th>Kỹ năng</th>
                            <th>Loại bài</th>
                            <th class="text-center">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${lessons}" var="l">
                        <tr>
                            <td>${l.id}</td>
                            <td>${l.title}</td>
                            <td>${l.lessonTypeId.skill.label}</td>
                            <td>${l.lessonTypeId.name}</td>
                            <td class="text-center">
                                <a href="<c:url value='/categories/${category.id}/lessons/${l.id}' />" class="btn btn-sm btn-success me-1">Chi tiết</a>
                                <a href="<c:url value='/categories/${category.id}/lessons/${l.id}/delete' />"
                                   class="btn btn-sm btn-danger"
                                   onclick="return confirm('Bạn có chắc chắn muốn xóa lesson này không?');">
                                    Xóa
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Pagination -->
            <c:if test="${pageQuantity > 1}">
                <nav class="mt-3">
                    <ul class="pagination justify-content-center">
                        <c:url value="/categories/${category.id}" var="filterAction"/>
                        <li class="page-item ${empty param.page ? 'active' : ''}">
                            <a class="page-link" href="${filterAction}">Tất cả</a>
                        </li>
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
                                <a class="page-link" href="${pageAction}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>
        </c:if>
    </div>
</div>


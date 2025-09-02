<%-- Document : index 
Created on : Jul 25, 2025, 9:03:47 PM 
Author : Admin --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<c:url value="/" var="action" />

<section class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2 class="text-primary mb-0">Danh sách Category</h2>
        <c:url value="/categories" var="categoriesAction" />
        <a href="${categoriesAction}" class="btn btn-info">Thêm Category</a>
    </div>

    <!-- Pagination -->
    <c:if test="${pageQuantity > 1}">
        <nav aria-label="Phân trang danh mục">
            <ul class="pagination justify-content-center">
                <li class="page-item ${empty param.page ? 'active' : ''}">
                    <a class="page-link" href="${action}">Tất cả</a>
                </li>
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
                        <a class="page-link" href="${pageAction}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </c:if>

    <!-- Category Table -->
    <div class="table-responsive">
        <table class="table table-striped table-hover align-middle">
            <thead class="table-light">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Tên</th>
                    <th scope="col">Ngày tạo</th>
                    <th scope="col">Ngày cập nhật</th>
                    <th scope="col" class="text-center">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${categories}" var="c">
                    <tr>
                        <td>${c.id}</td>
                        <td>${c.name}</td>
                        <td>${c.createdDate}</td>
                        <td>${c.updatedDate}</td>
                        <td class="text-center">
                            <a href="<c:url value='/categories/${c.id}' />" class="btn btn-sm btn-success me-1">
                                 Sửa
                            </a>
                            <a href="<c:url value='/categories/${c.id}/delete' />" 
                               class="btn btn-sm btn-danger"
                               onclick="return confirm('Bạn có chắc chắn muốn xóa category này không?');">
                                ️ Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</section>
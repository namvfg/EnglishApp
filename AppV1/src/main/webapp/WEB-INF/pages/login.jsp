<%-- 
    Document   : login
    Created on : Aug 15, 2025, 8:19:02 PM
    Author     : Admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="card shadow w-100" style="max-width: 400px;">
        <div class="card-body">
            <h3 class="card-title text-center mb-4">Đăng Nhập</h3>

            <c:url value="/login" var="action" />
            <form method="post" action="${action}">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="email" name="email" placeholder="Email" required>
                    <label for="email">Email</label>
                </div>

                <div class="form-floating mb-4">
                    <input type="password" class="form-control" id="pwd" name="password" placeholder="Mật khẩu" required>
                    <label for="pwd">Mật khẩu</label>
                </div>

                <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
            </form>

            <c:if test="${param.error != null}">
                <div class="alert alert-danger mt-4 text-center" role="alert">
                    Tên đăng nhập hoặc mật khẩu không đúng!
                </div>
            </c:if>
        </div>
    </div>
</div>

<!-- Bootstrap Bundle JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


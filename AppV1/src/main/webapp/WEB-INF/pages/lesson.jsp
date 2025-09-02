<%-- 
    Document   : lessons
    Created on : Aug 4, 2025, 9:52:53 PM
    Author     : Admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h2 class="text-center text-primary mb-4">Thông tin Bài học</h2>

<c:url value="/categories/${category.id}/lessons" var="action"/>
<form:form method="post" action="${action}" modelAttribute="lesson" enctype="multipart/form-data">
    <div class="row">
        <!-- Left: Lesson Info Form -->
        <div class="col-lg-3 col-md-4 mb-3">
            <form:errors path="*" element="div" cssClass="alert alert-danger" />
            <form:hidden path="id"/>

            <div class="form-floating mb-3">
                <form:input path="title" type="text" class="form-control" id="title" placeholder="Tên bài học"/>
                <label for="title">Tiêu đề bài học</label>
            </div>

            <div class="form-floating mb-3">
                <select class="form-select" id="skill" name="skill" <c:if test="${lesson.id != null}">disabled</c:if>>
                    <c:forEach items="${skills}" var="s">
                        <option value="${s}" ${s == selectedSkill ? 'selected' : ''}>${s}</option>
                    </c:forEach>
                </select>
                <label for="skill">Kỹ năng</label>
                <c:if test="${lesson.id != null}">
                    <input type="hidden" name="skill" value="${selectedSkill}" />
                </c:if>
            </div>

            <div class="form-floating mb-3">
                <form:select path="lessonTypeId" class="form-select" id="lessonType">
                    <!-- Options sẽ được load bằng JS -->
                </form:select>
                <label for="lessonType">Loại bài học</label>
            </div>

            <div class="form-floating mb-3">
                <form:input path="file" type="file" class="form-control" id="file" placeholder="Ảnh minh họa"/>
                <label for="file">Ảnh bài học</label>
                <c:if test="${lesson.id != null}">
                    <div class="mt-2">
                        <img src="${lesson.image}" alt="imageLesson${lesson.id}" class="img-fluid rounded border" />
                    </div>
                </c:if>
            </div>

            <div class="d-grid mt-3">
                <button class="btn btn-info" type="submit">
                    <c:choose>
                        <c:when test="${lesson.id == null}">Thêm</c:when>
                        <c:otherwise>Cập nhật</c:otherwise>
                    </c:choose>
                </button>
            </div>
        </div>

        <!-- Right: Audio + Text Content -->
        <div class="col-lg-9 col-md-8 border border-3 rounded p-3">
            <!-- Audio Upload -->
            <div class="form-floating mb-3" id="audio-upload">
                <input class="form-control" type="file" id="audio-file" name="audio-file" accept="audio/*">
                <label for="audio-file">Tệp âm thanh (nếu có)</label>
            </div>

            <!-- Audio Player Preview -->
            <c:if test="${lesson.lessonTypeId.skill == 'listening' && not empty lesson.content}">
                <div class="mb-3">
                    <audio id="player" controls preload="none" class="w-100">
                        <source src="${lesson.content}" type="audio/mpeg"/>
                    </audio>
                </div>
            </c:if>

            <!-- Text content (writing, reading, etc.) -->
            <div class="form-floating mb-3 h-100" id="text-upload">
                <form:textarea path="content" class="form-control h-100" id="content"></form:textarea>
                </div>
            </div>
        </div>

        <!-- Section List -->
        <hr class="my-4" />
        <h4 class="text-primary">Danh sách Section</h4>

    <c:if test="${not empty sections}">
        <div class="table-responsive">
            <table class="table table-bordered table-hover align-middle">
                <thead class="table-light">
                    <tr>
                        <th>#</th>
                        <th>Loại Section</th>
                        <th>Nội dung</th>
                        <th class="text-center">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="section" items="${sections}">
                        <tr>
                            <td>${section.id}</td>
                            <td>${section.sectionTypeId.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${fn:length(section.content) > 100}">
                                        ${fn:substring(section.content, 0, 100)}...
                                    </c:when>
                                    <c:otherwise>
                                        ${section.content}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div class="d-flex gap-2">
                                    <a href="<c:url value='/categories/${category.id}/lessons/${lesson.id}/sections/${section.id}' />"
                                       class="btn btn-sm btn-warning">
                                        <i class="bi bi-pencil-square"></i> Sửa
                                    </a>
                                    <a href="<c:url value='/categories/${category.id}/lessons/${lesson.id}/sections/${section.id}/delete' />"
                                       class="btn btn-sm btn-danger"
                                       onclick="return confirm('Bạn có chắc muốn xóa section này không?');">
                                        <i class="bi bi-trash"></i> Xóa
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <c:if test="${empty sections}">
        <div class="alert alert-info mt-3">
            📭 Chưa có section nào cho bài học này.
        </div>
    </c:if>
    <!-- Nút Thêm Section -->
    <div class="mt-3 mb-3">
        <c:url var="addSectionUrl" value="/categories/${category.id}/lessons/${lesson.id}/sections" />
        <a class="btn btn-primary" href="${addSectionUrl}">
            Thêm Section mới
        </a>
    </div>
</form:form>

<script src="${pageContext.request.contextPath}/static/js/lesson.js"></script>

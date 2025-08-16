<%-- 
    Document   : lessons
    Created on : Aug 4, 2025, 9:52:53 PM
    Author     : Admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1 class="text-center text-success">
    Lesson Info
</h1>
<div>
    <c:url value="/categories/${category.id}/lessons" var="action"/>
    <form:form method="post" action="${action}" modelAttribute="lesson" enctype="multipart/form-data">
        <div class="row">
            <div class="col-3" style="min-height: 70vh">
                <form:errors path="*" element="div" cssClass="alert alert-danger" />
                <form:hidden path="id"/>

                <div class="form-floating mb-3 mt-3">
                    <form:input path="title" type="text" class="form-control" id="title" placeholder="Type title"/>
                    <label for="title">Title</label>
                </div>

                <div class="form-floating mb-3">
                    <select class="form-select" id="skill" name="skill" <c:if test="${lesson.id != null}">disabled="true"</c:if> >
                        <c:forEach items="${skills}" var="s">
                            <option class="border border-1" value="${s}" ${s == selectedSkill ? 'selected' : ''}>${s}</option>
                        </c:forEach>
                    </select>
                    <label for="skill">Select skill:</label>
                    <c:if test="${lesson.id != null}">
                        <input type="hidden" name="skill" value="${selectedSkill}" />
                    </c:if>
                </div>

                <div class="form-floating mb-3">
                    <form:select path="lessonTypeId" class="form-select" id="lessonType">
                        <!-- Options sẽ được load bằng JS -->
                    </form:select>
                    <label for="lessonType">Select type:</label>
                </div>

                <div class="form-floating mb-3">
                    <form:input path="file" type="file" class="form-control" id="file" placeholder="File"/>
                    <label for="file">Image</label>
                    <c:if test="${lesson.id != null}">
                        <div style="width: 100%" class="mt-3">
                            <img style="object-fit: cover; width: 100%; height: 100%" src="${lesson.image}" alt="imageLesson${lesson.id}}"/>
                        </div>

                    </c:if>
                </div>

                <div class="form-floating mt-2 mb-2">
                    <button class="btn btn-info">
                        <c:choose>
                            <c:when test="${lesson.id == null}">Add</c:when>
                            <c:otherwise>Update Information</c:otherwise>
                        </c:choose>
                    </button>
                </div>

            </div>
            <div class="col-9 border border-3 rounded mt-3 p-0" style="height: 70vh">

                <div class="form-floating mb-3" id="audio-upload">
                    <input class="form-control" type="file" id="audio-file" name="audio-file" accept="audio/*">
                    <label for="audio-file">Audio File</label>
                </div>
                <c:if test="${lesson.lessonTypeId.skill == 'listening' && not empty lesson.content}">
                    <audio id="player" controls preload="none" style="width:100%">                    
                        <source src="${lesson.content}" type="audio/mpeg"/>
                    </audio>
                </c:if>
                <div class="form-floating mb-3 h-70" id="text-upload">
                    <form:textarea path="content" class="form-control h-70" id="content" ></form:textarea>
                        <label for="content" id="label-content"></label>
                    </div>
                </div>
            </div>    
    </form:form>
</div>
<c:choose>
    <c:when test="${lesson.id != null}">
        <div class="row mt-3 mb-3">
            <!-- add -->
            <div class="col-4">
                <button type="button" class="card w-100 h-100 border-2 border-dashed text-center p-5 bg-light"
                        data-bs-toggle="modal"
                        data-bs-target="#sectionModal"
                        data-mode="create">
                    <div class="card-body d-flex flex-column align-items-center justify-content-center">
                        <div style="font-size:48px; line-height:1;">+</div>
                        <div class="mt-2 fw-semibold">Thêm section</div>
                    </div>
                </button>
            </div>

            <!-- Các section hiện có -->
            <c:forEach var="section" items="${sections}">
                <div class="col-4 mt-3">
                    <div class="card h-100 section-card"
                         role="button"
                         data-bs-toggle="modal"
                         data-bs-target="#sectionModal"
                         data-mode="edit"
                         data-id="${section.id}"
                         data-section-type-id="${section.sectionTypeId.id}"
                         data-lessonType="${section.sectionTypeId.name}"                                    
                         data-content="${section.content}">

                        <div class="card-body">
                            <div class="d-flex flex-column justify-content-between">
                                <h6 class="mb-1">${section.sectionTypeId.name}</h6>
                                <p>${section.content}</p>
                            </div>
                        </div>
                        <a class="btn btn-info" href="<c:url value="/categories/${category.id}/lessons/${lesson.id}/sections/${section.id}" />">Chi tiết</a>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="modal fade" id="sectionModal" tabindex="-1" data-bs-focus="false">
            <div class="modal-dialog modal-lg modal-dialog-scrollable">
                <div class="modal-content">
                    <form id="sectionForm" ><!-- gui trong lesson.js -->
                        <div class="modal-header">
                            <h5 class="modal-title" id="sectionModalTitle">Thêm section</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                        </div>

                        <div class="modal-body">
                            <!-- Hidden phục vụ create/update -->
                            <input type="hidden" name="lessonId" id="lessonId" value="${lesson.id}"/>
                            <input type="hidden" name="id" id="secId"/>

                            <div class="mb-3">
                                <label for="sectionType" class="form-label">Chọn Section Type</label>
                                <select id="sectionTypeId" name="sectionTypeId" class="form-select">
                                    <option value="" selected disabled hidden>-- Chọn --</option>
                                    <c:forEach var="sectionType" items="${sectionTypes}">
                                        <option value="${sectionType.id}">${sectionType.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div>
                                <div id="contentWrap">
                                    <label class="form-label">Nội dung</label>
                                    <textarea class="form-control" rows="6" name="content" id="secContent"></textarea>
                                </div>
                            </div>


                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-primary" id="secSubmitBtn">Lưu</button>                          
                        </div>
                    </form>
                </div>
            </div>
        </div>              
    </c:when>
</c:choose>
<script src="${pageContext.request.contextPath}/static/js/lesson.js"></script>

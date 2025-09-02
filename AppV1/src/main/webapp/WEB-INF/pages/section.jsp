<%-- 
    Document   : sections
    Created on : Aug 13, 2025, 7:55:35 PM
    Author     : Admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container mt-4">
    <h3><c:choose><c:when test="${section.id != null}">Chỉnh sửa Section #${section.id}</c:when><c:otherwise>Thêm Section mới</c:otherwise></c:choose></h3>

    <form:form modelAttribute="section" method="post" action="${pageContext.request.contextPath}/categories/${categoryId}/lessons/${lessonId}/sections">
        <div class="form-floating mb-3">
            <form:select path="sectionTypeId" cssClass="form-select" id="sectionTypeId">
                <c:forEach var="st" items="${sectionTypes}">
                    <option value="${st.id}">${st.name} - ${st.saveType}</option>
                </c:forEach>
            </form:select>        

            <label for="sectionTypeId">Chọn loại bài tập</label>
        </div>
        <form:hidden path="id" />
        <div class="card p-3 mb-3">
            <label class="form-label">Nội dung (content) - hỗ trợ định dạng</label>
            <form:textarea path="content" id="content" cssClass="form-control" rows="6" />

            <label class="form-label mt-3">Câu hỏi (question - JSON)</label>
            <form:textarea path="question" cssClass="form-control" rows="4" />

            <label class="form-label mt-3">Đáp án (answer - JSON)</label>
            <form:textarea path="answer" cssClass="form-control" rows="5" />

            <label class="form-label mt-3">Đáp án đúng (correctAnswer - JSON)</label>
            <form:textarea path="correctAnswer" cssClass="form-control" rows="5" />
        </div>

        <button type="submit" class="btn btn-success">Lưu Section</button>
        <a href="<c:url value='/categories/${categoryId}/lessons/${lessonId}'/>" class="btn btn-secondary ms-2">Quay lại</a>
    </form:form>

    <div class="alert alert-secondary mb-4 mt-5">
        <h5 class="text-primary">📌 Hướng dẫn định dạng JSON cho từng loại bài tập</h5>
        <details>
            <summary class="mb-2"><strong>1. MULTIPLE_CHOICE</strong></summary>
            <pre>
                question: {
                    "1": "Q1",
                    "2": "Q2"
                },
                answer: {
                    "1": {
                        "1": "A",
                        "2": "B"
                    },
                    "2": {
                        "1": "C",
                        "2": "D"
                    }
                },
                correct_answer: {
                    "1": ["2"],
                    "2": ["1"]
                }
            </pre>
        </details>
        <details>
            <summary class="mb-2"><strong>2. MATCHING</strong></summary>
            <pre>
                question: {
                    "1": "P1",
                    "2": "P2"
                },
                answer: {
                    "1": "Heading 1",
                    "2": "Heading 2",
                    "3": "Heading 3"
                },
                correct_answer: {
                    "1": "3",
                    "2": "1"
                }
            </pre>
        </details>
        <details>
            <summary class="mb-2"><strong>3. COMPLETION</strong></summary>
            <pre>
                question: {
                    "1": "The sun rises in the _______",
                    "2": "Water boils at _______ degrees Celsius."
                },
                answer: {
                    "1": ["east"],
                    "2": ["100"]
                },
                correct_answer: {
                    "1": ["east"],
                    "2": ["100"]
                }
            </pre>
        </details>
        <details>
            <summary class="mb-2"><strong>4. TFNG_YNG</strong></summary>
            <pre>
                question: {
                    "1": "The Earth is flat.",
                    "2": "Water is wet."
                },
                answer: {
                    "1": "TRUE",
                    "2": "FALSE",
                    "3": "NOTGIVEN"
                },
                correct_answer: {
                    "1": "2",
                    "2": "1"
                }
            </pre>
        </details>
        <details>
            <summary class="mb-2"><strong>5. SHORT_ANSWER</strong></summary>
            <pre>
                question: {
                    "1": "What is the main ingredient in bread?",
                    "2": "What process makes dough rise?"
                },
                answer: {
                    "1": ["flour", "wheat flour"],
                    "2": ["fermentation"]
                },
                correct_answer: {
                    "1": ["flour", "wheat flour"],
                    "2": ["fermentation"]
                }
            </pre>
        </details>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/js/section.js"></script>
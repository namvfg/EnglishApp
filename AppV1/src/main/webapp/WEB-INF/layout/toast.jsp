<%-- 
    Document   : toast
    Created on : Aug 9, 2025, 3:28:36 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Toast container -->
<c:if test="${not empty toastMessage}">
    <div class="toast-container position-fixed top-10 end-0 p-3" style="z-index: 1100;">
        <div id="liveToast"
             class="toast align-items-center text-white 
             ${toastType eq 'success' ? 'bg-success' : 'bg-danger'} 
             border-0"
             role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">
                    ${toastMessage}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto"
                        data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/static/js/toast.js"></script> 
</c:if>


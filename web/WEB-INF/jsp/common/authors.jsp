<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="data">
    <div class="authors-header">
        <fmt:message bundle="${requestScope.language}" key="authors.title"/>
    </div>
    <c:forEach items="${requestScope.authors}" var="author">
        <div class="author">
            <div class="author-title">
                <a href="main?command=author&author=${author.id}"><c:out value="${author.lastname}"/> <c:out value="${author.firstname}"/> <br></a>
            </div>
            <div class="author-offset"></div>
        </div>
    </c:forEach>
    <c:import url="/WEB-INF/jsp/common/pages.jsp" charEncoding="utf-8"/>
</div>
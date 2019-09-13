<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${requestScope.pageCount gt 1}">
    <div class="page-list">
        <c:forEach var="index" begin="1" end="${requestScope.pageCount}">
            <c:if test="${not (index eq requestScope.pageNumber)}">
                <a href="main?command=${requestScope.pageCommand}&page=${index}"><c:out value="${index}"/></a>
            </c:if>
            <c:if test="${index eq requestScope.pageNumber}">
                <c:out value="${index}"/>
            </c:if>
        </c:forEach>
    </div>
</c:if>

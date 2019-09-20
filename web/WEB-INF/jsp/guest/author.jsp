<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="data" align="center">
    <div class="author-img2">
        <img class="author-img" src="img/author.png"/>
    </div>
    <div class="author-data">
        <div class="author-header1">
            <fmt:message bundle="${requestScope.language}" key="author.author"/>
        </div>
        <div class="author-header">
            <c:out value="${author.firstname}"/> <c:out value="${author.lastname}"/>
        </div>

        <c:if test="${not empty requestScope.author.born}">
            <div class="author-header3">
                <fmt:message bundle="${requestScope.language}" key="author.life"/>
            </div>
            <div class="author-author2">
                <fmt:formatDate value="${requestScope.author.born}" type="date" dateStyle="long"/>
                <c:if test="${not empty requestScope.author.dead}">
                    <c:out value=" - "/>
                    <fmt:formatDate value="${requestScope.author.dead}" type="date" dateStyle="long"/>
                </c:if>
            </div>
        </c:if>
        <div class="author-header2">
            <fmt:message bundle="${requestScope.language}" key="author.books"/>
        </div>
        <c:forEach items="${requestScope.author.books}" var="book">
            <div class="author-author2">
                <a href="main?command=book&book=${book.id}"><c:out value="${book.title}"/></a>
            </div>
        </c:forEach>
    </div>
</div>
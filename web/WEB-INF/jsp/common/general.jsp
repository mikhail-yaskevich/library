<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="data">
    <div class="books-header">
        <fmt:message bundle="${requestScope.language}" key="general.title"/>
    </div>
    <c:forEach items="${requestScope.books}" var="book">
        <div class="book">
            <div class="book-title">
                <a href="main?command=book&book=${book.id}"><c:out value="${book.title}"/><br></a>
            </div>
            <div class="book-authors">
                <c:forEach items="${book.authors}" var="author">
                    <div class="book-author">
                        <a href="main?command=author&author=${author.id}"><c:out value="${author.firstname}"/> <c:out value="${author.lastname}"/></a>
                    </div>
                </c:forEach>
            </div>
            <div class="book-offset"></div>
        </div>
    </c:forEach>
</div>
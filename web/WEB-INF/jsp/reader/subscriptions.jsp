<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="data">
    <div class="subscriptions-header">
        <fmt:message bundle="${requestScope.language}" key="subscriptions.title"/>
    </div>

    <c:forEach items="${requestScope.subscriptions}" var="subscription">
        <div class="subscription">
            <div class="subscription-title">
                <a href="main?book=${subscription.book.id}"><c:out value="${subscription.book.title}"/><br></a>
            </div>
            <c:out value="${subscription.stopping}"/>
<%--            <!div class="book-authors">--%>
<%--                <c:forEach items="${book.authors}" var="author">--%>
<%--                    <div class="book-author">--%>
<%--                        <a href="main?author=${author.id}"><c:out value="${author.firstname}"/> <c:out--%>
<%--                                value="${author.lastname}"/></a>--%>
<%--                    </div>--%>
<%--                </c:forEach>--%>
<%--            </div>--%>

            <div class="book-offset"></div>
        </div>
    </c:forEach>

    <c:import url="/WEB-INF/jsp/common/pages.jsp" charEncoding="utf-8"/>
</div>
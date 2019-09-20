<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="data" align="center">
    <div class="book-img2">
        <img class="book-img" src="img/book.png"/>
        <c:if test="${requestScope.userAuthorization and sessionScope.userType eq \"READER\"}">
            <%--            <div class="book-command">--%>
            <c:if test="${not empty requestScope.subscription}">
                <c:if test="${empty requestScope.subscription.starting and not empty requestScope.subscription.reserved}">
                    <div class="subscription-command">
                        <form action="main" method="post">
                            <input type="hidden" name="command" value="cancel"/>
                            <input type="hidden" name="subscription" value="${requestScope.subscription.id}"/>
                            <input type="hidden" name="book" value="${requestScope.book.id}"/>
                            <input class="link-lookalike" type="submit"
                                   value="<fmt:message bundle="${requestScope.language}" key="subscriptions.cancel"/>"/>
                        </form>
                    </div>
                    <c:if test="${empty requestScope.subscription.starting}">
                        <div class="subscription-command">
                            <form action="main" method="post">
                                <input type="hidden" name="command" value="take"/>
                                <input type="hidden" name="subscription" value="${requestScope.subscription.id}"/>
                                <input type="hidden" name="book" value="${requestScope.book.id}"/>
                                <input class="link-lookalike" type="submit"
                                       value="<fmt:message bundle="${requestScope.language}" key="book.take"/>"/>
                            </form>
                        </div>
                    </c:if>
                </c:if>
                <c:if test="${not empty requestScope.subscription.starting}">
                    <c:if test="${empty requestScope.subscription.stopping}">
                        <div class="subscription-command">
                            <form action="main" method="post">
                                <input type="hidden" name="command" value="bring"/>
                                <input type="hidden" name="subscription" value="${requestScope.subscription.id}"/>
                                <input type="hidden" name="book" value="${requestScope.book.id}"/>
                                <input class="link-lookalike" type="submit"
                                       value="<fmt:message bundle="${requestScope.language}" key="book.bring"/>"/>
                            </form>
                        </div>
                    </c:if>
                </c:if>
            </c:if>
            <c:if test="${empty requestScope.subscription}">
                <div class="subscription-command">
                    <form action="main" method="post">
                        <input type="hidden" name="command" value="reserve"/>
                        <input type="hidden" name="book" value="${requestScope.book.id}"/>
                        <input class="link-lookalike" type="submit"
                               value="<fmt:message bundle="${requestScope.language}" key="book.reserve"/>"/>
                    </form>
                </div>
                <div class="subscription-command">
                    <form action="main" method="post">
                        <input type="hidden" name="command" value="take"/>
                        <input type="hidden" name="book" value="${requestScope.book.id}"/>
                        <input class="link-lookalike" type="submit"
                               value="<fmt:message bundle="${requestScope.language}" key="book.take"/>"/>
                    </form>
                </div>
            </c:if>
            <%--            </div>--%>
        </c:if>
    </div>
    <div class="book-data">
        <div class="book-header1">
            <fmt:message bundle="${requestScope.language}" key="book.book"/>
        </div>
        <div class="book-header">
            <c:out value="${requestScope.book.title}"/>
        </div>

        <div class="book-header3">
            <fmt:message bundle="${requestScope.language}" key="book.description"/>
        </div>
        <div class="book-author2">
            Bla-bla-bla
        </div>

        <div class="book-header2">
            <fmt:message bundle="${requestScope.language}" key="book.authors"/>
        </div>
        <c:forEach items="${requestScope.book.authors}" var="author">
            <div class="book-author2">
                <a href="main?command=author&author=${author.id}"><c:out value="${author.firstname}"/> <c:out
                        value="${author.lastname}"/></a>
            </div>
        </c:forEach>
    </div>
</div>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="data">
    <div class="subscriptions-header">
        <fmt:message bundle="${requestScope.language}" key="subscriptions.title"/>
    </div>
    <c:forEach items="${requestScope.subscriptions}" var="subscription">
        <div class="subscription">
            <div class="subscription-title">
                <a href="main?command=book&book=${subscription.book.id}"><c:out value="${subscription.book.title}"/><br></a>
            </div>
            <div class="subscription-states">
                <fmt:setLocale value="${requestScope.locale}"/>
                <div class="subscription-state">
                    <c:if test="${empty subscription.starting and not empty subscription.reserved}">
                        <div class="subscription-command">
                            <fmt:message bundle="${requestScope.language}" key="subscriptions.reserved"/>
                            <fmt:formatDate value="${subscription.reserved}" type="both" dateStyle="long"/>
                        </div>
                        <div class="subscription-command">
                            <form action="main" method="post">
                                <input type="hidden" name="command" value="cancel"/>
                                <input type="hidden" name="subscription" value="${subscription.id}"/>
                                <input type="hidden" name="page" value="${requestScope.pageNumber}"/>
                                <input class="link-lookalike" type="submit" value="<fmt:message bundle="${requestScope.language}" key="subscriptions.cancel"/>"/>
                            </form>
                        </div>
                        <c:if test="${empty subscription.starting}">
                            <div class="subscription-command">
                                <form action="main" method="post">
                                    <input type="hidden" name="command" value="take"/>
                                    <input type="hidden" name="subscription" value="${subscription.id}"/>
                                    <input type="hidden" name="page" value="${requestScope.pageNumber}"/>
                                    <input class="link-lookalike" type="submit" value="<fmt:message bundle="${requestScope.language}" key="book.take"/>"/>
                                </form>
                            </div>
                        </c:if>
                    </c:if>
                    <c:if test="${not empty subscription.starting}">
                        <div class="subscription-command">
                            <fmt:message bundle="${requestScope.language}" key="subscriptions.starting"/>
                            <fmt:formatDate value="${subscription.starting}" type="both" dateStyle="long"/>
                        </div>
                        <c:if test="${empty subscription.stopping}">
                            <div class="subscription-command">
                                <form action="main" method="post">
                                    <input type="hidden" name="command" value="bring"/>
                                    <input type="hidden" name="subscription" value="${subscription.id}"/>
                                    <input type="hidden" name="page" value="${requestScope.pageNumber}"/>
                                    <input class="link-lookalike" type="submit" value="<fmt:message bundle="${requestScope.language}" key="book.bring"/>"/>
                                </form>
                            </div>
                        </c:if>
                    </c:if>
                    <c:if test="${not empty subscription.stopping}">
                        <div class="subscription-command">
                            <div class="subscription-command-offset"></div>
                            <fmt:message bundle="${requestScope.language}" key="subscriptions.stopping"/>
                            <fmt:formatDate value="${subscription.stopping}" type="both" dateStyle="long"/>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="subscription-offset"></div>
        </div>
    </c:forEach>

    <c:import url="/WEB-INF/jsp/common/pages.jsp" charEncoding="utf-8"/>
</div>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--<div class="header-logo">--%>
    <a href="main"><img class="header-logo" src="img/logo.ico"/></a>
<%--</div>--%>
<div class="header-user">
    <c:if test="${empty requestScope.loginPage}"><a href="main?command=login"><fmt:message bundle="${requestScope.language}" key="main.enter"/></a></c:if>
    <c:if test="${empty requestScope.loginPage and empty requestScope.registrationPage}">/</c:if>
    <c:if test="${empty requestScope.registrationPage}"><a href="main?command=registration"><fmt:message bundle="${requestScope.language}" key="main.sign-up"/></a></c:if>
</div>
<h1>Библиотека "Давай почитаем"</h1>
<c:import url="/WEB-INF/jsp/common/locale.jsp" charEncoding="utf-8"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${requestScope.locale}"/>
<fmt:setBundle basename="language.title" var="language" scope="request"/>
<html>
<head>
    <title>Library</title>
    <meta charset="utf-8">
    <link rel="shortcut icon" href="favicon.ico"/>
    <link rel="stylesheet" href="css/main.css"/>
</head>
<body>
<div class="page">
    <c:set var="userAuthorization" value="${not empty sessionScope.user and not empty sessionScope.userType}" scope="request"/>
    <div class="context header">
        <c:choose>
            <c:when test="${userAuthorization and sessionScope.userType eq \"ADMIN\"}">
                <c:import url="/WEB-INF/jsp/admin/header.jsp" charEncoding="utf-8"/>
            </c:when>
            <c:when test="${userAuthorization and sessionScope.userType eq \"READER\"}">
                <c:import url="/WEB-INF/jsp/admin/header.jsp" charEncoding="utf-8"/>
            </c:when>
            <c:otherwise>
                <c:import url="/WEB-INF/jsp/guest/header.jsp" charEncoding="utf-8"/>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="context middle">
        <!--menu left-->
        <div class="middle-menu">
            <c:choose>
                <c:when test="${userAuthorization and sessionScope.userType eq \"ADMIN\"}">
                    <c:import url="/WEB-INF/jsp/admin/menu.jsp" charEncoding="utf-8"/>
                </c:when>
                <c:when test="${userAuthorization and sessionScope.userType eq \"READER\"}">
                    <c:import url="/WEB-INF/jsp/reader/menu.jsp" charEncoding="utf-8"/>
                </c:when>
                <c:otherwise>
                    <c:import url="/WEB-INF/jsp/guest/menu.jsp" charEncoding="utf-8"/>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="middle-context">
            <!--menu top-->
            <c:if test="${not empty requestScope.pageMenu}">
                <div class="middle-context-menu-top"><c:import url="${requestScope.pageMenu}" charEncoding="utf-8"/></div>
            </c:if>
            <!--content-->
            <c:if test="${not empty requestScope.page}">
                <div class="middle-context-data"><c:import url="${requestScope.page}" charEncoding="utf-8"/></div>
            </c:if>
        </div>
    </div>
    <div class="context footer">
        <c:import url="/WEB-INF/jsp/common/footer.jsp" charEncoding="utf-8"/>
    </div>
</div>
</body>
</html>

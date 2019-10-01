<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--div class="header-submenu">
    <a href="main?command=book_search"><fmt:message bundle="${requestScope.language}" key="book.search"/></a>
</div--%>
<div class="header-search">
    <form>
        <input type="hidden" name="command" value="search"/>
        <input type="hidden" name="searchType" value="books"/>
        <input id="search" type="text" name="searchText" value="${requestScope.searchText}"/>
        <input type="submit" value="<fmt:message bundle="${requestScope.language}" key="search.find"/>">
    </form>
</div>
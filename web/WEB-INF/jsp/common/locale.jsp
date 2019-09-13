<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="header-locale">
    <form action="main" method="post">
        <input type="hidden" name="command" value="locale"/>
        <input type="hidden" name="lastRequest" value="${requestScope.lastRequest}"/>
        <select name="locale" onchange="this.form.submit()">
            <option <c:if test="${requestScope.locale eq \"\"}">selected</c:if> value=""></option>
            <option <c:if test="${requestScope.locale eq \"en_US\"}">selected</c:if> value="en_US">EN</option>
            <option <c:if test="${requestScope.locale eq \"ru_RU\"}">selected</c:if> value="ru_RU">RU</option>
        </select>
    </form>
</div>
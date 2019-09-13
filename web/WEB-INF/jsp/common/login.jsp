<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div align="center">
    <form action="main" method="post">
        <input type="hidden" name="command" value="login"/>
        <table>
            <tr>
                <th colspan="2" class="head"><fmt:message bundle="${requestScope.language}" key="login.header"/></th>
            </tr>
            <c:if test="${not empty requestScope.loginError}">
                <tr>
                    <td colspan="2" class="error"><c:out value="${requestScope.loginError}"/></td>
                </tr>
            </c:if>
            <tr>
                <td align="right"><fmt:message bundle="${requestScope.language}" key="login.login"/></td>
                <td><input type="text" name="login" value=""/></td>
            </tr>
            <tr>
                <td align="right"><fmt:message bundle="${requestScope.language}" key="login.password"/></td>
                <td><input type="password" name="password" value=""/></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input class="link-lookalike" type="submit" value="<fmt:message bundle="${requestScope.language}" key="login.enter"/>"/></td>
            </tr>
        </table>
    </form>
</div>
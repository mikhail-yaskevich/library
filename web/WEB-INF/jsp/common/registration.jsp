<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div align="center">
    <form action="main" method="post">
        <input type="hidden" name="command" value="registration"/>
        <table>
            <tr>
                <th colspan="2" class="head"><fmt:message bundle="${requestScope.language}" key="registration.header"/></th>
            </tr>
            <c:if test="${not empty requestScope.registrationError}">
                <tr>
                    <td colspan="2" class="error center"><c:out value="${requestScope.registrationError}"/></td>
                </tr>
            </c:if>
            <tr>
                <td class="right"><fmt:message bundle="${requestScope.language}" key="registration.login"/><b class="required">*</b>:</td>
                <td>
                    <input type="text" name="login" value="${requestScope.oldLogin}"/>
                </td>
            </tr>
            <tr>
                <td class="right"><fmt:message bundle="${requestScope.language}" key="registration.password"/><b class="required">*</b>:</td>
                <td><input type="password" name="password" value=""/></td>
            </tr>
            <tr>
                <td class="right"><fmt:message bundle="${requestScope.language}" key="registration.confirmation"/><b class="required">*</b>:</td>
                <td><input type="password" name="passwordyet" value=""/></td>
            </tr>
            <tr>
                <td class="right"><fmt:message bundle="${requestScope.language}" key="registration.email"/><b class="required">*</b>:</td>
                <td><input type="text" name="email" value="${requestScope.oldEmail}"/></td>
            </tr>

            <tr>
                <td class="right"><fmt:message bundle="${requestScope.language}" key="registration.name"/><b class="required">*</b>:</td>
                <td><input type="text" name="firstname" value="${requestScope.oldFirstname}"/></td>
            </tr>
            <tr>
                <td class="right"><fmt:message bundle="${requestScope.language}" key="registration.last-name"/><b class="required">*</b>:</td>
                <td><input type="text" name="lastname" value="${requestScope.oldLastname}"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input class="link-lookalike" type="submit" value="<fmt:message bundle="${requestScope.language}" key="registration.enter"/>"/></td>
            </tr>
        </table>
    </form>
</div>
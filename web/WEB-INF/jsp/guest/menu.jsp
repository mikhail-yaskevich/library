<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<a href="main"><fmt:message bundle="${requestScope.language}" key="main.general"/></a><br>
<a href="main?command=books"><fmt:message bundle="${requestScope.language}" key="main.books"/></a><br>
<a href="main?command=authors"><fmt:message bundle="${requestScope.language}" key="main.authors"/></a><br>
<a href="main?command=about"><fmt:message bundle="${requestScope.language}" key="main.about"/></a><br>
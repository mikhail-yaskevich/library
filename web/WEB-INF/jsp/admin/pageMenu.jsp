<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<a href="main?command=book_add"><fmt:message bundle="${requestScope.language}" key="book.add"/></a>
<a href="main?command=book_search"><fmt:message bundle="${requestScope.language}" key="book.search"/></a>
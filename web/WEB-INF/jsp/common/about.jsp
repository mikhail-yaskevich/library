<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div align="left" class="data">
    <p>Страница ни о чем, для разнообразия.</p>

    <form action="main" method="post" enctype="multipart/form-data">
        <input type="hidden"  name="command" value="upload"/><br>
        <input type="text" placeholder="description*" name="description"/><br>
        <input type="file"  name="file"/><br>
        <input type="submit" value="OK"/><br>
    </form>

    <c:out value="x${pageScope.upload}"/>
    <c:out value="z${param.upload}"/>
    <c:out value="c${paramValues.upload}"/>

</div>
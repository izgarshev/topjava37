<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://localhost:8080/topjava/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href=${pageContext.request.contextPath}/meals?action=create><h3>Добавить еду</h3></a>
<table>
    <thead>
    <tr>
        <th>id</th>
        <th>Описание</th>
        <th>Дата</th>
        <th>Калории</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="meals" scope="request" type="java.util.ArrayList"/>
    <c:forEach items="${meals}" var="meal">
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td>${meal.id}</td>
            <td>${meal.description}</td>
            <td>${f:formatLocalDateTime(meal.dateTime)}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Обновить</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>

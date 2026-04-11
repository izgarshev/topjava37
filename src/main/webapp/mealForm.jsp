<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Форма еды</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<h3>${meal.id == null ? 'Добавление' : 'Редактирование'} еды</h3>
<form class="meal-form" method="post">

    <label for="id" hidden>id</label>
    <input type="text" id="id" name="id" value="${meal.id}" hidden>

    <label for="description">description</label>
    <input type="text" id="description" name="description" value="${meal.description}">

    <label for="dateTime">dateTime</label>
    <input type="datetime-local" id="dateTime" name="dateTime" value="${meal.dateTime}">

    <label for="calories">calories</label>
    <input type="number" id="calories" name="calories" min="1" value="${meal.calories}">

    <div class="buttons">
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отмена</button>
    </div>
</form>
</body>
</html>

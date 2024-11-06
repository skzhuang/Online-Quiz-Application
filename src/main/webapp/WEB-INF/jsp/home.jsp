<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
    <title>Home - Quiz Application</title>
</head>

<jsp:include page="navbar.jsp" />

<body>

<div class="container">

    <h2>Quiz Categories</h2>
    <form action="${pageContext.request.contextPath}/quiz/start" method="post">
        <label for="categorySelect">Choose a quiz category:</label>
        <select id="categorySelect" name="categoryId">
            <c:forEach var="category" items="${quizCategories}">
                <option value="${category.id}">${category.name}</option>
            </c:forEach>
        </select>
        <c:if test="${not empty sessionScope.currentQuiz}">
            <button type="submit">Continue</button>
        </c:if>
        <c:if test="${empty sessionScope.currentQuiz}">
            <button type="submit">Start Quiz</button>
        </c:if>
    </form>

    <h2>Recent Quiz Results</h2>
    <table class="results-table">
        <thead>
        <tr>
            <th>Quiz Name</th>
            <th>Time Start</th>
            <th>Time End</th>
            <th>Results</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="quiz" items="${quizs}">
            <tr>
                <td>${quiz.name}</td>
                <td>${quiz.timeStart}</td>
                <td>${quiz.timeEnd}</td>
                <td><a href="${pageContext.request.contextPath}/quiz/result?quizId=${quiz.quizId}">View Result</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>

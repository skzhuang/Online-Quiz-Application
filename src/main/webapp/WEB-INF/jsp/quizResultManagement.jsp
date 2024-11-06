<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quiz Result Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>

<jsp:include page="navbar.jsp" />

<body>
<div class="container">
<h1>Quiz Result Management</h1>

<form method="get" action="${pageContext.request.contextPath}/admin/quizResultManagement">
    <div>
        <label for="category">Filter by Category:</label>
        <input type="text" id="category" name="category" value="${category}">
    </div>
    <div>
        <label for="userFullName">Filter by User:</label>
        <input type="text" id="userFullName" name="userFullName" value="${userFullName}">
    </div>
    <button type="submit">Filter</button>
</form>

<table>
    <thead>
    <tr>
        <th><a href="?sort=taken_time">Taken Time</a></th>
        <th><a href="?sort=categoryName">Category</a></th>
        <th><a href="?sort=userFullName">User Full Name</a></th>
        <th>Number of Questions</th>
        <th>Score</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="result" items="${quizResults}">
        <tr>
            <td>${result.takenTime}</td>
            <td>${result.categoryName}</td>
            <td>${result.userFullName}</td>
            <td>${result.questionsNumber}</td>
            <td><a href="${pageContext.request.contextPath}/quiz/result?quizId=${result.quizId}">View</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="pagination">
    <c:forEach begin="1" end="${totalPages}" var="i">
        <a href="?page=${i}&category=${category}&user=${userFullName}&sort=${sort}"
           class="${i == currentPage ? 'active' : ''}">${i}</a>
    </c:forEach>
</div>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/navigateBar.css">
    <title>Global Navigation Bar</title>
</head>
<body>

<nav class="navbar">
    <ul class="nav-list">
        <li class="nav-item">
            <c:if test="${not empty user}">
                <a class="nav-link" href="${pageContext.request.contextPath}/home">Home</a>
            </c:if>
        </li>

        <li class="nav-item">
            <c:if test="${not empty currentQuiz}">
                <a class="nav-link" href="${pageContext.request.contextPath}/question">Taking Quiz</a>
            </c:if>
        </li>

        <li class="nav-item">
            <c:if test="${not empty user}">
                <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
            </c:if>
            <c:if test="${empty user}">
                <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
            </c:if>
        </li>

        <li class="nav-item">
            <c:if test="${empty user}">
                <a class="nav-link" href="${pageContext.request.contextPath}/register">Register</a>
            </c:if>
        </li>

        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/contact">Contact Us</a>
        </li>

        <li class="nav-item">
            <c:if test="${not empty user && user.admin == true}">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/userManagement">User Management</a>
            </c:if>
        </li>

        <li class="nav-item">
            <c:if test="${not empty user && user.admin == true}">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/questionManagement">Question Management</a>
            </c:if>
        </li>

        <li class="nav-item">
            <c:if test="${not empty user && user.admin == true}">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/quizResultManagement">Quiz Result Management</a>
            </c:if>
        </li>
    </ul>
</nav>

</body>
</html>

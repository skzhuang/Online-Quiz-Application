<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>

<jsp:include page="navbar.jsp" />

<body>
<div class="container">
    <h1>Login</h1>
    <form method="post" action="/login">
        <div>
            <label>Email</label>
            <input type="email" name="email" required>
        </div>
        <div>
            <label>Password</label>
            <input type="password" name="password" required>
        </div>
        <button type="submit">Submit</button>
    </form>

    <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a></p>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">
                ${errorMessage}
        </div>
    </c:if>
</div>
</body>
</html>

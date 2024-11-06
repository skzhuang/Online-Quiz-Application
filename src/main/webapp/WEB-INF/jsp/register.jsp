<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>

<jsp:include page="navbar.jsp" />

<body>
<div class="container">
    <h1>Register</h1>

    <form action="register" method="post">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" required>

        <label for="firstname">First Name</label>
        <input type="text" id="firstname" name="firstname" required>

        <label for="lastname">Last Name</label>
        <input type="text" id="lastname" name="lastname" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>

        <label for="confirmPassword">Confirm Password</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required>

        <button type="submit">Register</button>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                    ${errorMessage}
            </div>
        </c:if>
    </form>
</div>

</body>
</html>

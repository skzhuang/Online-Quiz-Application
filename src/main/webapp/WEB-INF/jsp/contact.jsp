<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact Us</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<jsp:include page="navbar.jsp" />

<body>
<div class="container">
<h2>Contact us</h2>
<form action="/contact" method="post">
    <label for="subject">Subject:</label>
    <input type="text" id="subject" name="subject" required /><br /><br />

    <label for="email">E-mail:</label>
    <input type="email" id="email" name="email" required /><br /><br />

    <label for="message">Message:</label>
    <textarea id="message" name="message" rows="5" required></textarea><br /><br />

    <button type="submit">Submit</button>
</form>
</div>
</body>
</html>

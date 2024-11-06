<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <title>Quiz Results</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/quizResult.css">
</head>

<jsp:include page="navbar.jsp" />

<body>
    <div class="quiz-result-container">
    <h1>Quiz Results</h1>
    <h2>Quiz Name: ${quizName}</h2>
    <p>Start Time: ${timeStart}</p>
    <p>End Time: ${timeEnd}</p>
    </div>

    <div class="questions-results">
        <h3>Questions:</h3>
        <c:forEach var="result" items="${results}">
            <div class="question-result">
                <h4>Question: ${result.question.description}</h4>
                <ul>
                    <li><strong>Your Choice:</strong> ${result.userChoice.id != null ? result.userChoice.description : 'None selected'}</li>
                    <li><strong>Correct Choice:</strong> ${result.realChoices.id != null ? result.realChoices.description : 'No correct answer available'}</li>
                </ul>
            </div>
            <hr>
        </c:forEach>
    </div>
</body>
</html>

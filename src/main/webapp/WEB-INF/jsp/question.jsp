<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Question ${currentIndex + 1} of ${totalQuestions}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/question.css">
</head>

<jsp:include page="navbar.jsp" />

<body>

<p>Completed: ${count} / ${totalQuestions}</p>

<h2>Question ${currentIndex + 1}</h2>
<div class="question-detail">
    <form action="${pageContext.request.contextPath}/quiz/question/navigate" method="post">
        <p>${question.description}</p>
        <c:forEach var="choice" items="${choices}">
            <div>
                <input type="radio" name="selectedChoiceId" value="${choice.id}" id="choice_${choice.id}"
                       <c:if test="${choice.id == selectedChoiceId}">checked="checked"</c:if> />
                <label for="choice_${choice.id}">${choice.description}</label>
            </div>
        </c:forEach>

        <input type="hidden" name="action" value="" id="action"/>

        <button type="submit" name="action" value="previous" <c:if test="${currentIndex == 0}">disabled</c:if>>Previous</button>

        <button type="submit" name="action" value="next" <c:if test="${currentIndex == totalQuestions - 1}">disabled</c:if>>Next</button>

        <button type="submit" name="action" value="submit" >Submit Quiz</button>
    </form>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">
                ${errorMessage}
        </div>
    </c:if>
</div>

</body>
</html>

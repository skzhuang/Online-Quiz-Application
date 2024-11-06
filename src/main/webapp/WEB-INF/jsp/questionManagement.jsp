<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Question Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>

<jsp:include page="navbar.jsp" />

<body>
<div class="container">
    <h1>Question Management</h1>

    <table class="table">
        <thead>
        <tr>
            <th>CategoryID</th>
            <th>Description</th>
            <th>Status</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="q" items="${questions}">
            <tr>
                <td>${q.categoryId}</td>
                <td>${q.description}</td>
                <td>
                    <c:choose>
                        <c:when test="${q.active}">
                            <span class="badge bg-success">Active</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-danger">Suspended</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/editQuestion" method="get">
                        <input type="hidden" name="questionId" value="${q.id}" />
                        <button type="submit" class="btn btn-primary">Edit</button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/toggleQuestionStatus" method="post">
                        <input type="hidden" name="questionId" value="${q.id}" />
                        <button type="submit" class="btn btn-secondary">
                            <c:choose>
                                <c:when test="${q.active}">
                                    Suspend
                                </c:when>
                                <c:otherwise>
                                    Activate
                                </c:otherwise>
                            </c:choose>
                        </button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/deleteQuestion" method="post">
                        <input type="hidden" name="questionId" value="${q.id}" />
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <form action="${pageContext.request.contextPath}/admin/addQuestion" method="get">
        <button type="submit" class="btn btn-success">Add New Question</button>
    </form>

</div>
</body>
</html>

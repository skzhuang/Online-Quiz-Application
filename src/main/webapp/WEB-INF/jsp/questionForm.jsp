<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title><c:choose><c:when test="${action == 'edit'}">Edit</c:when><c:otherwise>Add</c:otherwise></c:choose> Question</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>

<jsp:include page="navbar.jsp" />

<body>
    <div class="container">
        <h1><c:choose><c:when test="${action == 'edit'}">Edit</c:when><c:otherwise>Add</c:otherwise></c:choose> Question</h1>

        <form action="${pageContext.request.contextPath}/admin/${action}Question" method="post">
            <c:if test="${action == 'edit'}">
                <table class="table">
                    <thead>
                    <tr>
                        <th>CategoryID</th>
                        <th>Description</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${question.categoryId}</td>
                            <td>${question.description}</td>
                            <td>${question.active}</td>
                        </tr>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${action == 'edit'}">
                <input type="hidden" name="id" value="${question.id}" />
            </c:if>

            <div>
                <label for="categoryId">Category ID:</label>
                <input type="number" name="categoryId" id="categoryId" value="1" min="1" max="3" required />
            </div>

            <div>
                <label for="description">Description:</label>
                <textarea name="description" id="description" required>${question.description}</textarea>
            </div>

            <div>
                <label for="isActive">Active:</label>
                <input type="checkbox" name="isActive" id="isActive" value="true" ${question.active ? 'checked' : ''} />
                <input type="hidden" name="isActive" value="false" />
            </div>

            <div>
                <button type="submit">
                    <c:choose>
                        <c:when test="${action == 'edit'}">Update</c:when>
                        <c:otherwise>Add</c:otherwise>
                    </c:choose>
                </button>
            </div>
        </form>

        <a href="${pageContext.request.contextPath}/admin/questionManagement">Back to Question List</a>
    </div>
</body>
</html>

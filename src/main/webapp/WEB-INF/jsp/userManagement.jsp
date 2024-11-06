<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>

<jsp:include page="navbar.jsp" />

<body>
    <div class="container">
        <h1>User Management</h1>
        <table class="table">
            <thead>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Active</th>
                <th>Admin</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td>${u.firstname}</td>
                    <td>${u.lastname}</td>
                    <td>${u.email}</td>
                    <td>
                        <c:choose>
                            <c:when test="${u.active}">
                                Active
                            </c:when>
                            <c:otherwise>
                                Suspended
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${u.admin}">
                                Admin
                            </c:when>
                            <c:otherwise>
                                Not Admin
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <c:if test="${u.id != user.id}">
                    <td>
                        <form action="${pageContext.request.contextPath}/admin/toggleStatus" method="post">
                            <input type="hidden" name="userId" value="${u.id}" />
                            <button type="submit" class="btn btn-secondary">
                                <c:choose>
                                    <c:when test="${u.active}">
                                        Suspend
                                    </c:when>
                                    <c:otherwise>
                                        Activate
                                    </c:otherwise>
                                </c:choose>
                            </button>
                        </form>
                    </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>

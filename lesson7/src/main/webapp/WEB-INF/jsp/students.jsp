<!DOCTYPE HTML>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="command" class="com.example.app.dto.StudentDto" scope="request"></jsp:useBean>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Person List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<h1>Students List</h1>

<br/><br/>
<div>
    <form:form action="/add" method="post" modelAttribute="studentDto">
        <form:label path="name">Name: </form:label> <form:input type="text" path="name"/>
        <form:label path="age">Age: </form:label> <form:input type="number" path="age"/>
        <input type="submit" value="Add"/>
    </form:form>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Age</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${students}" var="student">
            <tr>
                <form:form action="/update" method="post" modelAttribute="studentDto">
                    <td>
                        <form:input type="text" value="${student.id}" path="id" readonly="true"/>
                    </td>
                    <td>
                        <form:input type="text" value="${student.name}" path="name"/>
                    </td>
                    <td>
                        <form:input type="number" value="${student.age}" path="age"/>
                    </td>
                    <td>
                        <input type="submit" value="Update">
                    </td>
                </form:form>
                <td>
                    <form:form action="/remove/${student.id}" method="post">
                        <input type="submit" value="Delete">
                    </form:form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>

</html>
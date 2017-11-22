<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="user" class="ua.nure.kn156.legenka.User" scope="session"/>
<html>
<head><title>User management. Details</title></head>
<body>
 <form action="<%=request.getContextPath()%>/details" method="post">
 <input type="hidden" name="id" value="${user.id}">
   First name <input type="text" name="firstName" value="${user.firstName}"><br>
   Last name <input type="text" name="lastName" value="${user.lastName}"><br>
<<<<<<< HEAD
   Age <input type="text" name="age" value="${user.age}"><br>
=======
   Age <input type="text" name="age" value="${user.age}"><br> 

>>>>>>> d69b299e4d71fe7619c5778aad85eaf50c2d2390
        <input type="submit" name="okButton" value="Ok">
    </form>
    <c:if test="${requestScope.error != null}">
        <script>
            alert("${requestScope.error}")
        </script>
    </c:if>
</body>
</html>

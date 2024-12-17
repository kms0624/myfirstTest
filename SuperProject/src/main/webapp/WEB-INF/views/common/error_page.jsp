<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나는 공용 실패 페이지</title>
</head>
<body>
	<!-- ../ 바로 하나위로 올라가는 방법 -->
	<jsp:include page="../include/menubar.jsp" />

	<h1 style="color:red;">${ failMsg }</h1>
	






	<jsp:include page="../include/footer.jsp" />

</body>
</html>
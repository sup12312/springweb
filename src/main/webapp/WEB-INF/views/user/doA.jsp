<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello doA!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<P>  doC에서 가져온 값 :${msg } </P>
<P>  doC에서 가져온 값 :${member.id } </P>
<P>  doD에서 가져온 Attribute :${asd } </P>
</body>
</html>

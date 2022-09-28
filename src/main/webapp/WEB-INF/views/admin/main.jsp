<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/css/style.css">
</head>
<body>
	<h1 style="text-align:center">관리자 시스템</h1>
	<div class="topnav">
	  <a href="/">HOME</a>
	  <a>공지사항</a>	
	  <a href="member.lo">회원관리</a>
	  <a>탈퇴회원관리</a>
	  <a href="/admin/logout">logout</a>
	</div>
	<h1>회원등록건수 :<%=request.getAttribute("class") %>명</h1>
	<h1>회원탈퇴 요청건수 :<%=request.getAttribute("isdel") %>명</h1>
</body>
</html>
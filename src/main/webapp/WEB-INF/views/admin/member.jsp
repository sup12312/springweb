<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="dto.Member" %>    
<%
Member[] member = (Member[])request.getAttribute("member");
String del = (String)request.getAttribute("expulsion");
%>    
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/css/style.css">
<title>Insert title here</title>
</head>
<body>
	<h1 style="text-align:center">관리자 시스템</h1>
	<div class="topnav">
	  <a href="/">HOME</a>
	  <a>공지사항</a>	
	  <a href="member.lo">회원관리</a>
	  <a>탈퇴회원관리</a>
	</div>
	<h1>회원리스트</h1>
	<hr>
	<form name="member" method="post" action="search.lo">
		<fieldset>
			<input type="checkbox" name="class" value="m">일반회원
			<input type="checkbox" name="class" value="s">운영자
			<input type="checkbox" name="class" value="a">관리자
			<input type="text" name="input" placeholder="이름을 입력하세요">
			<input type="submit" value="검색">
		</fieldset>
	</form>
	<hr>
	<h1><%= member[0].getNum() %>건</h1>
	<button onclick="location.href ='expulsion.lo'">탈퇴회원</button>
	<%if(del != null && del.equals("delete")) {%>
	<button onclick="location.href ='delete.lo'">삭제</button>
	<%} %>
	<table>
		<tr><th>순번
			<th>id		
			<th>비밀번호		
			<th>이름		
			<th>성별		
			<th>등급		
			<th>등록일자		
			<th>탈퇴요청일자		
		</tr>
			<% for(Member m : member){	%>
		<tr><td><%= m.getRownum() %>
			<td><%= m.getId() %>
			<td><%= m.getPw() %>
			<td><%= m.getName() %>
			<td><%= m.getGender() %>
			<td><%= m.getCla() %>
			<td><%= m.getWdate() %>
			<td><%= m.getIwdate() %>
		</tr>
		
			<%}%>
			
	</table>
</body>
</html>
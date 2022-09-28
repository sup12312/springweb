<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="topnav">
  <a href="/">홈</a>
  <a href="/board/boardlist">게시판</a>
<c:choose>
	<c:when test="${user.id != null }">  
		<a id="modal1" href="/member/logout">로그아웃</a>
		<a id="modal1" href="logout.do">마이페이지</a>
		<a id="modal1">${user.name }님 어서오세요</a>
	</c:when>
	<c:otherwise>
		<a id="modal1" href="javascript:login()">로그인</a>
	</c:otherwise>
</c:choose>

</div>

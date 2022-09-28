<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
	function init(){
		var pre = document.getElementsByName("pre");
		var msg;
		var modal = false;
		if(pre[0].value == "login"){
			msg ="로그인 되었습니다.";
			modal = false;
		}
		if(pre[0].value == "loginfail") {
			msg = "로그인 정보가 없습니다.";
			modal = true;
		}
		if(pre[0].value == "member"){
			msg = "회원가입이 완료되었습니다.";
			modal = true;
		}
		if(pre[0].value != "null")alert(msg);
		if(modal){
			document.getElementById("modal").style.display = "block";
			document.getElementById("container").style.display = "block";
		}	
	}
</script>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="common.jsp" %>
	<script src="/js/formCheck.js"></script>
	<title>HOME</title>
</head>

<body onload="init()">
	<%@ include file="header.jsp" %>
	<%@ include file="menu.jsp" %>
	<a href="list.do">리스트</a>
<c:set value="${user }" var="user" />	
<%-- <p> 현재접속자수:<%=lo.getUser() %> --%>
<div class="row">
  <div class="leftcolumn">
    <div class="card">
      <h2>TITLE HEADING</h2>
      <h5>Title description, Dec 7, 2017</h5>
      <div class="fakeimg" style="height:200px;">Image</div>
      <p>Some text..</p>
      <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
    </div>
  </div>
  <div class="rightcolumn">
    <div class="card">
      <h2>About Me</h2>
      <div class="fakeimg" style="height:100px;">Image</div>
      <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
    </div>
    <div class="card">
      <h3>Popular Post</h3>
      <div class="fakeimg"><p>Image</p></div>
      <div class="fakeimg"><p>Image</p></div>
      <div class="fakeimg"><p>Image</p></div>
    </div>
    <div class="card">
      <h3>Follow Me</h3>
      <p>Some text..</p>
    </div>
  </div>
</div>

<%@ include file="footer.jsp" %>
<!-- 화면 끝 -->

<%@ include file="member/login_modal.jsp" %>

</body>
</html>




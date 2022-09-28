<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
	function login(){
		var lo = document.getElementsByName("login");
		if(lo[0].value == "loginfail"){
			document.getElementById("loginstat").innerHTML ="로그인에 실패하셨습니다"
		}
	}
</script>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/css/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

</head>
<body onload="login()">
<input type="hidden" name="login" value="${pre }">
<div class="login">
	<h1>관리 시스템</h1>
	<form class="pw1" method="post" action="login" name="main">
		<input type="text" placeholder=아이디 maxlength="10" name="id" required>
		<input type="password" placeholder=비밀번호 name="pw" required>
		<a id="loginstat"></a><br>
		<input type="submit" value="로그인" style="text-align:center;">
	</form>	
</div>


<button id="myBtn">Try it</button>

<p id="demo"></p>
<script>
/*
$(document).ready(function(){
	  $("input").click(function(){
		  var s = document.getElementById("loginstat");
	    $(s).hide();
	  });
	});
*/	
*document.getElementById("myBtn").addEventListener("click", displayDate);

function displayDate() {
  document.getElementById("demo").innerHTML = Date();
}
</script>
</body>
</html>
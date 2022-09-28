<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../common.jsp" %>
	<script src="/js/formCheck.js"></script>
	<link rel="stylesheet" href="/css/board.css">
	<title>게시판</title>
</head>

<body onload="init()">
<input type="hidden" name="stat" value="<%= request.getParameter("stat") %>">
 <%@ include file="../header.jsp" %>
<%@ include file="../menu.jsp" %>



<div class="row">
  <div class="leftcolumn">
    <div class="card">
    	<h1>게시판</h1>
    	<form method="post" enctype="multipart/form-data" action="/board/insert" id="reg" onsubmit="return check(this)">
    		<h2>제목: <input type="text" name="title" autofocus required></h2>
    		<h2>내용: <textarea name="content" rows="10" cols="100" required></textarea></h2>
    		<input type="file" name="files">
    		<c:set value="${user }" var="user" />
    		<input type="hidden" name="id"value="${user.id }">
	    	<input type="radio" value="y" name="open">공개
	    	<input type="radio" value="n" name="open">비공개
	    	<hr>
	 		<input type="submit" value="등록">
	 		<input type="reset" value="취소">     
    	</form>
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

<%@ include file="../footer.jsp" %>
<!-- 화면 끝 -->

<%@ include file="../member/login_modal.jsp" %>
<div id="boardback">
	<div class="board">
		<a href="javascript:boardmodalEx()" style="float:right">나가기</a>
	<form id="boardform" name="boardmain" action="/board/boardproc.jsp">
		<input type="text" name="title" id="title" placeholder="제목" autofocus>
		<fieldset>
			<legend>공개여부</legend>
				<input type="radio" value="o" name="open">공개
				<input type="radio" value="x" name="open">비공개
		</fieldset>
		<input type="text" name="content" id="content">
		<input type="submit" name="reg" value="등록">
	</form>
	</div>
</div>
<script>
	function boardmodal(){
		document.getElementById("boardback").style.display ="block"
	}
	function boardmodalEx(){
		document.getElementById("boardback").style.display ="none"		
	}
   	function check(f){
   		if(f.open.value == ""){
   			alert("공개여부를 체크하십시오");
   			return false;
   		}
   		return true;
   	}	
</script>
</body>
</html>



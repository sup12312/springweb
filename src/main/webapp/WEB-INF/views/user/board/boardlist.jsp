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

<body>
<%@ include file="../header.jsp" %>
<%@ include file="../menu.jsp" %>



<div class="row">
  <div class="leftcolumn">
    <div class="card">
    	<h1><i class="fa-solid fa-clipboard-list"></i>&nbsp;게시판</h1>
    	<form name="search" method="post" action="/board/boardlist">
	   			<select name="category" onchange="del()">
	   				<option value="a"
	   				<c:if test="${page.c.category eq 'a' }"> selected</c:if> >전체</option>
	   				<option value="title"
	   				<c:if test="${page.c.category eq 'title' }"> selected</c:if>>제목</option>
	   				<option value="name"
	   				<c:if test="${page.c.category eq 'name'}"> selected</c:if>>작성자</option>
	   				<option value="content"
	   				<c:if test="${page.c.category eq 'content' }"> selected</c:if>>내용</option>
	   			</select>	
    		<input type="text" name="keyword"
    		<c:if test="${page.c.category != null }">  value="${page.c.keyword }"</c:if>>
    		<input type="submit" value="검색">
    		<select name="row" onchange="sel()">
    			<c:forEach var="i" begin="3" end="40" step="3">
    				<option value="${i }"
    				<c:if test="${i == page.c.row}"> selected</c:if>
    				>${i }개씩</option>
    			</c:forEach>
    		</select>
    	</form>
<script>
function sel(){
	document.forms["search"].submit();
	
}
function del(){
	var key = document.forms["search"]["category"];
	var sel = key.options[key.selectedIndex].value;
	if(sel == 'a'){
		document.forms["search"]["keyword"].value = null;
	}
}
function insert(){
	var l = confirm("로그인 하시겠습니까?");
	if(l){
		login();
	}
}
</script>
   		<button id="boardin"
   		<c:if test="${user.id != null }"> onclick="location.href ='/board/boardreg'"</c:if> onclick="insert()">등록</button><br>
    <hr>
    	<table class="c">
			  <thead>
				  <tr>
				    <th>순번</th>
				    <th>제목</th>
				    <th>작성일자</th>
				    <th>조회수</th>
				    <th>작성자</th>
				  </tr>
			  </thead>
			  <tbody>
				<c:forEach items="${board}" var="board">
				<c:set var="i" value="${i+1 }"></c:set>
				  <tr onclick="location.href='/board/boarddetail?no=${board.seqno}&cpage=${n}&row=${page.c.row}'">
				    <td id="b">${(page.c.row * (page.c.cpage-1)) + i  }</td>
				    <td>${board.title}</td>
				    <td>${board.wdate }</td>
				    <td>${board.count}</td>
				    <td>${board.name}</td>
				  </tr>
				</c:forEach>				  
			  </tbody>
	    </table>
	 <p>
	 <p>
	 <p>
	 <p>총레코드개수 :${page.t }</p>
	    <div class="pagination">
	    <c:if test="${page.prev }">
		  <a href="/board/boardlist?cpage=${page.start -1 }&row=${page.c.row}&category=${page.c.category}&keyword=${page.c.keyword}">&laquo;</a>
		</c:if>
		  <c:forEach var="n" begin="${page.start }" end="${page.end }">
		  	<a href="/board/boardlist?cpage=${n }&row=${page.c.row}&category=${page.c.category}&keyword=${page.c.keyword}"
		  	   class="${page.c.cpage == n ?"active" :"" }">${n}</a>
		  </c:forEach>
		<c:if test="${page.next }">
		  <a href="/board/boardlist?cpage=${page.end +1 }&row=${page.c.row}&category=${page.c.category}&keyword=${page.c.keyword}">&raquo;</a>
		</c:if>
		</div>
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
	<form id="boardform" name="boardmain" action="/boardproc.jsp">
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
</script>
</body>
</html>



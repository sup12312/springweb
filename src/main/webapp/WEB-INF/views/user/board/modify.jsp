<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
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
    	<h1>게시판</h1>
    	<c:set value="${board }" var="board" />
    	<form method="post" enctype="multipart/form-data" action="modify.bo" id="reg">
    		<input type="hidden" name="seqno" value="${board.no }">
    		<h2>제목: <input type="text" name="title" value="${board.title }"></h2>
    		<h2>내용: <textarea name="content" rows="10" cols="100" >${board.content}</textarea></h2>
	    	<input type="radio" value="y" name="open"<c:if test="${board.open eq 'y' }">checked</c:if>> 공개
	    	<input type="radio" value="n" name="open"<c:if test="${board.open eq 'n' }">checked</c:if>> 비공개
	    	<br>
	    	<c:set value="${board.up }" var="up"/>
	    	<c:choose>
	    		<c:when test="${empty up }">
	    			<input type="file" name="filename">
	    		</c:when>
	    		<c:otherwise>
	    			<c:forEach items="${up }" var="u">
	    				<c:set value="${u.filetype }" var="type" />
	    				<c:set value="${fn:substring(type,0, fn:indexOf(type,'/')) }" var="t" />
	    				<div id = "fs">
		    				<c:if test="${t eq 'image' }">
		    					<c:set value="${u.thumb.filename }" var="tname" />
			    				<img src="/upload/thumbnail/${tname }">
		    				</c:if>
		    				${u.filename }(사이즈:${u.filesize })
		    				<input type="button" value="삭제" onclick="filedel('${u.seqno}','${u.savefilename }','${u.filepath }','${tname }')">
	    				</div>			
	    			</c:forEach>
	    		</c:otherwise>
	    	</c:choose>
	    	
	    	<hr>
	 		<input type="submit" value="수정">
	 		<input type="reset" value="취소">     
    	</form>
    </div>
  </div>
<script>
	function filedel(no,sf,fp,fn) {
		var l = confirm("정말로 삭제하시겠습니까?");
		if(l){
			var x = new XMLHttpRequest();
			x.onreadystatechange = function(){
			var t = document.getElementById("fs");
			console.log('에러코드:'+x.status);
			console.log('에러코드:'+x.raedyState);
				if(x.readyState === 4 && x.status === 200){
					if(x.responseText.trim() === "0"){
						alert("파일삭제실패하였습니다.");
					}else{
						alert("파일삭제완료하였습니다.");
						t.innerHTML = "<input type='file' name='filename'>";
					}
				}else{
						console.log('에러코드:'+x.status);
				}
			};
		}
		x.open("POST","/file/filedel",true);
		x.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		x.send("no="+no+"&sf="+sf+"&fp="+fp+"&fn="+fn);
	}
	
</script>
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
<!-- <div id="boardback">
	<div class="board">
		<a href="javascript:boardmodalEx()" style="float:right">나가기</a>
	<form id="boardform" name="boardmain" action="/board/boardproc2.jsp">
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
</div> -->
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



<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript" src="/js/reply.js"></script>
	<%@ include file="../common.jsp" %>
	<script src="/js/formCheck.js"></script>
	<link rel="stylesheet" href="/css/board.css">
	<title>게시판</title>
</head>
<script>
// 즉시실행  함수
var board_no = '<c:out value="${board.no}"/>';
var id = '<c:out value="${user.id}"/>';
$(document).ready(function(){
	
	console.log(replyService);
	showlist(1);
	//모달 클로즈
	var reply_modal = $("#re_modal");
	$("#modal_close").on("click",function(){
		reply_modal.hide();
	});
	//기본 하이드
	reply_modal.hide();
	//모달 뷰
	var m_content = reply_modal.find("textarea[name='content_re']");
	$(".reply_ul").on("click","li",function(){
		reply_modal.show();
		//this이용시 클릭한 값 자신을 가져옴
		var no =$(this).data("ro");
		console.log(no);
		replyService.replydetail(no,function(result){
			console.log("댓글"+no+"내용 :"+result.content);
			m_content.val(result.content);
			reply_modal.data("rno",result.seqno);
		});
	});
	//수정버튼 클릭
	$("#reply_re_button").on("click",function(){
		console.log("댓글수정번호 :"+reply_modal.data("rno"));
		console.log("댓글수정내용 :"+m_content.val());
		var reply = {seqno : reply_modal.data("rno"),content:m_content.val()};
		console.log("확인용"+reply.seqno);
		replyService.replyupdate(reply,function(result){
			reply_modal.hide();
			showlist(page);
		});
		
	});
	//삭제버튼 클릭
	$("#reply_del_button").on("click",function(){
		var no = reply_modal.data("rno");
		replyService.replydelete(no,function(result){
			reply_modal.hide();
			showlist(page);
		});
	});
	var page = 1;
	function showlist(cpage){
		replyService.list({bno:board_no,cpage:cpage || 1},function(replycnt,li){
			var len=li.length||0;
			console.log(replycnt);
			/* 댓글이 등록된 경우 */
			if(cpage == -1){
				page = Math.ceil(replycnt/5.0);
				showlist(page);
				return;
			}
			for(var i=0; i < len; i++){
				console.log(li[i]);
			};
			/* 댓글이 없는경우 */
			if(len == 0){
				$(".reply_ul").html("");
				return;
			}else{
				var str="";
				for(var i=0; i < len; i++){
					str += "<li data-ro='"+li[i].seqno+"'><div class='replyrow'>"+li[i].id+" | "+li[i].content+" | "+li[i].wdate+"</div></li>";
					$(".reply_ul").html(str);
					replypage(replycnt);
				};
			}
		});
	};
	
	//페이지 분할 출력
	function replypage(allcnt){
		var row = 5;
		var end = Math.ceil(page/(row*1.0))*row;
		var start = end - (row-1);
		var prev = false;
		if(start != 1){
			prev = true;
		}
		var next = false;
		if(end * row >= allcnt){
			end = Math.ceil(allcnt/(row*1.0));
		}
		if(end * row < allcnt){
			next = true;
		}
		var str ="<ul class='pageUL'>";
		if(prev){
			str += "<li class='page-link'>";
			str += "<a href='"+(start-1)+"'이전페이지</a></li>";
		}
		for(var i=start; i<=end; i++){
			var active = page == i ? "active" :	"";
			str += "<li class='page-link " +active+ "'>";
			str += "<a data-page='"+ i + "'>" + i + "</a></li>";
		}
		if(next){
			str += "<li class='page-link'>";
			str += "<a href='" + (end+1)+ "'>다음페이지</a></li>";
		}
		str += "</ul>";
		console.log(str);
		$(".reply_page").html(str);
	}
	
	/* 댓글페이지번호 클릭시 */
	/* li a 는 li 안에 a태그를 찾으라는 뜻이다 안적으면 값을 찾을 수 없다 */
	$(".reply_page").on("click", "li a" ,function(e){
		console.log("page click........");
		//e.preventDefault();
		var clickpage = $(this).data("page");
		console.log(clickpage);
		page = clickpage
		showlist(page)
	});
	
	
	
	//댓글등록 
	$("#replybutton").on("click",function(e){
		var content = document.getElementById("content").value;
		var reply = {boardNo:board_no,id:id,content:content};
		//콜백은 값을 받을 수 있음
		replyService.add(reply,function(result){
			//alert(result);
			console.log(result);
			document.getElementById("content").value = "";
			showlist(-1);
		});
	});
});
	 
</script>	

<body>
<%@ include file="../header.jsp" %>
<%@ include file="../menu.jsp" %>

<script>
	function board(no){
		var l = confirm("정말 삭제하시겠습니까?");
		if(l){
			location.href="/board/boardDel?no="+no;
		}
		
		
	}
</script>

<div class="row">
  <div class="leftcolumn">
    <div class="card">
    	<c:if test="${user.id eq board.id }">
	    	<button class="button1" onclick="board('${board.no}')">삭제</button>
	    	<button class="button1" onclick="location.href='boardDetail.bo?no=${board.no}&page=reg'">수정</button>
	    </c:if>		
    	<h1>게시판</h1>
    		<h4>${board.wdate}</h4>
    		<h4>조회수: ${board.count}</h4>
    		<h3>제목: ${board.title}</h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    		<h3>작성자: ${board.name}</h3>
    		<hr>
    		<label>${board.content }</label>	
    		
    	<!-- 첨부파일 -->
    	<div>
    		<c:set value="${board.up }" var="up" />
    		<c:if test="${up != null }">
    			<c:forEach items="${up }" var="u">
	    			<form name="down" method="post" action="/file/fileDown">
	    				<c:set value="${u.filetype }" var="type" />
	    				<c:set value="${fn:substring(type, 0 , fn:indexOf(type,'/')) }" var="t" />
	    				<c:if test="${t eq 'image' }">
	    					<c:set value="${u.thumb.filename }" var="tname" />
		    				<img src="/upload/thumbnail/${tname }">
		    				<br>썸네일 이름 :${tname }
		    			</c:if>	
	    				${u.filename}
	    				(사이즈: ${u.filesize })
	    				<input type="hidden" name="fpath" value="${u.filepath }">
	    				<input type="hidden" name="fname" value="${u.filename }">
	    				<input type="hidden" name="fsname" value="${u.savefilename }">
		    			<input type="submit" value="다운로드">
		    		</form>		
    			</c:forEach>
  			</c:if>  	
    	</div>
    	<!-- 댓글 등록 폼 -->
    	<div>
	    	<c:if test="${user != null }" >	
	    		<textarea name="content" id="content" placeholder="댓글작성" rows="2" cols="50"></textarea>
	    		<button value="등록" id="replybutton">댓글등록</button>
		    </c:if>
	    </div>	
    	<hr>
    	
    	
    	<!-- 댓글리스트 출력 영역 -->
    	<div id="ajaxlist">
    		<ul class="reply_ul">
    		<!-- data이용시 데이터를 담을 수있음 -->
    			<li>
    				<div>작성자 | 작성내용 | 작성일자</div>
    			</li>
    		</ul>
    	</div>
    	<!-- 댓글페이지 리스트 출력 -->
    	<div class="reply_page"></div>
    	
		<%-- <div class="reply">
		<c:set value="${board.refly }" var="reply" />
		<c:if test="${reply != null }">
			<c:forEach items="${reply}" var= "r">
				${r.content }
				${r.name }
				${r.wdate }
				<hr>
				<br>
			</c:forEach>
		</c:if>
		</div> --%>
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
<!-- 댓글 수정,삭제 창 -->
<div id="re_modal" >
	<div id="main">
		<button id="modal_close">X</button>
		<textarea name="content_re" id="content_re"required></textarea><br>
		<button id="reply_re_button">댓글수정</button>
		<button id="reply_del_button">댓글삭제</button>
	</div>
</div>

<%@ include file="../footer.jsp" %>
<!-- 화면 끝 -->

<%@ include file="../member/login_modal.jsp" %>

</body>
</html>
/**
 * 2022년 6월 2일
 * 작성자 : 강홍묵
 * 내용 : 회원가입 폼체크
 */
 // 로그인창
	function login() {
		document.getElementById("modal").style.display = "block";		
	
		document.getElementById("container").style.display = "block";		
	}
	
	function clo() {
		document.getElementById("modal").style.display = "none";		
		
		document.getElementById("container").style.display = "none";		
	}
	
	
	
	function formCheck() {
		//비밀번호 체크
		var pw = document.forms["memRegform"]["pw"].value;
		//alert(pw); - 안내창 띄우기  
		if(pw.length < 6) {
			alert("비밀번호 문자길이를 확인하세요.");
			return false;
		}	
		
		//이름체크
		if(name = document.forms["memRegform"]["name"].value.length < 1) {
			alert("이름을 입력하세요.");
			//document.getElementById("msg").innerHTML = "이름을 입력하세요";
			return false;
		}
		
		//성별체크
		var gender = document.forms["memRegform"]["gender"].value;
		if(gender == ""){
			alert("성별을 체크하세요.");
			return false;
		} 
		
		//취미체크
		var hobby_length = document.forms["memRegform"]["hobby"].length;
		
		//반복문
		for(var i=0; i < hobby_length ; i++){
		
			if(document.forms["memRegform"]["hobby"][i].checked){
				//alert(i + "번째 취미가 선택되었음");
				console.log(i + "번째 취미가 선택되었음");
				break;
			}
		}	
		
			if(i == hobby_length){
				return false;
			} 
	}
	
	function inputDomain(){
		console.log("도메인선택함");
		var sel = document.forms["memRegform"]["selDomain"].value;
		console.log("선택 옵션값 : " + sel);
		document.forms["memRegform"]["Domain"].value = sel;
		
		if (sel != ""){
			document.forms["memRegform"]["Domain"].readOnly = true;
			document.forms["memRegform"]["Domain"].style.backgroundColor = 'lightgray';
		} else {
			document.forms["memRegform"]["Domain"].readOnly = false;
			document.forms["memRegform"]["Domain"].focus; //커서 생기게 하는거
			document.forms["memRegform"]["Domain"].style.backgroundColor = 'white';
		}
	}
	
	
	
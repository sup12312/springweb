package com.khs.controller.user;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khs.dto.Board;
import com.khs.dto.Cre;
import com.khs.dto.Member;
import com.khs.service.BoardService;


@RestController
@RequestMapping("/ex/")
public class Restcontrollersample {
	
	private final static Logger log = LoggerFactory.getLogger(Restcontrollersample.class);
	
	@Autowired
	BoardService b;
	
	
	//produces : 해당 메소드가 생산하는 mime 타입 => 문자열로 지정,mediatype 클래스로 지정
	@GetMapping(value = "gettext" , produces ="text/plain; charset=utf-8")
	public String gettest() {
		
		log.info("MIME TYPE: "+ MediaType.TEXT_PLAIN_VALUE);
		return "반갑습니다";
	}
	
	//객체변환 :jackson-databind, jackson-dataformat-xml
	//자바객체를 json타입의 문자열로 변횐:gson
	@GetMapping(value = "getboard" , produces={MediaType.APPLICATION_JSON_UTF8_VALUE,
											   MediaType.APPLICATION_XML_VALUE})
	public Board getboard() {
		return b.boardDetail("115");
	}
	
	//컬렉션타입 객체 반환
	@GetMapping("getlist")
	public List<Board> getlist(){
		Cre c= new Cre();
		c.setCpage(1);
		c.setRow(6);
		return b.boardList(c);
	}
	
	@GetMapping("getmap")
	public Map<String, Board> getMap(){
		Map<String, Board> m = new HashMap<String,Board>();
		m.put("first", b.boardDetail("1"));
		m.put("last", b.boardDetail("115"));
		return m;
	}
	
	//ResponseEntity 타입 => 요청 데이터가 정상인지 비정상인지 구분, 헤더에 상태코드값 전달가능
	//메소드 매개변수 타입이 기본데이터타입(int,long,char 등)은 사용불가
	@GetMapping(value="check",params={"page","record"})
	public ResponseEntity<List<Board>> check(Integer page,Integer record){
		Cre c = new Cre(page,record);
		List<Board> bl = b.boardList(c);
		ResponseEntity<List<Board>> result = null;
		if(page < 1 || record > 100) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(bl);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(bl);
		}
		return result;
	}
	
	//파라미터가 url경로에 포홤된 경우 : @pathVarialbe
	// http://localhost:8080/board/{변수}/page/1/record/5
	@GetMapping("board/page/{pno}/record/{rno}/{key}/{value}")
	public List<Board> boardlist(@PathVariable("pno") Integer cpage,@PathVariable("rno") Integer row,
								 @PathVariable("key") String cate,@PathVariable("value") String search){
		Cre c = new Cre(cpage,row);
		c.setCategory(cate);
		c.setKeyword(search);
		List<Board> ls = b.boardList(c); 
		return ls;
	}
	
	//@RequestBody : 요청메시지를 Content-type 헤더에 지정된 값에 따라 httpMㄷssageConvert를 사용해서 요청메시지를 반환함
	// ByteArrayMessageConverter : 바이트배열로 반환
	// StringHttpMessageConverter : String 타입으로 반환
	// FromHttpMessageConverter : application/x-www-form-urlcoded로 읽어서 MultyValueMap<String,String>으로 반환
	// 반대로 MultyValueMap<String,String>=> application/x-www-form-urlcoded 으로 반환
	// 또는 Multypart/form-data 메시지로 반환
	@PostMapping("memberinput")
	public Member convert(@RequestBody Member m) {
		log.info("convert.. member :"+m);
		return null;
	}
	
	
	
}

package com.khs.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khs.dto.Cre;
import com.khs.dto.Reply;
import com.khs.dto.ReplyDB;
import com.khs.dto.Replylist;
import com.khs.service.ReplyService;


@RestController
@RequestMapping("/reply/")
public class ReplyController {
	
	private static final Logger log = LoggerFactory.getLogger("ReplyController.class");
	
	@Autowired
	private ReplyService r;
	
	//consumes는 request시 데이터 처리방식
	//produces는 response시 데이터 처리방식
	@PostMapping(value = "insert", consumes = "application/json",produces= "text/plain; charset=UTF-8")
	public ResponseEntity<String> replyinsert(@RequestBody Reply reply){
		log.info("ReplyController replyinsert called"+reply);
		int rs = r.replyinsert(reply);
		return rs == 1 ? new ResponseEntity<String>("댓글등록완료",HttpStatus.OK) 
					   : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@GetMapping(value="list/{no}/{cpage}",produces= {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Replylist> list(@PathVariable("no") String no,@PathVariable("cpage") int cpage){
		log.info("list .....");
		Cre c = new Cre(cpage,5);
		Replylist reply = r.getlist(no,c);
		return new ResponseEntity<>(reply,HttpStatus.OK);
	}
	@GetMapping(value="{rno}",produces= {MediaType.APPLICATION_JSON_UTF8_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ReplyDB> detail(@PathVariable("rno") int no){
		log.info("get detail......");
		return new ResponseEntity<ReplyDB>(r.detail(no),HttpStatus.OK);
	};
	@PutMapping(value="update",produces = "text/plain; charset=UTF-8")
	//@RequestMapping(method = {RequestMethod.PUT,RequestMethod.PATCH},value="update",produces = "text/plain; charset=UTF-8")
	public ResponseEntity<String> update(@RequestBody ReplyDB re){
		log.info("get update......");
		log.info(re.getContent());
		log.info(re.getSeqno());
		return r.update(re) == 1 ? new ResponseEntity<String>("댓글수정완료",HttpStatus.OK)
								 : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@DeleteMapping(value="delete",produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> delete(@RequestBody int no){
		log.info("get delete ...............");
		return r.delete(no) == 1 ?  new ResponseEntity<String>("댓글삭제완료",HttpStatus.OK)
			 					  : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

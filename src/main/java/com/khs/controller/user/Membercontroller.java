package com.khs.controller.user;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.khs.dto.Member;
import com.khs.service.MemberService;


@Controller
@RequestMapping("/member/*")
public class Membercontroller {
	
	private static final Logger logger = LoggerFactory.getLogger(Membercontroller.class);
	
	@Autowired
	private MemberService m;
	
	
	@RequestMapping("memregform")
	public String memreg() {
		logger.info("회원가입폼");
		return "/member/memregform";
	}
	
	@PostMapping("memreg")
	public  String reg(Member member) {
		m.memReg(member);
		return "index";
	}
	
	@GetMapping("idcheck")
	public ResponseEntity<String> idcheck(@RequestParam("id") String id) {
		logger.info("id체크 call..");
		String rs = Integer.toString(m.check(id));
		return new ResponseEntity<String>(rs,HttpStatus.OK);
	}
	@GetMapping("logout")
	public String logout(HttpSession sess) {
		sess.invalidate();
		return "index";
	}
}

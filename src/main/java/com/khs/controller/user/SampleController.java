package com.khs.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.khs.dto.Member;

@Controller
public class SampleController {
	
	//클래스 앞에 자신으 클래스 이름을 적는다.
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	@RequestMapping("doA")
	public ModelAndView doo() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/memregform");
		mav.addObject("msg","회원가입폼");
		
		return mav;
	}

	@RequestMapping("doB")
	public String doB(Model model) {
		logger.info("doB");
		
		Member m = new Member();
		m.setId("qwe");
		m.setName("홍길동");
		model.addAttribute("dob",m);
		model.addAttribute(m);
		return "result";
	}
	
	@RequestMapping("doC")
	public String doC(@ModelAttribute("msg") String msg, Model model) {
		logger.info("doC called.. {}",msg);
		model.addAttribute("msg",msg);
		Member m = new Member();
		m.setId("qwe");
		m.setName("홍길동");
		model.addAttribute(m);
		return "redirect:/doA";
	}
	
	@RequestMapping("doD")
	public String doC(RedirectAttributes at) {
		Member m = new Member();
		m.setId("qwe");
		m.setName("홍길동");
		
		at.addAttribute("asd","Attribute");
		at.addFlashAttribute(m);
		return "redirect:/doA";
	}
	
	
	//json 라이브러리 추가
	@RequestMapping("jason")
	public @ResponseBody Member jason(){
		Member m = new Member();
		m.setId("qwe");
		m.setName("홍길동");
		m.setHobby_str("축구,테니스");
		return m;
	}
}

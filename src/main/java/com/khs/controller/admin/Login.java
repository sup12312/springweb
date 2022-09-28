package com.khs.controller.admin;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.khs.common.Loginimp;
import com.khs.service.MemberService;


@Controller
@RequestMapping("/")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(Login.class);
	
	@Autowired
	private MemberService m;
	
	@PostMapping("login")
	public String login(@RequestParam("id") String id,@RequestParam("pw") String pw,HttpSession sess,RedirectAttributes ra) {
		Map<Integer,String> lo = m.login(id, pw);
		Set<Entry<Integer,String>> i2 = lo.entrySet();
		Iterator<Entry<Integer,String>> entry = i2.iterator();
		Entry<Integer,String> e1 =entry.next();
		
		//뷰페이지 분기
		String view = null;
		switch(e1.getKey()) {
		case 1:
			Loginimp li = new Loginimp(id , e1.getValue());
			sess.setAttribute("user", li);
			ra.addFlashAttribute("pre", "login");
			view = "/main";
			break;
		default:
			ra.addFlashAttribute("pre", "loginfail");
			view = "redirect:/admin/";
		}
	
		return view;
	}
	@GetMapping("logout")
	public String logout(HttpSession sess) {
		sess.invalidate();
		return "redirect:/admin/";
	}
	public int test(int num,int num2) {
		int i = 0;
		i = num + num2;
		return i;
	}

}

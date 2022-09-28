package com.khs.controller.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.khs.common.Loginimp;
import com.khs.dto.Board;
import com.khs.dto.BoardList;
import com.khs.dto.Cre;
import com.khs.dto.Page;
import com.khs.service.BoardService;


@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	BoardService board;
	
	
	@RequestMapping(value ="boardlist", method = {RequestMethod.POST,RequestMethod.GET})
	public String boardlist(Model m,Cre c){
		if(c.getCpage() == 0 ) {
			c.setCpage(1);
		}
		if(c.getRow() == 0) {
			c.setRow(3);
		}
		List<Board> b = board.boardList(c);
		m.addAttribute("board", b);
		m.addAttribute("page", new Page(board.getT(c),c));
		
		return "/board/boardlist";
	}
	@RequestMapping(value = "boarddetail", method= {RequestMethod.GET,RequestMethod.POST})
	public String detail(Model m,@ModelAttribute("no") String no) {
		m.addAttribute("board", board.boardDetail(no));
		return	"/board/Detail";
	}
	@GetMapping("boardreg")
	public void boardinsert() {
	
	}
	@PostMapping("insert")
	public String insert(Board b,MultipartFile files,HttpSession sess,RedirectAttributes m) {
		b.setId(((Loginimp)sess.getAttribute("user")).getId());;
		String no = board.boardinsert(b,files);
		m.addFlashAttribute("no",no);
		return "redirect:/board/boarddetail";
	}
	
	@GetMapping("boardDel")
	public String boarddel(@RequestParam("no")String no) {
		board.boarddelete(no);
		return "redirect:/board/boardlist";
	}
	  
	 
}

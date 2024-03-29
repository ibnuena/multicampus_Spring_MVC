package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.common.util.CommonUtil;
import com.user.domain.MemberVO;
import com.user.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class MemberController {

	@Autowired // byType
	private CommonUtil util;

	@Autowired
	private MemberService mService;

	@GetMapping("/signup")
	public String joinForm() {

		return "member/join";
	}

	@PostMapping("/signup")
	public String joinEnd(Model m, MemberVO user) {
		log.info("user : " + user);

		// 유효성 체크
		if (user.getUserid() == null || user.getName() == null || user.getUserid().trim().isBlank()
				|| user.getName().trim().isBlank())
			return "redirect:signup";

		int n = mService.insertMember(user);

		String msg = (n > 0) ? "회원가입 완료!" : "회원가입 실패";
		String loc = (n > 0) ? "index" : "javascript:history.back()";

		return util.addMsgLoc(m, msg, loc);
	}

	@GetMapping("/idCheck")
	public String idCheckForm() {
		// get 방식 요청이면 id 입력 form을 보여주고
		return "member/idCheck";
	}

	@PostMapping("/idCheck")
	public String idCheckEnd(Model m, @RequestParam(defaultValue="") String userid) {
		// post 방식 요청이면 id 사용 가능 여부를 보여주자
		if (userid == null || userid.trim().isBlank()) {
			return util.addMsgBack(m, "아이디를 입력하세요");
		}

		boolean isUse = mService.idCheck(userid);
		
		// 사용 가능하면 true, 중복 아이디면 false
		String msg = (isUse) ? userid + "는 사용 가능한 id입니다" : userid + "는 이미 사용중인 id입니다";
		String result = (isUse) ? "ok" : "fail";

		m.addAttribute("msg", msg);
		m.addAttribute("result", result);
		m.addAttribute("uid", userid);
		
		return "member/idCheckResult";
	}
	
	@GetMapping("/user/mypage")
	public String mypage() {
		return "member/mypage";
	}

}

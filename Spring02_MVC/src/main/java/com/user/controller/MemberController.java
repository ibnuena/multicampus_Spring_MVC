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

		// ��ȿ�� üũ
		if (user.getUserid() == null || user.getName() == null || user.getUserid().trim().isBlank()
				|| user.getName().trim().isBlank())
			return "redirect:signup";

		int n = mService.insertMember(user);

		String msg = (n > 0) ? "ȸ������ �Ϸ�!" : "ȸ������ ����";
		String loc = (n > 0) ? "index" : "javascript:history.back()";

		return util.addMsgLoc(m, msg, loc);
	}

	@GetMapping("/idCheck")
	public String idCheckForm() {
		// get ��� ��û�̸� id �Է� form�� �����ְ�
		return "member/idCheck";
	}

	@PostMapping("/idCheck")
	public String idCheckEnd(Model m, @RequestParam(defaultValue="") String userid) {
		// post ��� ��û�̸� id ��� ���� ���θ� ��������
		if (userid == null || userid.trim().isBlank()) {
			return util.addMsgBack(m, "���̵� �Է��ϼ���");
		}

		boolean isUse = mService.idCheck(userid);
		
		// ��� �����ϸ� true, �ߺ� ���̵�� false
		String msg = (isUse) ? userid + "�� ��� ������ id�Դϴ�" : userid + "�� �̹� ������� id�Դϴ�";
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

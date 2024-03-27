package com.multi.campus;

import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.common.util.CommonUtil;
import com.memo.model.MemoVO;
import com.shop.service.SampleTxService;
import com.user.domain.MemberVO;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class SampleTxController {
	
	@Inject
	private SampleTxService service;
	
	@Inject
	private CommonUtil util;
	
	@GetMapping("/tx")
	public String transactionTest(Model m) {
		String str = "test 500 mileage plz ~~~~~!!";
//		str += "test 500 mileage plz";
//		str += "test 500 mileage plz";
//		str += "test 500 mileage plz";
//		str += "test 500 mileage plz";
//		str += "test 500 mileage plz";
//		str += "test 500 mileage plz";
//		str += "test 500 mileage plz";

		MemberVO user = new MemberVO();
		user.setUserid("ban01");
		user.setMileage(500);
		
		MemoVO memo = new MemoVO();
		memo.setMsg(str);
		memo.setName(user.getUserid());
		
		service.addMemo(memo, user);
		
		String msg = user.getUserid() + "님의 글이 한줄 메모장에 등록되었습니다.";
		String loc = "index";
		
		
		return util.addMsgLoc(m, msg, loc);
	}
}

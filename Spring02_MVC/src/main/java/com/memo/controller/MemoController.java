package com.memo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.common.util.CommonUtil;
import com.memo.model.MemoVO;
import com.memo.service.MemoService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j // log객체 사용 가능
public class MemoController {
	
	@Autowired
	private MemoService memoService;
	
	@Autowired
	private CommonUtil util;
	
	@RequestMapping(value="/memo", method=RequestMethod.GET)
	public String memoForm() {
		
		return "memo/input"; // View Name
		// servlet-context.xml에 등록된 InternalResourceViewResolver빈이
		// 뷰 네임 앞에 접두어("/WEB-INF/views/")와 접미어(.jsp)를 붙여준다.
		///WEB-INF/views/memo/input.jsp
	}
	
	@RequestMapping(value="/memo", method=RequestMethod.POST)
	public String memoInsert(Model model, MemoVO vo) {
		
		if(vo.getName()==null || vo.getName().trim().isBlank()) {
			return "redirect:memo"; // redirect방식으로 이동
		}
		
		
		int n = 0;
//		for(int i=0; i<20; i++)
			n = this.memoService.insertMemo(vo);
		
		String msg = (n>0) ? "글 등록 성공" : "글 등록 실패";
		String loc = (n>0) ? "memoList" : "javascript:history.back()";
		
		model.addAttribute("msg", msg);
		model.addAttribute("loc", loc);
		
		return "message";
	}// --------------------------------------------------
	
	@RequestMapping(value="/memoList", method=RequestMethod.GET)
	public String memoList(Model model, @RequestParam(defaultValue="1") int pageNum) {
		log.info("pageNum: " +pageNum);
		
		if(pageNum<1) 
			pageNum=1;
		
		int totalCount = memoService.getMemoTotalCount(); // 총 게시글 수
		int oneRecordPage = 5;
		int pageCount = (totalCount-1)/oneRecordPage + 1;
		
		if(pageNum>pageCount)
			pageNum = pageCount;
		
		// where rn between 1 and 5
		int start = (pageNum-1)*oneRecordPage; // oracle에서 db 끊어올 때 시작값
		int end = start + (oneRecordPage+1); // end값
		
		/* 페이징 블럭 연산
		 * pagingBlock = 5;
		 *  ◀ : prevBlock
		 *  ▶ : nextBlock
		 * pageNum
		 * [1][2][3][4][5] ▶ | ◀ [6][7][8][9][10] ▶ | ◀ [11][12][13][14][15] ▶
		 * 
		 * pageNum       pagingBlock       prevBlock       nextBlock
		 * 1~5           5                 0               6
		 * 6~10          5                 5               11
	     * 11~15         5                 10              16
	     * 
	     * prevBlock = (pageNum-1) / pagingBlock * pagingBlock;
	     * nextBlock = prevBlock + (pagingblock+1)
	     * 
		 * **/
		int pagingBlock = 5;
		int prevBlock = (pageNum-1)/pagingBlock * pagingBlock;
		int nextBlock = prevBlock + (pagingBlock+1);
		
		// 서비스 listMemo() 호출. 반환값 model에 저장
		List<MemoVO> vo = this.memoService.listMemo(start, end);
		
		int offset = (pageNum-1)*oneRecordPage; //mysql
		int limit = oneRecordPage;
		
//		List<MemoVO> vo = this.memoService.listMemoMySQL(limit, offset); // mysql

		model.addAttribute("vo", vo);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageCount", pageCount);
		
		model.addAttribute("prevBlock", prevBlock);
		model.addAttribute("nextBlock", nextBlock);
		model.addAttribute("pagingBlock", pagingBlock);
		
		return "memo/list2";
	}// -------------------------------------------------------
	
	@RequestMapping(value="/memoDel", method=RequestMethod.GET)
	public String memoDelete(Model model, @RequestParam(name="no", defaultValue="0") int no) {
		
		log.info("no : " + no);
		
		if(no==0) {
			return "redirect:memoList";
		}
		
		int n = memoService.deleteMemo(no);
		String msg = (n>0)? "삭제 성공" : "삭제 실패";
		String loc = "memoList";
		
		return "redirect:memoList";
	}// -------------------------------------------------------

	@RequestMapping(value="/memoEdit", method=RequestMethod.GET)
	public String memoEditForm(Model model, @RequestParam(defaultValue="0") int no) {
		
		// 유효성 체크
		if(no==0)
			return util.addMsgBack(model, "잘못된 경로입니다.");
		
		// getMemo(글번호) -> MemoVO 반환 -> model에 저장 "key값 : vo"
		MemoVO vo = memoService.getMemo(no);
		
		model.addAttribute("vo", vo);
		
		return "memo/edit";
	}// -------------------------------------------------------

	@RequestMapping(value="/memoEdit", method=RequestMethod.POST)
	public String memoEditEnd(Model model, MemoVO memo) {
		log.info("memo : "  + memo);
		
//		유효성 체크
		if(memo.getNo()==0 || memo.getName()==null || memo.getName().trim().isBlank())
			return util.addMsgBack(model, "작성자를 입력하세요");
		
		int n=memoService.updateMemo(memo);
		String msg=(n>0) ? "수정 완료" : "수정 실패";
		
//		model.addAttribute("mode", "popup");
//		model.addAttribute("msg", msg);
//		model.addAttribute("loc", "memoList");
		
//		return "message";
		return util.addMsgPopup(model, msg);
	}// -------------------------------------------------------
	
}


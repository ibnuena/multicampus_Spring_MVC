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
@Log4j // log��ü ��� ����
public class MemoController {
	
	@Autowired
	private MemoService memoService;
	
	@Autowired
	private CommonUtil util;
	
	@RequestMapping(value="/memo", method=RequestMethod.GET)
	public String memoForm() {
		
		return "memo/input"; // View Name
		// servlet-context.xml�� ��ϵ� InternalResourceViewResolver����
		// �� ���� �տ� ���ξ�("/WEB-INF/views/")�� ���̾�(.jsp)�� �ٿ��ش�.
		///WEB-INF/views/memo/input.jsp
	}
	
	@RequestMapping(value="/memo", method=RequestMethod.POST)
	public String memoInsert(Model model, MemoVO vo) {
		
		if(vo.getName()==null || vo.getName().trim().isBlank()) {
			return "redirect:memo"; // redirect������� �̵�
		}
		
		
		int n = 0;
//		for(int i=0; i<20; i++)
			n = this.memoService.insertMemo(vo);
		
		String msg = (n>0) ? "�� ��� ����" : "�� ��� ����";
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
		
		int totalCount = memoService.getMemoTotalCount(); // �� �Խñ� ��
		int oneRecordPage = 5;
		int pageCount = (totalCount-1)/oneRecordPage + 1;
		
		if(pageNum>pageCount)
			pageNum = pageCount;
		
		// where rn between 1 and 5
		int start = (pageNum-1)*oneRecordPage; // oracle���� db ����� �� ���۰�
		int end = start + (oneRecordPage+1); // end��
		
		/* ����¡ �� ����
		 * pagingBlock = 5;
		 *  �� : prevBlock
		 *  �� : nextBlock
		 * pageNum
		 * [1][2][3][4][5] �� | �� [6][7][8][9][10] �� | �� [11][12][13][14][15] ��
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
		
		// ���� listMemo() ȣ��. ��ȯ�� model�� ����
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
		String msg = (n>0)? "���� ����" : "���� ����";
		String loc = "memoList";
		
		return "redirect:memoList";
	}// -------------------------------------------------------

	@RequestMapping(value="/memoEdit", method=RequestMethod.GET)
	public String memoEditForm(Model model, @RequestParam(defaultValue="0") int no) {
		
		// ��ȿ�� üũ
		if(no==0)
			return util.addMsgBack(model, "�߸��� ����Դϴ�.");
		
		// getMemo(�۹�ȣ) -> MemoVO ��ȯ -> model�� ���� "key�� : vo"
		MemoVO vo = memoService.getMemo(no);
		
		model.addAttribute("vo", vo);
		
		return "memo/edit";
	}// -------------------------------------------------------

	@RequestMapping(value="/memoEdit", method=RequestMethod.POST)
	public String memoEditEnd(Model model, MemoVO memo) {
		log.info("memo : "  + memo);
		
//		��ȿ�� üũ
		if(memo.getNo()==0 || memo.getName()==null || memo.getName().trim().isBlank())
			return util.addMsgBack(model, "�ۼ��ڸ� �Է��ϼ���");
		
		int n=memoService.updateMemo(memo);
		String msg=(n>0) ? "���� �Ϸ�" : "���� ����";
		
//		model.addAttribute("mode", "popup");
//		model.addAttribute("msg", msg);
//		model.addAttribute("loc", "memoList");
		
//		return "message";
		return util.addMsgPopup(model, msg);
	}// -------------------------------------------------------
	
}


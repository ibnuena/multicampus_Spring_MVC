package com.shop.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.common.util.CommonUtil;
import com.shop.domain.CategoryVO;
import com.shop.domain.ProductVO;
import com.shop.service.AdminService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/admin")
public class AdminController {
	
	@Inject
	private CommonUtil util;
	
	@Inject
	private AdminService adminService;
	
	@GetMapping("/prodForm")
	public String productForm(Model m) {
		
		List<CategoryVO> upCgList = adminService.getUpcategory();
		m.addAttribute("upCgList", upCgList);
		
		return "admin/prodInput";
	}
	
	
	// 0. servlet-context.xml에 multipartResolver 빈 등록
	@PostMapping("/prodInsert")
	public String productInsert(Model m, ProductVO item, 
			@RequestParam("pimage") List<MultipartFile> pimage, 
			@RequestParam(value="mode", defaultValue="insert") String mode,
			HttpServletRequest req) {
		
		log.info("@@ mode : " + mode);
		log.info("@@ item : " + item);
		log.info("@@ pimage : " + pimage);
		// 1. 업로드 할 디렉토리의 절대 경로 얻기
		ServletContext app = req.getServletContext();
		String upDir = app.getRealPath("/resources/product_images");
		log.info("@@ upDir : " + upDir);
		
		// 2. 파일 업로드 처리 -> MultipartFile의 transferTo()메서드 이용
		if(pimage!=null) {
			for(int i=0; i<pimage.size(); i++) {
				MultipartFile mf = pimage.get(i);
				if(!mf.isEmpty()) { // 업로드 했다면
					try {
						String fileName = mf.getOriginalFilename(); // 첨부파일명
						mf.transferTo(new File(upDir, fileName)); // 업로드 처리
						
						// 3. 업로드한 파일명을 ProductVO의 pimage1, pimage2, pimage3에 세팅
						if(i==0) {
							item.setPimage1(fileName);
						}else if(i==1) {
							item.setPimage2(fileName);
						}else if(i==2) {
							item.setPimage3(fileName);
						}
					}catch(IOException e) {
						log.error("파일 업로드 중 에러 : " + e);
					}
				}
			} // for ---------
		} // if ---------
		log.info("item2 : " + item);
		
		int n = 0;
		String s;
		
		if(mode.equals("insert")) {
			n = adminService.productInsert(item); // 등록
			s = "등록";
		}else if(mode.equals("edit")) {
			n = adminService.productUpdate(item); // 수정
			s = "수정";
		}
		
		
		String msg = (n>0) ? "등록 성공" : "등록 실패";
		String loc = (n>0) ? "prodList" : "javascript:history.back()";
		
		return util.addMsgLoc(m, msg, loc);
	}
	
	@GetMapping("/prodList")
	public String productList(Model m) {
		
		List<ProductVO> itemList = adminService.productList();
		
		m.addAttribute("itemList", itemList);
		
		return "admin/prodList";
	}
	
	@PostMapping("/prodDel")
	public String productDelete(Model m, int pnum) {
		
		log.info("del : " + pnum);
		
		
		int n = adminService.productDelete(pnum);
		
		
		String msg = (n>0) ? "삭제 성공" : "삭제 실패";
		String loc = (n>0) ? "prodList" : "javascript:history.back()";
		
		return util.addMsgLoc(m, msg, loc);
	}
	
	@PostMapping("/prodEditForm")
	public String productEditForm(Model m, @RequestParam(defaultValue="0") int pnum) {
		log.info("pnum : " + pnum);
		if(pnum==0) {
			return "redirect:prodList";
		}
		
		List<CategoryVO> upCgList = adminService.getUpcategory();
		m.addAttribute("upCgList", upCgList);
		
		ProductVO item = adminService.getProduct(pnum);
		
		m.addAttribute("item", item);
		
		return "admin/prodEdit";
	}
}

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
	
	
	// 0. servlet-context.xml�� multipartResolver �� ���
	@PostMapping("/prodInsert")
	public String productInsert(Model m, ProductVO item, 
			@RequestParam("pimage") List<MultipartFile> pimage, 
			@RequestParam(value="mode", defaultValue="insert") String mode,
			HttpServletRequest req) {
		
		log.info("@@ mode : " + mode);
		log.info("@@ item : " + item);
		log.info("@@ pimage : " + pimage);
		// 1. ���ε� �� ���丮�� ���� ��� ���
		ServletContext app = req.getServletContext();
		String upDir = app.getRealPath("/resources/product_images");
		log.info("@@ upDir : " + upDir);
		
		// 2. ���� ���ε� ó�� -> MultipartFile�� transferTo()�޼��� �̿�
		if(pimage!=null) {
			for(int i=0; i<pimage.size(); i++) {
				MultipartFile mf = pimage.get(i);
				if(!mf.isEmpty()) { // ���ε� �ߴٸ�
					try {
						String fileName = mf.getOriginalFilename(); // ÷�����ϸ�
						mf.transferTo(new File(upDir, fileName)); // ���ε� ó��
						
						// 3. ���ε��� ���ϸ��� ProductVO�� pimage1, pimage2, pimage3�� ����
						if(i==0) {
							item.setPimage1(fileName);
						}else if(i==1) {
							item.setPimage2(fileName);
						}else if(i==2) {
							item.setPimage3(fileName);
						}
					}catch(IOException e) {
						log.error("���� ���ε� �� ���� : " + e);
					}
				}
			} // for ---------
		} // if ---------
		log.info("item2 : " + item);
		
		int n = 0;
		String s;
		
		if(mode.equals("insert")) {
			n = adminService.productInsert(item); // ���
			s = "���";
		}else if(mode.equals("edit")) {
			n = adminService.productUpdate(item); // ����
			s = "����";
		}
		
		
		String msg = (n>0) ? "��� ����" : "��� ����";
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
		
		
		String msg = (n>0) ? "���� ����" : "���� ����";
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

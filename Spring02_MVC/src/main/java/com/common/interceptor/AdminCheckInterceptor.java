package com.common.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.user.domain.MemberVO;

public class AdminCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession ses = request.getSession();
		MemberVO user = (MemberVO)ses.getAttribute("loginUser");
		
		if(user.getMstate()!=9) {
			request.setAttribute("msg", "관리자만 이용할 수 있습니다");
			String loc = request.getContextPath() + "/index";
			request.setAttribute("loc", loc);
			
			RequestDispatcher disp = request.getRequestDispatcher("/WEB-INF/views/message.jsp");
			disp.forward(request, response);
			
			return false; // false를 반환하면 컨트롤러로 넘어가지 못함
		}
		
		return true;
	}
	
	// [1] preHandle() : 사전 처리. Controller를 실행하기전에 호출되는 메서드
	// [2] postHandle() : 사후 처리. Controller를 실행한 후, 아직 뷰를 실행하기 전에 호출
	// [3] afterCompletion() : 사후 처리. Controller를 실행하고 뷰를 실행한 후 호출
	
}
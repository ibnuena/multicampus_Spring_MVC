<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-8 offset-2" style="height:450px">
	<h1 class="text-secondary text-center">Login</h1>
	<br><br>
	<%--
		el 표현식을 이용한 쿠키 출력
		${cookie.키이름.value}
	<h1>${cookie.uid.value}</h1>
	
		${cookie} -> Map 객체
		${cookie.key} -> Cookie 객체
		${cookie.key.value} -> String 객체 쿠키.getValue();
	--%>
	<form name="loginF" method="post" action="login">
	<table class="table">
		<tr>
			<th width="20%">아이디</th>
			<td>
				<input type="text" name="userid" required class="form-control" value="${cookie.uid.value}">
			</td>
		</tr>
		<tr>
			<th width="20%">비밀번호</th>
			<td>
				<input type="password" name="pwd" required class="form-control">
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:center">
				<label for="saveId">
					<input type="checkbox" name="saveId" id="saveId" 
					<c:if test="${cookie.uid ne null}">
						checked
					</c:if>
					style="width:12%" > 아이디 저장
				</label>
				&nbsp;&nbsp;&nbsp;
				<button class="btn btn-primary">로그인</button>
			</td>
		</tr>
	</table>
	</form>
</div>


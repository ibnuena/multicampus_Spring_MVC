<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- idCheckResult.jsp -->
<style>
	.jumbotron, .navbar{
		display: none;
	}
</style>
<script>
	function id_check(){
		if(!idf.userid.value){
			alert('아이디를 입력하세요');
			idf.userid.focus();
			return false;
		}
		return true;
	}
	function setId(uid){
		// window>document>form
		// opener ==> 부모창(window 객체)
		opener.document.mf.userid.value = uid;
		
		// self -> 자기자신 창 닫기
		self.close();
	}
</script>
<div class="container">
	<br>
	<form name="idf" action="idCheck" method="post" onsubmit="return id_check()">
		<label for="id">아이디</label>
		<input type="text" name="userid" id="userid" class="form-control" placeholder="ID" autofocus="autofocus"><br>
		<button class="btn btn-outline-success">확인</button>
	</form>
	
	<div id="idResult" style="margin:1em auto">
		<br>
		<h3>${msg}</h3>
		<br>
		<c:if test="${result eq 'ok'}">
			<button class="btn btn-outline-success" onclick="setId('${uid}')">아이디 사용하기</button>
		</c:if>
	</div>
	
</div>
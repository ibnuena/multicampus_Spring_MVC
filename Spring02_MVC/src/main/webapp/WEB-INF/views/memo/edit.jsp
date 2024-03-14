<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>

input {
	padding: 4px;
	width: 60%;
	border: 1px solid #ddd;
}

ul.memo {
	width: 90%;
	margin: 1em auto;
}

ul.memo li {
	float: left;
	line-hight: 40px;
	height: 40px;
	padding: 4px;
}

ul.memo li {
	list-style: none;
	border-top: 1px solid silver;
}

ul.memo li:nth-child(2n+1) {
	width: 20%;
}

ul.memo li:nth-child(2n) {
	width: 75%;
}
</style>
<div class="container" style="text-align: center">
	<h1>한줄 메모장 - edit</h1>
	<form name="mf" action="memoEdit" method="post">
		<input type="hidden" name="no" value="${param.no }">
		<ul class="memo">
			<li>작성자</li>
			<li><input type="text" name="name" id="name" placeholder="Name"
				required value="${vo.name }"></li>
			<li>메모내용</li>
			<li><input type="text" name="msg" id="msg" placeholder="Message"
				required value="${vo.msg }"></li>
			<li></li>
			<li><button>글수정</button></li>
		</ul>

	</form>
</div>
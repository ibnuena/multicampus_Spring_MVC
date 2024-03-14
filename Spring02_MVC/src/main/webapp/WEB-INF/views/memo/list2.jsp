<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	body *{
		padding: 0;
		margin: 0;
		box-sizing: border-box;
	}
	#memoList{
		text-align: center;
		width: 90%;
		margin: auto;
	}
	ul.memos{
		margin: 2em auto;
	}
	ul.memos li{ 
		list-style: none;
		line-height: 40px;
		height: 40px;
		padding: 5px;
		border-bottom: 1px solid #7FE1D4;
		float: left;
	}
	ul.memos li:nth-child(4n+1){
		width: 10%;
	}
	ul.memos li:nth-child(4n+2){
		width: 60%;
	}
	ul.memos li:nth-child(4n+3){
		width: 15%;
	}
	ul.memos li:nth-child(4n){
		width: 15%;
	}
	span.ed{
		float: right;
	}
	.active{
		font-weight: bold;
		color: pink;
		background-color: gray;
	}
</style>

<!-- 글쓰기 페이지 포함 ---------------------- -->
<br>
	<c:import url="/memo"/>
<br><br><br><br><br><br><br><br>
<!-- ------------------------------------ -->
<div id="memoList">
	<!-- <h1 class="text-center">한줄 메모장 목록</h1> -->
	<ul class="memos">
		<li>글번호</li>
		<li>메모내용</li>
		<li>작성자</li>
		<li>작성일</li>
		<!-- --------------- -->
		<c:forEach var="user" items="${vo}">
			<li>
				<c:out value="${user.no }"/>
			</li>
			<li>
				<c:out value="${user.msg }"/>
				<span class="ed">
					[<a href="#" onclick="edit('<c:out value="${user.no }"/>')">수정</a>|
					<a href="memoDel?no=<c:out value='${user.no }'/>">삭제</a>]
				</span>
			</li>
			
			<li>
				<c:out value="${user.name }"/>
			</li>
			<li>
				<fmt:formatDate value="${user.wdate }" pattern="yy-MM-dd" />
			</li>
		</c:forEach>
		<!-- --------------- -->
	</ul>
	<div style="clear:both"></div>
	<br>
	<div class="page">
		<!-- 페이징 블럭 처리 --------------------------------------- -->
		<c:if test="${prevBlock > 0}">
			<a href="memoList?pageNum=${prevBlock}">◀</a>
		</c:if>
		
		<c:forEach var="i" begin="${prevBlock + 1}" end="${nextBlock - 1}">
			<c:if test="${i <= pageCount}">
			
				<c:choose>
					<c:when test="${param.pageNum eq i }">
						[<a href="memoList?pageNum=${i}" class='active'>${i}</a>]
					</c:when>
					<c:otherwise>
						[<a href="memoList?pageNum=${i}">${i}</a>]
					</c:otherwise>
				</c:choose>
				
			</c:if>
		</c:forEach>
		
		<c:if test="${nextBlock <= pageCount}">
			<a href="memoList?pageNum=${nextBlock}">▶</a>
		</c:if>
		<!-- 페이징 블럭 처리 --------------------------------------- -->
		
		<span class="ed">총 게시글 수 : <c:out value="${totalCount }"/>개</span>
	</div>
	
</div>

<script>
	const edit=function(num){
		// alert(num);
		let url = 'memoEdit?no=' + num; // GET 방식 요청
		win = window.open(url, "edit", "width=700, height=400, left=100, top=100");
	}
</script>
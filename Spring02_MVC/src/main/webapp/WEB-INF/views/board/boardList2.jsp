<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	.container{
		overflow-y: auto;
	}
</style>

<div class="container">
		<h1 class="text-center mt-4">Spring Board 목록</h1>
	<br><br>
	<a href="write">글쓰기</a>
	<br><br>
	<!-- 검색 form 시작-----------------------  -->
	<div id="divFind" class="col-md-10 offset-1 text-center">
		<form name="findF" id="findF" action="list"
						class="form-inline">
						<select name="findType" id="findType" class="form-control m-3">
							<option value="0">::검색 유형::</option>
							<option value="1">제 목</option>
							<option value="2">작성자</option>
							<option value="3">글내용</option>
						</select> 
						<input type="text" name="findKeyword" id="findKeyword"
							placeholder="검색어를 입력하세요" class="form-control m-3" required>
						<button class="btn btn-primary">검 색</button>
		</form>
	</div>
	<!-- ----------------------------------- -->
	<br><br>
	<table class="table">
		<tr class="m1">
			<th width="10%">글번호</th>
			<th width="40%">제  목</th>
			<th width="20%">작성자</th>
			<th width="20%">작성일</th>
			<th width="10%">조회수</th>			
		</tr>
		<c:if test="${list eq null or empty list}">
			<tr>
				<td colspan="5">
					<b>데이터가 없습니다</b>
				</td>
			</tr>
		</c:if>
		<!-- --------------------------- -->
		
		<c:if test="${list ne null and not empty list}">
			<c:forEach var="board" items="${list}">
				<tr>
					<td><c:out value="${board.num}"/></td>
					<td align="left" style="padding-left:20px">
						<!-- 답변 레벨에 따라 들여쓰기 -->
						<c:forEach var="k" begin="1" end="${board.getLev()}">
							&nbsp;&nbsp;&nbsp;
						</c:forEach>
						<c:if test="${board.lev > 0}">
							<img src="${myctx}/resources/images/re.png" width="15px">
						</c:if>
						
						<!-- campus/board/view?num=10 -->
						<a href="view/<c:out value='${board.num}'/>"><c:out value="${board.subject}"/></a>
						
						
						<!-- 최신글에 new 붙이기 -->
						<c:if test="${board.newImg < 1}">
							<span class="badge badge-danger">New</span>
						</c:if>
						
						<c:if test="${board.filename ne null}">
							<img src="${pageContext.request.contextPath}/resources/images/attach.png" width="20px">
						</c:if>
					</td>
					<td><c:out value="${board.userid}"/></td>
					<td>
						<fmt:formatDate value="${board.wdate}" pattern="yy-MM-dd"/>
					</td>
					<td><c:out value="${board.readnum}"/></td> 
				</tr>
			</c:forEach>
		</c:if>
		<!-- --------------------------- -->

		<tr>
			<td colspan="3">
				
				<!-- 페이지 네비게이션 -->
				${pageNavi}
				
			</td>
			<td colspan="2">
				총게시글 수: <span style="color:red;font-weight:bold">${totalCount}</span> 개
			</td>
		</tr>
	</table>

</div>





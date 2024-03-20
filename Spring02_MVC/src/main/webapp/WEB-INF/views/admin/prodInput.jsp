<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script>
// $.ajax({}) : AJAX 처리를 하는 JQuery 함수
	$(function(){
		$('#upCg_code').on('change', function(){
			let upCode = $(this).val();
			// alert(upCode);
			$.ajax({
				type: 'get', // 요청 메서드 
				url: 'downCgListJSON?upCg_code='+upCode,
				dataType: 'json', // 응답 유형(html, text, json, xml)
				cache: false, // 캐시 사용 안함
				success: function(res){ // 성공 응답이 온 경우
					// alert(res); // 배열로 들어옴 -> 반복문 이용
					
					let str = `<option value="">::하위 카테고리::</option>`;
					for(var i=0; i<res.length; i++){
						let obj = res[i]; // 카테고리
						str += `<option value="\${obj.downCg_code}">\${obj.downCg_name}</option>`
					}
					$('#downCg_code').html(str);
				},
				error:function(err){ //
					alert('error : ' + err.status);
				}
			});
		}) // onchange -------------
	}) // $(function) end-----
</script>
<h2 class="text-center">상품 등록 - Product Form</h2>
<div class="row">
<div class="col-md-10 offset-1">
	<form name="pf" id="pf" action="prodInsert" method="post" enctype="multipart/form-data">
	<!-- enctype="multipart/form-data"> -->
		<table class="table">
			<tr>
				<th width="20%">카테고리</th>
				<td width="80%">
					<select name="upCg_code" id="upCg_code">
						<option value="">::상위 카테고리::</option>
						<c:forEach var="up" items="${upCgList }">
							<option value="${up.upCg_code}">${up.upCg_name}</option>
						</c:forEach>
					</select>
					<!-- 하위 카테고리 -->
					<span id="downCg">
					<select name="downCg_code" id="downCg_code">
						<!-- ajax로 하위 카테고리 받아오기 -->						
					</select>
					</span>
				</td>
			</tr>
			<tr>
				<td width="20%"><b>상품명</b></td>
				<td width="80%">
				<input type="text" name="pname" id="pname" class="form-control">
				</td>
			</tr>
			<tr>
				<td width="20%"><b>제조사</b></td>
				<td width="80%">
				<input type="text" name="pcompany" id="pcompany" class="form-control">
				</td>
			</tr>
			<tr>
				<td width="20%">
					<b>상품 스펙</b>
				</td>
				<td width="80%">
				<select name="pspec" id="pspec">
					<option value="NEW" selected>NEW</option>
					<option value="HIT">HIT</option>
					<option value="BEST">BEST</option>
				</select>
				
				</td>
			</tr>
			<tr>
				<td width="20%">
				<b>상품 이미지</b>
				</td>
				<td width="80%">
				<!-- db, vo :pimage1, pimage2, pimage3 (String)
					 파일 업로드시 -> MultipartFile타입으로 받아야 함
					 input name을 VO의 property명과 다르게 주기. input name을 pimage로
					 @RequestParam(name="pimage") List<MultipartFile> pimage
				-->
					<input type="file" name="pimage" accept="images/*"  class="form-control"><br>
					<input type="file" name="pimage" accept="images/*"  class="form-control"><br>
					<input type="file" name="pimage" accept="images/*"  class="form-control"><br>				
				</td>
			</tr>
			<tr>
				<td width="20%">
				<b>상품 수량</b>
				</td>
				<td width="80%">
					<input type="number" name="pqty" id="pqty" min="1" max="100"
					 class="form-control">
				</td>
			</tr>
			<tr>
				<td width="20%">
				<b>상품 정가</b>
				</td>
				<td width="80%">
					<input type="number" name="price" id="price" min="0"  required
					 class="form-control">
				</td>
			</tr>
			<tr>
				<td width="20%">
				<b>상품 판매가</b>
				</td>
				<td width="80%">
					<input type="number" name="saleprice" id="saleprice"  min="0"
					 required 
					class="form-control">
				</td>
			</tr>
			<tr>
				<td width="20%">
				<b>상품 설명</b>
				</td>
				<td width="80%">
					<textarea name="pcontents" id="pcontent"
					 rows="5" cols="60" class="form-control"></textarea>
				</td>
			</tr>
			<tr>
				<td width="20%">
				<b>상품 포인트</b>
				</td>
				<td width="80%">
					<input type="number" name="point" id="point"
					 min="0" required
					 class="form-control">
				</td>
			</tr>
			<tr>
				<td colspan="2" class="text-center">
					<button class="btn btn-outline-primary">상품등록</button>				
					<button class="btn btn-outline-danger">다시쓰기</button>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
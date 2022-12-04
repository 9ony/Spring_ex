<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/top" />
<script>
	//prod list에서 edit버튼을 통해 prodForm에 접근했을때
	$(function(){
		if(${flag==false}){
			//alert(${flag});
			$('#prodF[action^="prodInsert"]').prop('action','prodUpdate/${prod.pnum}');
			$('#title').html('상품 수정 [Admin Page]');
			$('#pname').val('${prod.pname}');
			$('#pcompany').val('${prod.pcompany}');
			$('#pspec>option[value^="${prod.pspec}"]').prop('selected',true);
			$('#pqty').val(${prod.pqty});
			$('#price').val(${prod.price});
			$('#saleprice').val(${prod.saleprice});
			$('#pcontents').val('${prod.pcontents}');
			$('#point').val(${prod.point});
			$('#editBtn').html("상품수정");
			$('#upCg_code>option[value=${prod.upCg_code}]').prop('selected',true); //상위카테고리
			selectDownCategory(${prod.upCg_code}); //하위카테고리 출력 함수 실행
			setTimeout(()=> $('#downCg_code>option[value=${prod.downCg_code}]').prop('selected',true),500);
			//$('#downCg_code>option[value=${prod.downCg_code}]').prop('selected',true); //하위카테고리
			
		}
	});
	//상위카테고리를받아 하위카테고리를 출력
	function selectDownCategory(upCode){
		$.ajax({
			url:'getDownCategory',
			data:'upCode='+upCode,
			type:'post',
			dataType : 'json',
			cache : false
		}).done(function(res) {
			/* alert(JSON.stringify(res));
			alert(res.length);
			alert(res[0].downCg_code+"/"+res[0].downCg_name); */
			let str = "<select id='downCg_code' name='downCg_code'>";
				str+="<option value=''>::하위카테고리::</option>"
				for(var i=0;i<res.length;i++){
					//str+= "<option value='"res[i].downCg_code"'>"res[i].downCg_name"</option>";
					str+= "<option value='";
					str+= res[i].downCg_code;
					str+= "'>";
					str+= res[i].downCg_name;
					str+= "</option>";
				}
				str+="</select>";
			$('#selectDcg').html(str);
		}).fail(function(err) {
			alert('err');
		});
		
	}
	//상품등록 유효성 체크
	function prod_check(){
		if(!$('#upCg_code').val()){
			alert('상위 카테고리를 선택하세요');
			$('#upCg_code').focus();
			return false;
		}		
		if(!$('#downCg_code').val()){
			alert('하위 카테고리를 선택하세요');
			$('#downCg_code').focus();
			return false;
		}
		if(!$('#pname').val()){
			alert('상품명을 입력하세요');
			$('#pnum').focus();
			return false;
		}
		
		let $price=$('#price').val();
		let pattern=/^[0-9]+$/;
		if(!pattern.test($price)){
			alert('정가는 숫자로 입력해야 해요');
			$('#price').select();
			return false;
		}
		if(!pattern.test($('#saleprice').val())){
			alert('판매가는 숫자로 입력해야 해요');
			$('#saleprice').select();
			return false;
		}
		if(!pattern.test($('#pqty').val())){
			alert('수량 숫자로 입력해야 해요');
			$('#pqty').select();
			return false;
		}
		if(!pattern.test($('#point').val())){
			alert('포인트 숫자로 입력해야 해요');
			$('#point').select();
			return false;
		}
	}
</script>
<div class="py-5">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<h1 class="text-center" id="title">상품 등록[Admin Page]</h1>

				<form name="prodF" id="prodF" action="prodInsert" method="post" 
				enctype="multipart/form-data" onsubmit="return prod_check()">
					<!-- 파일업로드시: enctype="multipart/form-data"-->
					<table class="table table-condensed table-bordered mt-4">
						<thead>
							<tr>
								<th colspan="2" class="text-center">
									<h3>:::Product Register:::</h3>
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td width="20%"><b>카테고리</b></td>
								<td width="80%">
								<select name="upCg_code" id="upCg_code"
									onchange="selectDownCategory(this.value)">
										<option value="">::상위 카테고리::</option>
										<c:forEach var="up" items="${upCgList }">
										<option value="${up.upCg_code }">${up.upCg_name}</option>
										</c:forEach>
								</select> 
								<span id="selectDcg"></span>
								</td>
							</tr>

							<tr>
								<td width="20%"><b>상품명</b></td>
								<td width="80%"><input type="text" name="pname" id="pname">
									<span style="color: red"> </span></td>
							</tr>
							<tr>
								<td width="20%"><b>제조사</b></td>
								<td width="80%"><input type="text" name="pcompany"
									id="pcompany"></td>
							</tr>
							<tr>
								<td width="20%"><b>상품스펙</b></td>
								<td width="80%"><select name="pspec" id="pspec">
										<option value="NEW" selected>NEW</option>
										<option value="HIT">HIT</option>
										<option value="BEST">BEST</option>
								</select></td>
							</tr>
							<tr>
								<td width="20%"><b>상품이미지</b></td>
								<td width="80%">
								<input type="file" name="pimage"><br> 
								<input type="file" name="pimage"><br>
								<input type="file" name="pimage"><br>
								</td>
							</tr>

							<tr>
								<td width="20%"><b>상품수량</b></td>
								<td width="80%"><input type="number" name="pqty" id="pqty">
									개 <span style="color: red"> </span></td>

							</tr>
							<tr>
								<td width="20%"><b>상품정가</b></td>
								<td width="80%">
									<input type="text" name="price" id="price">
									원 <span style="color: red"> </span>
								</td>
							</tr>
							<tr>
								<td width="20%"><b>상품판매가</b></td>
								<td width="80%">
									<input type="text" name="saleprice" id="saleprice"> 원 
									<span style="color: red"> </span>
								</td>
							</tr>
							<tr>
								<td width="20%"><b>상품설명</b></td>
								<td width="80%">
								<textarea name="pcontents" id="pcontents" rows="5" cols="60"></textarea>
								</td>
							</tr>
							<tr>
								<td width="20%"><b>포인트</b></td>
								<td width="80%">
								<input type="number" name="point" id="point"> 
								POINT</td>
							</tr>
							<tr>
								<td colspan="2" class="text-center">
									<button class="btn btn-outline-success" id="editBtn">상품등록</button>
									<button type="button" class="btn btn-outline-success" onclick="javascript:history.back()">취소</button>
								</td>
							</tr>
						</tbody>
					</table>
				</form>

			</div>
		</div>
	</div>
</div>

	<c:import url="/foot" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/top"/>
<script type="text/javascript" src="./../js/userCheck.js"></script>
<style>
	tr>td:nth-child(1){
		text-align:center;
	}
</style>
<div class="container">
	<h1 class="text-center mt-3">마이페이지</h1>
	<table class="table mt-5" style="width:50%; margin:auto;">
		<tr>
			<td><b>이름</b></td>
			<td>${loginUser.name }</td>
		</tr>
		<tr>
			<td><b>아이디</b></td>
			<td>${loginUser.userid }</td>
		</tr>
		<tr>
			<td><b>전화번호</b></td>
			<td>${loginUser.getAllHp() }</td>
		</tr>
		<tr>
			<td><b>주소</b></td>
			<td>${loginUser.getAllAddr() }</td>
		</tr>
		<tr>
			<td><b>나의 회원상태</b></td>
			<td>${loginUser.statusStr }</td>
		</tr>
		<tr>
			<td><b>나의 마일리지</b></td>
			<td>${loginUser.mileage }</td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" onclick="modF_open()">회원수정</button>
				<button type="button" onclick="javascript:history.back()">돌아가기</button>
			</td>
		</tr>
	</table>
</div>
<script>
	function modF_open(){
		$('#idx').val(${loginUser.idx});
		$('#name').val('${loginUser.name}');
		$('#pwd').val('${loginUser.pwd}');
		$('#pwd2').val('${loginUser.pwd}');
		$('#hp1').val('${loginUser.hp1}');
		$('#hp2').val('${loginUser.hp2}');
		$('#hp3').val('${loginUser.hp3}');
		$('#post').val('${loginUser.post}');
		$('#addr1').val('${loginUser.addr1}');
		$('#addr2').val('${loginUser.addr2}');
		$('#st').prop('class', 'txt' + ${loginUser.status}).html('${loginUser.statusStr}');
		$('option[value*=' + ${loginUser.status} + ']').prop('selected', true);
		$("#editForm").modal('show');
	}
	function userEdit(bool) {
		if (bool == true) {
			$('#editF').prop('action', 'userEdit');
			$('#editF').submit();
			//test
		}
	}
</script>
<c:import url="/modify"/>
<c:import url="/foot"/>
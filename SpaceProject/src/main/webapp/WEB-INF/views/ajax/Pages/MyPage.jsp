<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	tr>td:nth-child(1){
		width:40%;
		text-align:right;
	}
	tr>td{
		text-align:center;
	}
</style>
<div class="container">
	<h1 class="text-center mt-3">마이페이지</h1>
	<table class="table table-borderless mt-5 " style="width:50%; margin:auto;">
		<tr>
			<td><h5>이름</h5></td>
			<td><b>홍길동</b></td>
			<%-- ${loginUser.uname} --%>
		</tr>
		<tr>
			<td><h5>아이디</h5></td>
			<td><b>hong</b></td>
			<%-- ${loginUser.userid} --%>
		</tr>
		<tr>
			<td><h5>연락처</h5></td>
			<td><b>010-0000-0000</b></td>
			<%-- ${loginUser.hp} --%>
		</tr>
		<tr>
			<td><h5>나의 회원상태</h5></td>
			<td><b>일반회원</b></td>
			<%-- ${loginUser.status} --%>
		</tr>
		<tr>
			<td><h5>가입일</h5></td>
			<td><b>2022-12-19</b></td>
			<%-- ${loginUser.wdate} --%>
		</tr>
		<tr>
			<td><h5>나의 마일리지</h5></td>
			<td><b>1000</b></td>
			<!--${loginUser.mileage}  -->
		</tr>
		<tr>
			<td class="text-center" colspan="2">
				<button type="button" class="btn btn-info" onclick="javascript:sel_menu('MyModify')">회원수정</button>
				<button type="button" class="btn btn-secondary" onclick="javascript:sel_menu('MyModify')">회원탈퇴</button>
				<button type="button" class="btn btn-danger" onclick="javascript:sel_menu('Home')">돌아가기</button>
			</td>
		</tr>
	</table>
</div>

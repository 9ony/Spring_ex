<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	.container{
		width:60%;
		margin:auto;
	}
	#edit_table>tbody>tr>td:nth-child(1) {
		width: 30%;
		text-align:center;
	}
	
	#edit_table>tbody>tr>td:nth-child(2) {
		width: 65%;
	}
	input{
		width:70%;
	}
	input#hp1, input#hp2, input#hp3 {
		width: 30%;
	}
</style>
<div class="container mt-5">
	<h1 class="text-center mb-5">회원정보 수정</h1>
	<form name="mf" id="editF" method="POST">
	    <input type="hidden" id="userid" name="userid">
	    <!-- 업데이트 idx 값 저장-->
	  	<table class="table table-borderless" id="edit_table">
	  		<tr>
	  			<td><h5>이름</h5></td>
	  			<td><input type="text" id="uname" name="uname" value="" placeholder="사용자이름"></td>
		   	</tr>
		   	<tr>
	  			<td><h5>비밀번호</h5></td>
	  			<td><input type="password" id="pwd" name="pwd"  value="" placeholder="비밀번호"> </td>
		   	</tr>
		   	<tr>
	  			<td><h5>비밀번호확인</h5></td>
	  			<td><input type="password" id="pwd2" name="pwd2"  value="" placeholder="비밀번호"> </td>
		   	</tr>
		   	<tr>
	  			<td><h5>전화번호</h5></td>
	  			<td>
	   			<input type="text" id="hp1" name="hp1" value="" placeholder="ex)010,011..." maxlength="3">-
	   			<input type="text" id="hp2" name="hp2" value="" placeholder="3~4자리" maxlength="4">-
	   			<input type="text" id="hp3" name="hp3" value="" placeholder="4자리" maxlength="4">
	  			</td>
		   	</tr>
		   	<!-- <tr>
	  			<td>회원상태</td>
	  			<td>
	  				<h3 id="st"></h3>
	  				<select id="status" name="status">
	  					<option value="-2">탈퇴처리</option>
	  					<option value="-1">정지처리</option>
	  					<option value="0">일반회원</option>          					
	  				</select>
	  			</td>
	   		</tr> -->
	  	</table>
	</form>
	<div class="modify-btn text-center" >
          <button type="button" class="btn btn-info" onclick="">수정완료</button>
          <button type="button" class="btn btn-danger" onclick="sel_menu('MyPage')">취소</button>
    </div>
</div>
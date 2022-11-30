<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" id="editForm" style="display:none;" aria-hidden="ture">
    <div class="modal-dialog">
      <div class="modal-content">
      
        <!-- Header -->
        <div class="modal-header">
          <h4 class="text-center">회원 수정</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 id="udapteid"></h4>
        </div>
        
        <!-- body -->
        <div class="modal-body">
          <form name="mf" id="editF" method="POST">
            <input type="hidden" id="idx" name="idx">
            <!-- 업데이트 idx 값 저장-->
          	<table class="table" id="edit_table">
          		<tr>
          			<td>이름</td>
          			<td><input type="text" id="name" name="name" value="" placeholder="사용자이름"></td>
	          	</tr>
	          	<tr>
          			<td>비밀번호</td>
          			<td><input type="password" id="pwd" name="pwd"  value="" placeholder="비밀번호"> </td>
	          	</tr>
	          	<tr>
          			<td>비밀번호확인</td>
          			<td><input type="password" id="pwd2" name="pwd2"  value="" placeholder="비밀번호"> </td>
	          	</tr>
	          	<tr>
          			<td>전화번호</td>
          			<td>
	          			<input type="text" id="hp1" name="hp1" value="" placeholder="ex)010,011..." maxlength="3">-
	          			<input type="text" id="hp2" name="hp2" value="" placeholder="3~4자리" maxlength="4">-
	          			<input type="text" id="hp3" name="hp3" value="" placeholder="4자리" maxlength="4">
          			</td>
	          	</tr>
	          	<tr>
          			<td>우편번호</td>
          			<td>
          				<input type="text" id="post" name="post" value="" placeholder="우편번호">
          				<button type="button" class="btn btn-success" onclick="postfind()">우편번호 찾기</button>
          			</td>
	          	</tr>
	          	<tr>
          			<td>주소</td>
          			<td><input type="text" id="addr1" name="addr1" value="" placeholder="주소"> </td>
	          	</tr>
	          	<tr>
          			<td>상세주소</td>
          			<td><input type="text" id="addr2" name="addr2" value="" placeholder="상세주소"> </td>
	          	</tr>
	          	<tr>
          			<td>회원상태</td>
          			<td>
          				<h3 id="st"></h3>
          				<select id="status" name="status">
          					<option value="-2">탈퇴처리</option>
          					<option value="-1">정지처리</option>
          					<option value="0">일반회원</option>          					
          				</select>
          			</td>
	          	</tr>
          	</table>
           </form>
        </div>
        
        <!-- footer -->
        <div class="modal-footer" >
          <button type="button" class="btn btn-danger" onclick="userEdit(modify_check())">수정완료</button>
          <button type="button" class="btn btn-danger" data-dismiss="modal">닫기</button>
        </div>
        
      </div>
    </div>
</div>
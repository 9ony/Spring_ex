<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- The Modal -->
<script type="text/javascript" src="./js/userCheck.js"></script>
<div class="modal" id="loginModal">
  <div class="modal-dialog modal-sm modal-dialog-centered">
    <div class="modal-content">
		<form name="lgf" method="post"
			action='${pageContext.request.contextPath}/login'>
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">Login</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body">
	        <div class="form-group">
	        	<label for="userid">아이디</label>
	        	<input type="text" id="userid" name="userid">
	        </div>
	        <div class="form-group">
	        	<label for="pwd">아이디</label>
	        	<input type="password" id="pwd" name="pwd">
	        </div>
	      </div>
	
	      <!-- Modal footer -->
	      <div class="modal-footer">
	        <button type="button" class="btn btn-danger"onclick="login_check()">로그인</button>
	        <button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
	      </div>
		</form>
    </div>
  </div>
</div>

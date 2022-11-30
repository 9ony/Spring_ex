<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/top"/>
<script type="text/javascript" src="./js/userCheck.js"></script>
<div class="container mt-3" style="width:40%;">
	<h1 class="text-center">login</h1>
	<form id="lgf" action="login" method="post">
	<table class="table text-center mt-3" >
		<tr>
			<td>아이디</td>
			<td><input type="text" id="userid" name="userid"></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" id="pwd" name="pwd"></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" onclick="login_check()">로그인</button>
				<button type="button" onclick="javascript:location.href='./join'">회원가입</button>
				<button type="button" onclick="javascript:history.back()">취소</button>
			</td>
		</tr>
	</table>
	</form>
</div>
<c:import url="/foot"/>
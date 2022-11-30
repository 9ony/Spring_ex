<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
	<h1>회원인증을 받은 사람만 볼수 있어요</h1>
	<br><br>
	<h3>이름 : ${loginUser.name }</h3>
	<h3>아이디 : ${loginUser.userid }</h3>
	<h3>연락처: ${loginUser.allHp}</h2>
	<h3>주소: ${loginUser.allAddr}</h2>
</div>
<%-- <jsp:include page="/foot.jsp"/> --%>
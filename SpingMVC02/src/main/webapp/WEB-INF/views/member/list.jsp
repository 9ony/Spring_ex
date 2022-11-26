<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/top"/>
<style>
	.txt0{
		color:gray;
	}
	.txt-1{
		color:blue;
	}
	.txt-2{
		color:tomato;
	}
</style>
<div class="container mt-3" style="height:600px;overflow: auto;">
	<h1 class="text-center">Users [Admin Page]</h1>
	<div>
		<table class="table table-bordered mt-5">
		<%-- test <p>${userArr }</p> --%>
			<tr>
				<th>번호</th>
				<th>이름</th>
				<th>아이디</th>
				<th>연락처</th>
				<th>회원상태</th>
				<th>수정|삭제</th>
			</tr>
			<!-- 반복 -->
			<c:if test="${userArr eq null or empty userArr }">
			<tr>
				<td colspan="6"><b>데이터가 없습니다</b></td>
			</tr>
			</c:if>
			<c:if test="${userArr ne null and not empty userArr }">
			<c:forEach var="user" items="${userArr}">
			<tr>
				<td>${user.idx }</td>
				<td>${user.name }</td>
				<td>${user.userid }</td>
				<td>${user.getAllHp() }</td>
				<td class="txt${user.status }">${user.statusStr }</td>
				<td>
				<a href="javascript:userEdit('${user.idx }')">수정</a>|
				<a href="#" onclick="userDel('${user.idx}')">삭제</a></td>
			</tr>
			</c:forEach>
			</c:if>
			<!-- 반복끝 -->
		</table>
	</div>
</div>
<form name="frm" id="frm" method="POST">
	<input type="text" name="idx" id="idx">
</form>
<script>
	function userDel(vidx){
		//alert(vidx);
		//attr():정적인 속성을 추가 , prop():기능적인 속성을 추가할때
		$('#idx').val(vidx);
		$('#frm').prop('action','userDel');
		$('#frm').submit();
	}
	function userEdit(vidx){
		//alert(vidx);
		$('#idx').val(vidx);
		$('#frm').prop('action','userEdit');
		$('#frm').submit();
	}
</script>
<c:import url="/foot"/>
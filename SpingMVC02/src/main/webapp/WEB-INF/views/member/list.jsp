<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/top" />
<script type="text/javascript" src="./../js/userCheck.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<style>
.txt0 {
	color: gray;
}

.txt-1 {
	color: blue;
}

.txt-2 {
	color: tomato;
}

#edit_table>tbody>tr>td:nth-child(1) {
	width: 25%;
}

#edit_table>tbody>tr>td:nth-child(2) {
	width: 65%;
}
input{
	width:90%;
}
input#hp1, input#hp2, input#hp3 {
	width: 30%;
}
</style>
<div class="container mt-3">
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
							<a href="#" onclick="userSel('${user.idx}'); return false;">수정</a>|
						 	<a href="#" onclick="userDel('${user.idx}')">삭제</a>
						 </td>
					</tr>
				</c:forEach>
			</c:if>
			<!-- 반복끝 -->
		</table>
	</div>
</div>
<!-- <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editForm">modal</button> //test -->
<form name="frm" id="frm" method="POST">
	<input type="hidden" name="didx" id="idx">
</form>
<c:import url="/modify"/>
<script>
	function userDel(vidx) {
		alert(vidx);
		//attr():정적인 속성을 추가 , prop():기능적인 속성을 추가할때
		$('#didx').val(vidx);
		$('#frm').prop('action', 'userDel');
		$('#frm').submit();
	}
	function userEdit(bool) {
		if (bool == true) {
			/*input태그 name속성값 = value값; 으로 VO객체 생성되서 보내짐 */
			$('#editF').prop('action', 'userEdit');
			$('#editF').submit();
		}
	}
	function userSel(vidx) {
		$.ajax({
			type : 'POST',
			url : 'getUser',
			data : 'idx=' + vidx,
			dataType : 'json',
			cache : false
		}).done(function(res) {
			$('#idx').val(res.idx);
			$('#name').val(res.name);
			$('#pwd').val(res.pwd);
			$('#pwd2').val(res.pwd);
			$('#hp1').val(res.hp1);
			$('#hp2').val(res.hp2);
			$('#hp3').val(res.hp3);
			$('#post').val(res.post);
			$('#addr1').val(res.addr1);
			$('#addr2').val(res.addr2);
			$('#st').prop('class', 'txt' + res.status).html(res.statusStr);
			$('option[value*=' + res.status + ']').prop('selected', true);
			/* prop으로 css에맞춰 클래스태그 추가 및 selected 설정*/
			$("#editForm").modal('show');
			/* 
			$('a[onclick^="userSel"]').attr("data-toggle", "modal");
			$('a[onclick^="userSel"]').attr("data-target", "#editForm"); 
			jquery 로도 modal block / none처리 가능
			근데 이렇게 할경우 새로고침하면 첫 수정버튼누를시 한번더눌러야됨
			페이지가 첫로드됫을때 위에 속성이 부여되어있지 않아서 a tag에 직접 추가해줌
			*/
			//a태그 속성선택 userSel 클릭시 #editFrom이 class가 modal fage show로 설정되어 보여짐 
			//	기본 modal hide설정
			//https://www.w3schools.com/bootstrap4/bootstrap_modal.asp 예제 참조함
		}).fail(function(err) {
			$("#editForm").modal('hide');
			/* modal('show')로 오픈하니까 hide도 된다. */
			alert('err');
			//location.reload();
			//새로고침으로 modal 닫기 (임시방편)
		});
	}
</script>
<c:import url="/foot" />
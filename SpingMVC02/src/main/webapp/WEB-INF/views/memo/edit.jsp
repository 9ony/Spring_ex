<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메모 수정</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
	function check(){
		/* window.document << 는 생략해도됨 html구조 보려고 한것 */
		if(window.document.frm.name.value==""){
			alert('이름을 입력하세요');
			frm.name.focus();
			return;
		}
		if(!frm.msg.value){
			alert('메모내용을 입력하세요')
			frm.msg.focus();
			return;
		}
		if(frm.msg.value.length>100){
			alert('메모 내용은 100자까지만 가능해요');
			frm.msg.select();
			return;
		}
		frm.submit();
	}
</script>
</head>
<body>
<div id="wrap" class="container py-5">
<c:if test="${memoVO eq null}">
	<script>
		alert('없는 글입니다')
		histroy.back();
	</script>
</c:if>
<c:if test="${memoVO ne null}">
	<form name="frm" action="memoEdit" method="post">
		<table class="table mt-3">
			<tr class="table-info text-center">
				<th colspan="2"> <h1>:: 메모 수정::</h1></th>
			</tr>
			<tr>
				<td width="20%"><b>번호</b></td>
				<td width="40%">
					<input type="text" name="idx" value="${memoVO.idx }" readonly placeholder="idx"
					class="from-control"> 
					
				</td>
			</tr>
			<tr>
				<td width="20%"><b>작성자</b></td>
				<td width="80%">
					<input type="text" name="name" value="${memoVO.name }" class="from-control"> 
				</td>
			</tr>
			<tr>
				<td width="20%"><b>메모내용</b></td>
				<td width="80%">
					<input type="text" name="msg" value="${memoVO.msg }" class="from-control"> 
				</td>
			</tr>
			<tr>
				<td colspan="2" class="text-center">
					<button type="button" onclick="check()" class="btn btn-primary">글 수정</button>
					<!-- 버튼타입을 button으로 변경
						이유 : check함수에 return을 넣어도 submit을 수행하기 때문에
							  함수 check()에서 서브밋을 수행하자!
					 -->
					 <button type="reset" class="btn btn-warning">다시쓰기</button>
					<button type="button" onclick="location.href='memoList'" class="btn btn-danger">수정취소</button>
				</td>
			</tr>
		</table>
	</form>
</c:if>
</div>
</body>
</html>
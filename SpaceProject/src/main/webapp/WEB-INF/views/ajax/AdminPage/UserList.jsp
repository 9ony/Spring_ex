<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<div class="container" id="userlist">
	<h1 class="text-center">회원목록</h1>
	<div id="container-userlist">
	</div>
	</div>
<script>
	window.onload= function(){
		$.ajax({
			type:'get',
			url:'userlist',
			contentType:'application/json',
	    	cache:false,
	    	success :function (res){
	    		alert(JSON.stringify(res));
	    		if(res==""||res==null){ //res값이 없을시
	    			alert("검색결과가 없습니다.");
	    		}else{
	    		  showUserList(res);
	    		}
	    	},
	    	error: function (err){
	    		alert("error"+err.status) //
	    	}
    	  });
	}
	const showUserList = function(res){
		let str=`
			<table class="table table-bordered mt-3">
			<tr>
				<th>이름</th>
				<th>닉네임</th>
				<th>아이디</th>
				<th>생년월일</th>
				<th>가입일자</th>
				<th>연락처</th>
				<th>회원상태</th>
				<th>누적포인트</th>
				<th>포인트사용량</th>
				<th>회원등급</th>
			</tr>`
			$.each(res,function(i,member){
			str+=`<tr>
					<td>\${member.mname}</td>
					<td>\${member.nickname}</td>
					<td>\${member.userid}</td>
					<td>\${member.birth}</td>
					<td>\${member.mdate}</td>
					<td>\${member.hp}</td>
					<td class="txt${member.status}">\${member.statusStr}</td>
					<td>\${member.pointadd}</td>
					<td>\${member.pointadd - member.point}</td>
					<td>\${member.mrank}</td>
				</tr>`
			});
			str+=`</table>`;
		$("#container-userlist").html(str);
	}
</script>

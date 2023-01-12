<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/views/ajax/AdminPage/AdminPageTop.jsp" %>
<c:set var="myctx" value="${pageContext.request.contextPath }"/>
<style>
.spacelist-filter-bar{
		display:flex;
		flex-wrap:wrap;
		justify-content: flex-start;
	}
.spacelist-filter-item{
	padding:2px;
	width:32%;
}
.dropdown-menu{
	padding:5px;
}
</style>
<div class="container mt-3" id="spacelist">
<h1 class="text-center">등록된 공간 목록</h1>
		<form method="post" id="searchf" name="searchf">
				<div class="spacelist-filter-bar">
				<div class="spacelist-filter-item">
					<label>지역</label>
					<button id="FmageSetbtn" type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
					    지  역
					</button>
					<div class="dropdown-menu" id="Fsage">
					    <a class="dropdown-item" href="#">서울</a>
					    <a class="dropdown-item" href="#">부산</a>
					    <a class="dropdown-item" href="#">경기도</a>
					    <a class="dropdown-item" href="#">...</a>
					</div>
				</div>
				<div class="spacelist-filter-item">
					<label>인원</label>
					<button id="FmrankSetbtn" type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
					    인  원
					</button>
					<div class="dropdown-menu" id="Fmrank">
					    <input type="number" id="minFmrank" name="minFmrank" placeholder="최소등급" onchange="btntextSet('Fmrank')">~
					    <input type="number" id="maxFmrank" name="maxFmrank" placeholder="최대등급" onchange="btntextSet('Fmrank')">
					    <p>검색할 인원 범위를 입력해주세요.</p>
					    <button class="btn btn-sm btn-primary" type="button" onclick="btntextSet('Fmrank')">설정</button>
					</div>
				</div>
				<div class="spacelist-filter-item">
					<label>태그</label>
					<button id="FmdateSetbtn" type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
					    태그
					</button>
					<div class="dropdown-menu" id="Fmdate">
						<c:forEach var="hashtag" items="${hashtag }">
							<a class="dropdown-item" href="#">${hashtag.h_name }</a>
						</c:forEach>
					</div>
				</div>
				<div class="spacelist-filter-item">
					<label>기본비용</label>
					<button id="FpointAddSetbtn" type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
					    기본비용
					</button>
					<div class="dropdown-menu" id="FpointAdd">
					    <input type="number" id="minFpointAdd" name="minFpointAdd" placeholder="최소 기본비용" onchange="btntextSet('FpointAdd')">~
					    <input type="number" id="maxFpointAdd" name="maxFpointAdd" placeholder="최대 기본비용" onchange="btntextSet('FpointAdd')">
					    <p>검색할 기본비용 범위를 입력해주세요.</p>
					    <button class="btn btn-sm btn-primary" type="button" onclick="btntextSet('FpointAdd')">설정</button>
					</div>
				</div>
				<div class="spacelist-filter-item">
					<label>추가비용</label>
					<button id="FrcountSetbtn" type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
					    추가비용
					</button>
					<div class="dropdown-menu" id="Frcount">
					    <input type="number" id="minFrcount" name="minFrcount" placeholder="최소 추가비용" onchange="btntextSet('Frcount')">~
					    <input type="number" id="maxFrcount" name="maxFrcount" placeholder="최대 추가비용" onchange="btntextSet('Frcount')">
					    <p>검색할 인원당 추가비용 범위를 입력해주세요.</p>
					    <button class="btn btn-sm btn-primary" type="button" onclick="btntextSet('Frcount')">설정</button>
					</div>
				</div>
				<div class="spacelist-filter-item">
					<label>예약횟수</label>
					<button id="FrcountSetbtn" type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
					    예약횟수
					</button>
					<div class="dropdown-menu" id="Frcount">
					    <input type="number" id="minFrcount" name="minFrcount" placeholder="최소 예약횟수" onchange="btntextSet('Frcount')">~
					    <input type="number" id="maxFrcount" name="maxFrcount" placeholder="최대 예약횟수" onchange="btntextSet('Frcount')">
					    <p>검색할 예약횟수 범위를 입력해주세요.</p>
					    <button class="btn btn-sm btn-primary" type="button" onclick="btntextSet('Frcount')">설정</button>
					</div>
				</div>
			</div>
			<div class="spacelist-search-bar text-center m-3">
				<select name="FkeywordOption">
					<option value="Fname">공간명</option>
					<option value="Fuserid">호스트 아이디</option>
				</select>
				<input type="text" id="keyword" name="keyword" style="width:30%">
				<button type="button" onclick="userlist_search_btn()">검색</button>
			</div>
		</form>
		
		<!-- result div -->
		<div id="container-spacelist">
		</div>
	</div>
<script>
	window.onload= function(){
		$.ajax({
			type:'post',
			url:'spacelist',
			contentType:'application/json',
	    	cache:false,
	    	success :function (res){
	    		//alert(JSON.stringify(res));
	    		if(res==""||res==null){ //res값이 없을시
	    			alert("검색결과가 없습니다.");
	    		}else{
	    		  showSpaceList(res);
	    		}
	    	},
	    	error: function (err){
	    		alert("error"+err.status) //
	    	}
    	  });
	}
	const showSpaceList = function(res){
		let str=`
			<table class="table table-bordered mt-3">
			<tr>
				<th>공간명</th>
				<th>호스트</th>
				<th>등록일</th>
				<th>인원</th>
				<th>기본비용</th>
				<th>추가금</th>
				<th>주소</th>
				<th>해시태그</th>
				<th>예약횟수</th>
			</tr>`
			$.each(res,function(i,space){
			str+=`<tr>
					<td>\${space.sname}</td>
					<td>\${space.userid}</td>
					<td>\${space.sdate}</td>
					<td>\${space.minn}~\${space.maxn}</td>
					<td>\${space.bcost}</td>
					<td>\${space.ecost}</td>
					<td>\${space.saddr1}<br>
						\${space.saddr2}
					</td>
					<td>\${space.h_code}</td>
					<td>\${space.rescount}</td>
				</tr>`
			});
			str+=`</table>`;
		$("#container-spacelist").html(str);
	}
</script>
<%@ include file="/WEB-INF/views/ajax/AdminPage/AdminPageFoot.jsp" %>
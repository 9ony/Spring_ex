<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="/top"/>
<style>
#boardBody{
	white-space : nowrap;
	overflow : hidden;
	text-overflow : ellipsis;
	color : red;
}
</style>
<div class="container mt-3">
	<h1 class="text-center">Spring Board</h1>
	<p class="text-center my-4">
		<a href="write">글쓰기</a>|<a href="list">글목록</a>
	</p>
	<!-- 검색폼 시작 -->
	<!--  -->
	<table class="table table-condensed table-striped">
		<thead>
			<tr>
				<th>글번호</th>
				<th>제목</th>
				<th>글쓴이</th>
				<th>날짜</th>
				<th>조회수</th>				
			</tr>
		</thead>
		<tbody id="boardbody">
			<c:if test="${boardArr eq null or empty boardArr }">
				<tr>
					<td colspan="3"><b>데이터가 없습니다</b></td>
				</tr>
			</c:if>
			<c:if test="${boardArr ne null and not empty boardArr}">
				<c:forEach var="board" items="${boardArr }">
				<tr>
					<td>
						<c:out value="${board.num }"/>
					</td>
					<td class="sub">
						<c:forEach var="k" begin="1" end="${board.lev }">
							&nbsp;&nbsp;&nbsp;
						</c:forEach>
						<c:if test="${board.lev>0 }">
							<img src="../images/re.png">
						</c:if>
						<%-- ${board.subject } <<text형태로 출력하는게아니다 script 넣으면 실행시킴 --%>
						<a href="view/<c:out value="${board.num }"/>">
						<c:if test="${fn:length(board.subject)>20}">
							<c:out value="${fn:substring(board.subject,0,25)}"/>...
						</c:if>
						<c:if test="${fn:length(board.subject)<21}">
							<c:out value="${board.subject }"/>
						</c:if>	
							</a>
						<!-- c:out text로만 불러옴  -->
						<c:if test="${board.filesize>0 }">
							<span class="float-right">
							<img src="../images/attach.JPG" style="width:16px" 
							title="<c:out value="${board.originFilename }"/>">
							</span>
						</c:if>
					</td>
					<td><c:out value="${board.name }"/></td>
					<td><c:out value="${board.wdate }"/></td>
					<td><c:out value="${board.readnum }"/></td>
				</tr>
				</c:forEach>
			</c:if>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="3" class="text-center">
				<ul class="pagination justify-content-center">
				<!-- 이전 page -->
				<c:if test="${paging.prevBlock>0 }">
					<li class="page-item">
					<a class='page-link' href="list?cpage=<c:out value="${paging.prevBlock }"/>">◀</a>
					</li>
				</c:if>
				<!-- 페이지개수 -->
				<c:forEach var="i" begin="${paging.prevBlock+1 }" end="${paging.nextBlock-1 }">
					<c:if test="${i <= paging.pageCount }">
					<!-- active를 주면 현재페이지 li 불들어옴 c:if로 조건처리 -->
					<li class="page-item <c:if test='${i eq paging.cpage }'>active</c:if>">
						<a class='page-link' href='list?cpage=<c:out value="${i }"/>'> 
							<c:out value="${i }"/> 
						</a>
					</li>
					</c:if>
				</c:forEach>
				<!-- 다음 page -->
				<c:if test="${paging.nextBlock<paging.pageCount }">
					<li class="page-item">
					<a class='page-link' href="list?cpage=<c:out value="${paging.nextBlock }"/>">▶</a>
					</li>
				</c:if>
				</ul>
				</td>
				<td colspan="2" class="text-right">
				총 게시글 수 : <b></b><c:out value="${paging.totalCount }"/></b><br>
				<span class="text-danger"><c:out value="${paging.cpage }"/></span> /<c:out value="${paging.pageCount }"/>
				</td>
			</tr>
		</tfoot> 
	</table>
</div>
<c:import url="/foot" />
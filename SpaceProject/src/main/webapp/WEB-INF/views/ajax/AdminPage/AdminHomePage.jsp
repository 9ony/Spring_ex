<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/views/Spacetop.jsp" %>
<c:set var="myctx" value="${pageContext.request.contextPath }"/>
<style>
.admincontainer{
	overflow:hidden;
}
#content-nav{
	width:15%;
	margin:2%;
	float:left;
}
#content-wrap{
	width:80%;
	float:left;
}
</style>
<script>
	const sel_menu = function(m_name) {
		fetch(m_name).then(function(response) {
			response.text().then(function(text) {
				$('#content-wrap').html(text);
			})
		})
	}
</script>
<div class="admincontainer mt-5" >
	<div id="content-nav">
		<%@ include file="/WEB-INF/views/ajax/AdminPage/AdminNav.jsp" %>
	</div>
	<div id="content-wrap">
		<%@ include file="/WEB-INF/views/ajax/AdminPage/UserList.jsp" %>
		<!-- <script>
			fetch('userlistform').then(function(response) {
				response.text().then(function(text) {
					document.getElementById("content-wrap").innerHTML = text;
				})
			})
		</script> -->
	</div>
</div>
<%@ include file="/WEB-INF/views/Spacefoot.jsp" %>
<%-- <c:import url="/Spacefoot" charEncoding="utf-8"/> --%>
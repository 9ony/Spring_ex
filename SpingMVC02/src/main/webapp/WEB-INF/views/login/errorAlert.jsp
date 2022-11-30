<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%-- isErrorPage="ture" 에러페이지로 이용--%>
<%
	response.setStatus(200);
%>


<script>
	alert('<%=exception.getMessage() %>');
	history.back();
</script>
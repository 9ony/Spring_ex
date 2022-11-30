<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="/top"/>

<div class="container mt-3" style="height:600px;overflow: auto;">
	<h1 class="text-center">파일 업로드 결과</h1>
	<div class="text-center mt-5">
	<h2>올 린 이 :  ${name }</h2>
	<h2>파일 이름 : ${fname }</h2>
	<h2>파일 크기 : ${fsize } Byte</h2>
	<h2>파일 경로 : ${fpath }</h2>
	
	</div>
	<img src="${fpath }/${fname }" style="width:200px;height:200px"/>
</div>
<c:import url="/foot" />

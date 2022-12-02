<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="/top"/>
<script>
	const openPop=function(img){
		//alert(img);
		$("#popupForm").modal('show'); //modal처리
		//let url='resources/product_images/'+img;
		
		//window.open(url,'imgView','width=400px , height=400px , left=100px, top=100px');
	}
	
	const goCart=function(){
		//pnum,oqty가 파라미터 넘어감
		frm.action="user/cartAdd";
		frm.method='post';
		frm.submit();
	}
	
	const goOrder=function(){
		frm.action="user/order";
		frm.method='get';
		frm.submit();
	}
	
	const goWish=function(){
		frm.action="user/wishAdd";
		frm.method='get';
		frm.submit();
	}
</script>
<div class="container mt-3">
	<div class="container" style="text-align:center">
		<div class="row">
         <div class="col-md-12">
         	<%-- <input type="text" id="loginuserid" value="${loginUser.userid}"> --%>
            <table class="table">
               <thead>
                  <tr>
                     <th colspan="2"><h3 style="text-align:center">상품 정보</h3></th>
                  </tr>
               </thead>

               <tbody>
                  <tr>
                     <td align="center" width="50%">
                     	<c:if test="${prod.pimage1 eq null }">
				         <a href="#" onclick="openPop('noimage.png')"> 
	                     		<img src="resources/product_images/noimage.png"  
	                     			class="img-fluid" style="width: 70%;">
	                     </a>
				        </c:if>
				        <c:if test="${prod.pimage1 ne null }">
                     	<a href="#" onclick="openPop('${prod.pimage1}')"> 
	                     		<img src="resources/product_images/${prod.pimage1}"  
	                     			class="img-fluid" style="width: 70%;">
	                     </a>
                        </c:if>
                     	
                     </td>
					<c:import url="/openPop">
						<c:param name="img" value="${prod.pimage1}"/>
					</c:import>
                     <td align="left" width="50%" style="padding-left: 40px">
                        <h4>
                           <span class="label label-danger"> ${prod.pspec} </span>
                        </h4> 
                        	상품번호: ${prod.pnum} <br> 
                        	상품이름: ${prod.pname} <br> 
                        	정가:
                        	<del>
                            <fmt:formatNumber value="${prod.price}" pattern="###,###" />
                        	</del>원
                        	<br> 
                        	판매가:<span style="color: red; font-weight: bold">
                           <fmt:formatNumber value="${prod.saleprice}" pattern="###,###" />
                     			</span>원<br> 
                     	    할인율:<span style="color: red">${prod.percent} %</span><br>

                        POINT:<b style="color: green">[${prod.point}]</b>POINT<br>

                        <!-- form시작---------- -->
                        <form name="frm" id="frm" method="POST">
                           <!-- 상품번호를 hidden으로 넘기자------ -->
                           <input type="hidden" name="pnum" value="${prod.pnum}">
                           <input type="hidden" name="opnum" value="${prod.pnum}">
                           <!-- -------------------------------- -->
                           <label for="oqty">상품갯수</label> 
                           <input type="number" name="oqty"
                              id="oqty" min="1" max="50" size="2" value="${prod.pqty}">

                        </form> <!-- form end------------ -->

                        <button type="button" onclick="goCart()" class="btn btn-primary">장바구니</button>
                        <button type="button" onclick="goOrder()"
                           class="btn btn-warning">주문하기</button>
                        <button type="button" onclick="goWish()" class="btn btn-danger">위시리시트</button>
                     </td>

                  </tr>
                  <tr style="border: 0">
                     <td align="center">
                     	<c:if test="${prod.pimage2 eq null }">
				         <img src = "resources/product_images/noimage.png"
				         	class="img img-thumbnail" style="width: 70%;">
				        </c:if>
				        <c:if test="${prod.pimage2 ne null }">
                     	<img src="resources/product_images/${prod.pimage2}"
                        class="img img-thumbnail" style="width: 70%;">
                        </c:if>
                     </td>
                     <td align="center">
                     	<c:if test="${prod.pimage3 eq null }">
				         <img src = "resources/product_images/noimage.png"
				         	class="img img-thumbnail" style="width: 70%;">
				        </c:if>
				        <c:if test="${prod.pimage3 ne null }">
                     	<img src="resources/product_images/${prod.pimage3}"
                        class="img img-thumbnail" style="width: 70%;">
                        </c:if>
                     </td>
                  </tr>
                  <tr>
                     <td colspan="2">
                        <p>상품설명</p> 
                        <pre>${prod.pcontents}</pre>
                     </td>
                  </tr>
               </tbody>
            </table>
         </div>
      </div>
	</div><!-- row end -->
	<!-- 글쓰기 폼 -->
	<div class="row mt-4">
         <div class="col-md-10 offset-md-1">
               <c:import url="/reviewForm"/>
         </div>
    </div>
    <div class="row">
         <div class="col-md-10 offset-md-1 text-center" id="reviewTitle">
         	<h4>Review List <span class="badge badge-success" id="review_cnt">7</span></h4>
         </div>
    </div>
    <!-- 리뷰 목록 -->
    <div class="row">
         <div class="col-md-10 offset-md-1" id="reviewList">
         </div>
    </div>
    <!-- 수정폼 -->
    <c:import url="/reviewEdit"/>
    <!-- 인크루드 방식= include file="/WEB-INF/views/review/reviewEdit.jsp" -->
</div><!-- container end -->
	
<c:import url="/foot" />
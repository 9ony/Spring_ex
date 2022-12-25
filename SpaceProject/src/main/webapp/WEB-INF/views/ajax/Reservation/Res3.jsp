<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.min.js"></script>
<script>
const backHome=function(){
	location.href="이동할페이지명";
}

const cal=function(yy,mm){
	$.ajax({
		type:'get',
		url:'ReservationAjax?year='+yy+'&month='+mm,
		dataType:'html',
		success:function(res){
			$('#bookingCalendar').html("");
			$('#bookingCalendar').html(res);
		},
		error:function(err){
			alert('err: '+err.status);
		}
	})
}

const aa=function(yy,mm,dd){
	//alert(yy+":"+mm+":"+dd)
	$('#check_month').html("");
	$('#check_month').html(mm);
	$('#check_date').html("");
	$('#check_date').html(dd);
}

</script>

<div class="wrap">
	<div class="bookingStep">
		<div class="section_spaceInfo">
			<div class="top_spaceTitle">
			<a class="btn_back" onclick="backHome()" title="이전화면이동"><i aria-hidden="true">←</i></a>
			<h2><a>${svo.sname}</a></h2>
			<h2><a>공간정보(컨셉, 등 필요한거)</a></h2>
			</div>
		</div>
	
	<div class="bookingCalendar" id="bookingCalendar">
		<div class="section_inner">
			<div class="time_title">
				<span class="option_label">일정선택</span>
				<span id="check_month">${today_info.search_month}</span>월 <span id="check_date">${today_info.today}</span>일
			</div>		
			
			<div class="Title_Calendar">
				<a class="before_after_calendar" onclick="cal(${today_info.search_year-1},${today_info.search_month})" title="전년도">&lt;&lt;</a>
				<a class="before_after_calendar" onclick="cal(${today_info.before_year},${today_info.before_month})" title="이전달">prev</a>
				<span class="this_month">&nbsp; ${today_info.search_year}.
					<c:if test="${today_info.search_month<10}">0</c:if>${today_info.search_month}
				</span>
				<a class="before_after_calendar" onclick="cal(${today_info.after_year},${today_info.after_month+1})" title="다음달">next</a>
				<a class="before_after_calendar" onclick="cal(${today_info.search_year+1},${today_info.search_month})" title="명년도">&gt;&gt;</a>
			</div>
			
			<table class="Body_Calendar">
				<thead class="tb_header">
					<tr>
						<th class="calendar_sun">
							<span>SUN</span>
						</th>
						<th>
							<span>MON</span>
						</th>
						<th>
							<span>TUE</span>
						</th>
						<th>
							<span>WED</span>
						</th>
						<th>
							<span>THU</span>
						</th>
						<th>
							<span>FRI</span>
						</th>
						<th class="calendar_sat">
							<span>SAT</span>
						</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<c:forEach var="dList" items="${dateList}" varStatus="date_status">
							<c:choose>
								<c:when test="${dList.value=='today'}">
									<td class="today">
										<div class="date" onclick="aa(${dList.year},${dList.month},${dList.date})">${dList.date}</div>
										<div></div>
									</td>
								</c:when>
								<c:when test="${date_status.index%7==6}">
									<td class="saturday">
										<div class="sat" onclick="aa(${dList.year},${dList.month},${dList.date})">${dList.date}</div>
										<div></div>
									</td>
								</c:when>
								<c:when test="${date_status.index%7==0}">
									<td class="sunday">
										<div class="sun" onclick="aa(${dList.year},${dList.month},${dList.date})">${dList.date}</div>
										<div></div>
									</td>
								</c:when>
								<c:otherwise>
									<td class="normal_day">
										<div class="date" onclick="aa(${dList.year},${dList.month},${dList.date})">${dList.date}</div>
									</td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<div class="time_booking">
		<div class="time_now">여기에 유저가 선택한 날짜 및 시간(~시부터 ~시까지 (몇시간)대여) 다시 한번 띄워주기</div>
		
		<div class="time_controller slick_slider">
			<div class="time_controller_inner">
				예약창 들어가야됨, 시간 선택할수 있는 예약창, 회원정보(이름,전화번호), 인원수선택,
				<!-- <button class="slick_arrow_prev" type="button" onclick="onSlickPrev()">
					<i class="fn-booking fn-bokking-backward1" aria-hidden="true"></i>
					<span class="blind">prev</span>
				</button> -->
				불가한 시간 누르면 선택이 불가능한 시간입니다 띄워주기
			</div>
			<div>여기엔 공간 정보 (한시간에 얼만지, 1인당 얼만지, 기본인원수, 인원추가금액등)</div>
		</div>
		
		<div class="time_controller_pay">
			<div class="price_time">
				<span>시간을 선택하면 자동으로 금액 변경(2시간 선택하면 공간정보금액에서 *2해서 출력)</span>
				<span>원</span>
			</div>
			<div class="price_count">
				<a class="btn_plus_minus spr_book2 ico_minus3">클릭시인원수빼기</a>
				<input><a>여긴 기본인원수가 디폴트로 들어갈꺼임</a>
				<a class="btn_plus_minus spr_book2 ico_plus3 disabled">클릭시인원수증가</a>
			</div>
		</div>
	</div>	
	
	<div class="payment">
		<a>결제</a>
	</div>
	
	</div>
</div>


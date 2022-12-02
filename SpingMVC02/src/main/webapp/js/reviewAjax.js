//reviewForm submit
$(function(){
	//파일 업로드 처리시 ==> FormData갹체에 form data를 담아 보내야 한다(업로드)
	show_reviews();
	$('#rf').submit(function(evt){
		evt.preventDefault(); //rf폼에 submit이벤트를 차단한다. 
		//alert('test');
		const file=$('#mfilename')[0]; //file은 여러개 들어올수 있으므로 기본적으로 배열형태임
		console.dir(file); //file의 세부적인 속성들을 웹 콘솔에 출력해줌
		
		alert("file=="+file);
		
		const fname = file.files[0]; 
		const userid=$('#userid').val();
		const content=$('#content').val();
		const score=$('input[name="score"]:checked').val();
		const pnum_fk=$('#pnum_fk').val();
		
		console.log(userid+"/"+content+"/"+score+"/"+pnum_fk+"/"+fname);
		
		let fd = new FormData();
		if(fname!=undefined){
			fd.append('mfilename',fname);
		}else{
			alert("fname=="+fname);
			fd.append('mfilename',null);
		}
		//fd.append('mfilename',fname); //test
		fd.append('userid',userid);
		fd.append('content',content);
		fd.append('score',score);
		fd.append('pnum_fk',pnum_fk);
		
		let url = "user/reviews";
		/*
			processData:false, //==>기본값은 true
			true면 enctype="application/x-www-form-urlencoded" 타입으로 전송
			contentType:false, //기본값 true
			true면 파일의 이름만가고 데이터는 가지않는다.
			그래서 둘다 false로 설정한다.
		*/
		
		$.ajax({
 		type: 'post',
 		url: url,
 		data:fd, //FormDate객체 전달
 		cache:false,
 		processData:false, //업로드할 파일데이터를 전송할때
 		contentType:false,
 		dataType:'xml',
 		success:function(res){
 			//alert(res);
 			let result=$(res).find('result').text();
 			if(result>0){
 				//$('#reviewList').html("<h1>등록성공</h1>");
 				show_reviews();
 			}else{
 				alert("등록실패");
 			}
 		},
 		error:function(err){
 			alert(err.status);
 		}
 		});
	}); //rf.submit end;
})//$() end

//리뷰목록가져오기 my
const show_reviews=function(){
	let url="reviews"
	$.ajax({
		type:'get',
		url:url,
		data:'pnum='+$('#pnum_fk').val(),
		dataType:'json',
		cache:false,
		success:function(res){
			//alert(JSON.stringify(res));
			showTable(res);
		},
		error:function(err){
			alert('err: ' + err.status);
		}
	});
}

const showTable=function(res){
	let str='<table class="table table-striped">';
	$.each(res,function(i,rvo){
		let d = new Date(rvo.wdate);
		let dstr=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
		
		str+='<tr><td width="15%">';
		if(rvo.filename==null){
		str+='<img src="resources/review_images/noimage.png" class="img-thumbnail" style="width:80%; margin:auto">';
		}else{
		str+='<img src="resources/review_images/'+rvo.filename+'" class="img-thumbnail" style="width:80%; margin:auto">';
		}
		str+='</td><td width="60%" class="text-left">';
		str+=rvo.content+" <span class='float-right'>"+rvo.userid+"["+dstr+"]</span>";
		str+='</td>';
		str+='<td width="25%" class="text-left">';
		for(let k=0;k<rvo.score;k++){
			str+='<img src="resources/review_images/star.jpg">';
		}
		str+='<div class="mt-4">';
		
		str+='<a href="#reviewList" onclick="reviewEdit('+rvo.num+')">수정</a>';
		str+= ' | ';
		str+='<a href="#reviewList" onclick="reviewDel('+rvo.num+')">삭제</a>';
		
		str+='</div>';
		str+='</td>';
		str+='</tr>';
		});
		str+='</table>';

		$('#reviewList').html(str);
}//--------------------------------

const reviewEdit = function(num){
	//alert(num);
	let url='reviews/'+num;
	$.ajax({
		type:'get',
		url:url,
		dataType:'json',
		cache:false,
		success:function(res){
			//alert(JSON.stringify(res));

			rf2.num.value=res.num; //업데이트할 글번호
			rf2.userid.value=res.userid; //사용자 아이디
			rf2.content.value=res.content; // 내용
			rf2.score.value=res.score; //별점
			rf2.pnum_fk.value=res.pnum_fk; //상품번호
			
			//파일은 자바스크립트로 못넣음 (읽기전용)
			
			
			$("#reviewModal").modal();
		},
		error:function(err){
			alert('err');
		}
	});
}


const reviewDel = function(num){
	//alert(num);
	let url="user/reviews/"+num;
	$.ajax({
		type:'delete',
		url:url,
		dataType:'json',
		cache:false,
		success:function(res){
			//alert(JSON.stringify(res));
			if(res.result>0){
				show_reviews();
			}else{
				alert("삭제실패");
			}
		},
		error:function(err){
			alert(err.status);
		}
	
	});
}

//파일 업로드 없는 일반적 form data 전송식
 const send = function(){
 	//alert('send');
 	let params = $('#rf').serialize();
 	alert(params);
 	let url = "user/reviews";
 	$.ajax({
 		type: 'post',
 		url: url,
 		data:params,
 		cache:false,
 		dataType:'xml',
 		success:function(res){
 			//alert(res);
 			let result=$(res).find('result').text();
 			alert(result);
 		},
 		error:function(err){
 			alert(err);
 		}
 	});
 }
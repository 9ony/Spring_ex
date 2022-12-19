<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>::멜론 차트 보기::</title>
<style>
	#wrap{
		padding:3em;
	}
	#melonList{
		width:49.5%;
		float:left;
		/* border:1px solid red; */
	}
	#melonSingerCnt{
		width:48.5%;
		float:right;
		padding-left:1em;
		/* border:1px solid green; */
	}
	#melonSingerCnt ul>li{
		list-style: none;
		float:left;
		height: 30px;
		line-height: 30px;
		margin-bottom:3px;
	}
	#melonSingerCnt ul>li:nth-child(2n+1){
		width:60%;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	#melonSingerCnt ul>li:nth-child(2n+2){
		width:40%;
	}
	
	#melonList ul.melon_chart>li{
		list-style-type:none;
		float:left;		
		height:120px;
		margin-bottom:3px;
	}
	#melonList ul.melon_chart>li:nth-child(3n+1){
		width:10%;
	}
	#melonList ul.melon_chart>li:nth-child(3n+2){
		width:25%;
	}
	#melonList ul.melon_chart>li:nth-child(3n+3){
		width:65%;
	}
</style>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.min.js"></script>
<!-- 구글 차트 라이브러리 cdn참조-------------------- -->
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<!-- ------------------------------------------- -->
<script>
	$(function(){
		$('#btn1').on('click', function(){
			$.ajax({
				type:'get',
				url:'crawling',
				dataType:'xml',
				cache:false,
				success:function(res){
					//alert(res);//XML Document
					let n=$(res).find('result').text()
					//alert(n+"개의 데이터를 저장했습니다");
					$('#melonList').html("<h3>"+n+"개의 데이터를 크롤링했습니다</h3>");
					getMelonList();
				},
				error:function(err){
					alert('err: '+err.status);
				}
			})
		})//#bnt1 click------
		
		$('#btn3').click(function(){
			$.ajax({
				type:'get',
				url:'singerCnt',
				dataType:'json',
				cache:false,
				success:function(res){
					//alert(JSON.stringify(res));
					renderCntBySinger(res);
				},
				error:function(err){
					alert('err: '+err.status);
				}
			})
		})
	})//$() end----------------
	const getMelonList=function(){
		$.ajax({
			type:'get',
			url:'list',
			dataType:'json',
			cache:false,
			success:function(res){
				//alert(res);
				renderMelon(res)
			},
			error:function(err){
				alert('err: '+err.status);
			}
		});
	}//------------------------------------
	
	const renderCntBySinger=function(jsonArr){
		var data=new google.visualization.DataTable();
		data.addColumn('string','Singer');
		data.addColumn('number','Song Count');
		
		let mydata=[];//배열<= 2차원 배열 형태로 넣어주어야 함
		
		let str='<ul class="melon_singer_cnt">';
			$.each(jsonArr, function(i, obj){
				let arr=[];//1차원배열
				arr.push(obj.singer);//가수명
				arr.push(obj.cntBySinger)//노래개수
				//arr=['임영웅',14]
				mydata.push(arr);
				
				console.log("arr="+arr);
				str+='<li>';
				str+=obj.singer;
				str+='</li>';
				str+='<li>';
				str+=obj.cntBySinger;
				str+='</li>';
			})
			console.log("mydata="+mydata);
			data.addRows(mydata);
			str+='</ul>';
			$('#melonSingerCnt').html(str);
			renderBarChart(data);
	}//------------------------------------
	
	const renderBarChart=function(data){
		let option={
				'title':'멜론차트에 오른 가수별 노래수',
				'width':600,
				'height':1000,
				'fontSize':11,
				'fontName':'Verdana',
				'colors':['#669355','#ffaacc']
		}
		let bar_chart=new google.visualization.BarChart(document.getElementById('melonList'));
		bar_chart.draw(data, option);
		
		let pie_chart=new google.visualization.PieChart(document.getElementById('melonSingerCnt'));
		pie_chart.draw(data, option);
	}//-------------------------------------
	
	const renderMelon=function(jsonArr){
		
		//alert(jsonArr.length)
		let str='<ul class="melon_chart">';
		$.each(jsonArr, function(i, obj){
			str+='<li>';
			str+=obj.ranking;
			str+='</li>';
			str+='<li>';
			str+='<img src="'+obj.albumImage+'">'
			str+='</li>';
			str+='<li>';
			str+='<span class="title">'+obj.songTitle+"</span><br>";
			str+='<span class="singer">'+obj.singer+"</span>"
			str+='</li>';
		});
			str+='</ul>';
		$('#melonList').html(str);
	}
</script>
</head>
<body>
<div id="wrap" class="container">
	<div id="menu">
		<h1>오늘의 Melon Chart - ${today} </h1>
		<button id="btn1">멜로 차트 크롤링하기</button>
		<button id="btn2" onclick="getMelonList()">멜론 차트 목록보기</button>
		<button id="btn3">멜론 차트 가수별 노래수 보기</button>
	</div>
	
	<div id="melonList">
		<!-- 여기에 멜론 차트 목록 들어옴 -->
	</div>
	<div id="melonSingerCnt">
		<!--여기에 멜론 가수별 노래수 들어옴  -->
	</div>
</div>
<!-- 구글 차트--------------------------------- -->
<script type="text/javascript">

      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Topping');
        data.addColumn('number', 'Slices');
        data.addRows([
          ['Mushrooms', 3],
          ['Onions', 1],
          ['Olives', 1],
          ['Zucchini', 1],
          ['Pepperoni', 2]
        ]);

        // Set chart options
        var options = {'title':'How Much Pizza I Ate Last Night',
                       'width':400,
                       'height':300};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('melonList'));
        chart.draw(data, options);
        
        var chart2 =new google.visualization.BarChart(document.getElementById('melonSingerCnt'))
        chart2.draw(data,options);
      }
    </script>

</body>
</html>



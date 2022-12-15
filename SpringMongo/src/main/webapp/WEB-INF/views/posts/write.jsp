<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.min.js"></script>
<style>
* {
	list-style: none;
}

#wrap {
	padding: 3em;
}

#frm li {
	padding: 10px 5px;
}

#frm input, textarea, button {
	padding: 7px;
}

#postList ul>li {
	float: left;
	width: 10%;
	height: 40px;
	line-height: 40px;
	border-bottom: 1px solid gray;
	padding: 5px;
}

#postList ul>li:nth-child(4n+1) {
	width: 60%;
}
</style>
<script>
	$(function() {
		//목록가져오기
		showPosts();

		//
		$('#frm button').click(function(e) {
			e.preventDefault();
			let author = $('#author').val();
			let title = $('#title').val();
			alert(author + "/" + title);
			let jsonData = {
				"author" : author,
				"title" : title
			}
			$.ajax({
				type : 'post',
				url : 'posts',
				contentType : 'application/json',
				data : JSON.stringify(jsonData),
				dataType : 'json',
				cache : false,
				success : function(res) {
					//alert(JSON.stringify(res));
					showPosts();
				},
				error : function(err) {
					alert("err" + err.status);
				}
			});
		})
	})
	const showPosts = function() {
		$.ajax({
			type : "get",
			url : 'postsAll',
			dataType : 'json',
			cache : false,
			success : function(res) {
				//alert(JSON.stringify(res));
				renderPosts(res);
			},
			error : function(err) {
				alert('err:' + err.status);
			}
		})
	}//------------------------------
	const renderPosts = function(data) {
		let str = '<ul>';
		$.each(data,
				function(i, post) {
					str += '<li>제목:';
					str += post.title;
					str += '</li>';
					str += '<li>작성자:';
					str += post.author;
					str += '</li>';
					str += '<li>날짜:';
					str += post.wdate;
					str += '</li>';
					str += '<li>';
					str += '<a href="#" onclick="postsEdit(\'' + post.id
							+ '\')">수정</a>';
					str += '<a href="#" onclick="postsDel(\'' + post.id
							+ '\')">삭제</a>';
					str += '</li>';
				})
		str += '</ul>';
		$('#postList').html(str);
	}//------------------------------
	const postsEdit = function(pid) {
		//alert(pid);
		$.ajax({
			type : 'get',
			url : 'postsEdit?id=' + pid,
			dataType : 'json',
			cache : false,
		}).done(function(res) {
			//alert(JSON.stringify(res))
			$('#id').val(pid);
			$('#title').val(res.title);
			$('#author').val(res.author);
			$('#frm button').text('글수정');
		}).fail(function(err) {
			alert('err: ' + err.status);
		})
	}//------------------------------
	const postsDel = function(pid) {
		//alert(pid);
		$('#id').val(pid);
		let data = {
			"id" : pid
		}
		$.ajax({
			type : 'post',
			url : 'postsDel',
			contentType : 'application/json',
			data : JSON.stringify(data),
			cache : false,
			success : function(res) {
				if (res.result == 1) {
					alert("삭제완료");
					showPosts();
				}
			},
			error : function(err) {
				alert('err: ' + err.status);
			}
		})
	}
</script>
<div id="wrap" class="container">
	<h1>Posts 글쓰기</h1>
	<form id="frm" name="frm" action="posts">
		<!-- id값 히든에저장 -->
		<input type="hidden" id="id" name="id" />
		<ul>
			<li>작성자 : <input type="text" id="author" name="author"
				placeholder="작성자" required>
			</li>
			<li><textarea name="title" id="title" placeholder="Title"
					rows="5" cols="70"></textarea></li>
			<li><button>글쓰기</button></li>
		</ul>
	</form>
	<div id="postList">여기에 포스트 목록 들어올 예정</div>
</div>

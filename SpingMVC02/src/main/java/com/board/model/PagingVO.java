package com.board.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import lombok.Data;

/*페이징 처리 및 검색기능 모듈화*/
@Data
public class PagingVO {
	//페이징 처리 관련 프로퍼티
	private int cpage;
	private int pageSize=5;
	private int totalCount;
	private int pageCount;
	
	//DB에서 레코드를 끊어오기 위한 프로퍼티
	private int start;
	private int end;
	
	//페이징 블럭 처리를 위한 프로퍼티
	private int pagingBlock; //한페이지당 보여줄 개수
	private int prevBlock; // 이전으로 넘어갈 페이지개수
	private int nextBlock; // 이후로 넘어갈 페이지 개수
	
	//검색을 위한 프로퍼티
	private String findType; //검색 유형
	private String findKeyword; //검색 키워드
	
	//페이징 처리 초기설정 메서드
	public void init(HttpSession ses) {
		if(ses!=null) {
			ses.setAttribute("pageSize", pageSize);
		}
		pageCount=(totalCount-1)/pageSize+1;
		if(cpage<1) {
			cpage=1;
		}
		if(cpage>pageCount) {
			cpage=pageCount;
		}
		//[1] between
		//int end=cpage*pageSize;
		//int start=end-(pageSize-1);
		
		
		
		//[2] 부등호
		start = (cpage-1)*pageSize;
		end = start+(pageSize+1);
		
		prevBlock = ((cpage-1)/pagingBlock) * pagingBlock;
		nextBlock = prevBlock +(pagingBlock+1);
		//페이징 블럭 연산---
		/*cpage
		 * [1][2][3][4][5] | [6][7][8][9][10] | [11][12][13][14][15] |[16][17][18][19][20]
		 * 
		 * cpage		pagingBlock			prevBlock(이전5개)		nextBlock(이후5개)
		 * 1~5				5					0						6
		 * 6~10									5						11  
		 * 11~15								10						16
		 * 
		 * prevBlock=(cpage-1)/pagingBlock * pagingBlock;
		 * nextBlock= prevBlock+(pagingBlock+1)
		 * */

		
	}
	public String getPageNavi(String myctx,String loc,String userAgent) {
		//myctx = 컨텍스트명
		//loc   = board/list
		//userAgent = 클라이언트 브라우저 파악
		//검색관련--------------
		if(findType==null) {//검색어가 넘어오지 않을 경우
			findType="";
			findKeyword="";			
		}else {
			//브라우저 IE일경우 검색어 한글 처리하기
			if(userAgent.indexOf("MSIE")>-1||userAgent.indexOf("Trident")>-1) {
				try {
				findKeyword=URLEncoder.encode(findKeyword,"UTF-8");
				}catch(UnsupportedEncodingException e) {
					System.out.println(e);
				}
			}
			
		}
		/////////////////////////////
		String link=myctx+"/"+loc; // "/multiweb/board/list"
		String qStr="?pageSize="+pageSize+"&findType="+findType+"&findKeyword="+findKeyword;
		link+=qStr;
		/////////////////////////////
		String str="";
		StringBuilder buf = new StringBuilder();
		buf.append("<ul class='pagination justify-content-center'>");
		if(prevBlock>0) {
			buf.append("<li class='page-item'>")// "/multiweb/board/list?cpage=5"
				.append("<a class='page-link' href='"+link+"&cpage="+prevBlock+"'>")
				.append("Prev")
				.append("</a>")
				.append("</li>");
		}
		for(int i=prevBlock+1; i<=nextBlock-1 && i<=pageCount ;i++) {
			String css=(i==cpage)? "active":"";
			
			buf.append("<li class='page-item "+css+"'>");
			buf.append("<a class='page-link' href='"+link+"&cpage="+i+"'>");
			buf.append(i);
			buf.append("</a>");
			buf.append("</li>");
		}
		
		if(nextBlock<=pageCount) {
			buf.append("<li class='page-item'>")// "/multiweb/board/list?cpage=5"
				.append("<a class='page-link' href='"+link+"&cpage="+nextBlock+"'>")
				.append("Next")
				.append("</a>")
				.append("</li>");
		}
		
		buf.append("</ul>");
		str=buf.toString();
		//System.out.println(str);
		return str;
	}
	
}

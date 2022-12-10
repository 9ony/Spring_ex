package com.board.model;

import lombok.Data;

/*페이징 처리 및 검색기능 모듈화*/
@Data
public class PagingVO {
	//페이징 처리 관련 프로퍼티
	private int cpage;
	private int pageSize;
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
	public void init() {
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
	
	
}

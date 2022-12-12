package com.my.multiweb;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.board.model.BoardVO;
import com.board.model.PagingVO;
import com.board.service.BoardService;
import com.common.CommonUtil;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board")
public class BoardController {
	
	@Resource(name="boardServiceImpl")
	private BoardService boardService;
	
	@Inject
	private CommonUtil util;
	
	@GetMapping("/write")
	public String boardForm() {
		return "board/boardWrite";
	}
	@PostMapping("/write")
	public String boardInsert(@RequestParam("mfilename") MultipartFile mfilename,
			@ModelAttribute BoardVO board,Model m,HttpServletRequest req)
	{
		log.info("board===>  "+board);
		
		//1.파일 업로드 처리
		ServletContext app = req.getServletContext();
		String upDir = app.getRealPath("/resources/board_upload");
		log.info("boardwirte 이미지 경로====>"+upDir);
		File dir=new File(upDir);
		if(!dir.exists()) {
			dir.mkdirs();//업로드 디렉터리 생성
		}
		if(!mfilename.isEmpty()) {
			//[1] 첨부파일명과 파일크기
			String originFname=mfilename.getOriginalFilename();
			long fsize=mfilename.getSize();
			log.info(originFname+">>"+fsize);
			
			//[2] 동일한 파일명이 서버에 있을경우 덮어쓰기방지
			// "랜덤문자"+"_원본파일명"
			UUID uuid=UUID.randomUUID(); //랜덤한 16진수 문자열 생성해주는 메서드
			String filename=uuid.toString()+"_"+originFname; //실제 업로드할 파일명
			log.info("filename===> "+filename);
			
			//mode가 edit일때 예전 첨부파일 삭제처리
			if(board.getMode().equals("edit") && board.getOld_filename()!=null) {
				File delF=new File(upDir, board.getOld_filename());
				if(delF.exists()) {
					boolean b=delF.delete();
					log.info("이전파일 삭제여부 : "+b);
				}
			}
			
			//[3] 업로드 처리
			try {
				mfilename.transferTo(new File(upDir,filename));
				log.info("upDir==="+upDir);
			}catch(Exception e) {
				log.error("board write error===>"+e);
			}
			
			//[4]BoradVO 객체에
			board.setFilename(filename);
			board.setFilesize(fsize);
			board.setOriginFilename(originFname);
			
		}
		
		
		//2.유효성 체크 (subject, name, passwd)==> redirect "write"
		if(board.getName().isEmpty()||board.getSubject().isEmpty()||board.getPasswd().isEmpty()) {
			return "redirect:write";
		}
		int n = 0;
		String str="";
		String loc="";
		if("write".equals(board.getMode())) {
			for(int i = 0; i<30 ; i++) {
			n=this.boardService.insertBoard(board);
			}
			str = "글쓰기 ";
			
		}else if("rewrite".equals(board.getMode())) {
			n=this.boardService.rewriteBoard(board);
			str= "답글 쓰기 ";
		}else if("edit".equals(board.getMode())) {
			n=this.boardService.updateBoard(board); //수정처리
			str = "글수정 ";
		}
		str+=(n>0)?"성공":"실패";
		loc=(n>0)?"list":"javascript:histroy.back()";
		
		return util.addMsgLoc(m,str,loc); //msg를 반환
	}
	@GetMapping("/list")
	public String boardListPaging(Model m , @ModelAttribute("page") PagingVO page,
			HttpServletRequest req,@RequestHeader("User-Agent") String userAgent) {
		String myctx=req.getContextPath();
		
		HttpSession ses=req.getSession();
		log.info("session ===>"+ses);
		log.info("page====>"+page);
		//총게시글수
		int totalCount=boardService.getTotalCount(page);
		page.setTotalCount(totalCount);
		//page.setPageSize(page.getPageSize()); //한 페이지 당 보여줄 목록 개수 <==파라미터로 받는다
		page.setPagingBlock(5);
		
		page.init(ses);
		
		//2. 게시글 목록 가져오기 or 검색한 게시글 목록 가져오기
		List<BoardVO> boardArr=this.boardService.selectBoardAllPaging(page);
		//3. 페이지 네비게이션 문자열 받아오기
		String loc="board/list";
		String pageNavi=page.getPageNavi(myctx, loc ,userAgent);
		
		m.addAttribute("pageNavi", pageNavi);
		m.addAttribute("paging",page);
		m.addAttribute("boardArr", boardArr);
		return "board/boardList3";
	}
	/*
	@GetMapping("/list_old2")
	public String boardListPaging2(Model m , @ModelAttribute("page") PagingVO page) {
		log.info("page====>"+page);
		//총게시글수
		int totalCount=boardService.getTotalCount(page);
		page.setTotalCount(totalCount);
		page.setPageSize(5);
		page.setPagingBlock(5);
		
		page.init();
		
		//게시판 목록조회
		List<BoardVO> boardArr=this.boardService.selectBoardAllPaging(page);

		m.addAttribute("boardArr",boardArr);
		m.addAttribute("paging",page);
		return "board/boardList2";
	}
	*/
	@GetMapping("/list_old")
	public String boardList(Model m , @RequestParam(defaultValue="1") int cpage) {
		//cpage(현재페이지) 기본값 1
		log.info("cpage===="+cpage);
		if(cpage<1) { //0이나 음수로들어올때 첫페이지로
			cpage=1;
		}
		//페이지 총게시글 조회
		int totalCount = boardService.getTotalCount(null);
		
		//페이지크기 설정
		int pageSize=5; // 1페이지당 글개수
		int pageCount=(totalCount-1)/pageSize+1; //(페이지개수)
		
		if(cpage>pageCount) { //현재페이지가 pageCount보다 크면 마지막페이지로
			cpage=pageCount;
		}
		//[1] between
		/*
		int end=cpage*pageSize;
		int start=end-(pageSize-1);
		*/
		
		
		//[2] 부등호
		int start = (cpage-1)*pageSize;
		int end = start+(pageSize+1);
		
		
		Map<String,Integer> map=new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		//게시판 목록조회
		List<BoardVO> boardArr=this.boardService.selectBoardAll(map);
		m.addAttribute("boardArr",boardArr);
		m.addAttribute("totalCount",totalCount);
		m.addAttribute("pageCount",pageCount);
		return "/board/boardList";
	}
	@GetMapping("/view/{num}")
	public String boardView(Model m,@PathVariable("num") int num) {
		log.info("num==="+num);
		//조회수 증가
		boardService.updateReadnum(num);
		
		BoardVO board=boardService.selectBoardByIdx(num);
		m.addAttribute("board",board);
		
		
		return "board/boardView";
	}
	
	@PostMapping("/delete")
	public String boardDelete(Model m, @RequestParam(defaultValue="0") int num,
			@RequestParam(defaultValue="") String passwd,
			HttpServletRequest req) {
		log.info("num=="+num+", password=="+passwd);
		if(num==0||passwd.isEmpty()) {
			return "redirect:list";
		}
		//해당 글을 db에서 가져오기
		BoardVO vo = boardService.selectBoardByIdx(num);
		if(vo==null) {
			return util.addMsgBack(m,"해당 글은 존재하지 않아요");
		}
		//비밀번호 일치여부 체크해서 일치하면 삭제 처리
		String dbPwd=vo.getPasswd();
		if(!dbPwd.equals(passwd)) {
			return util.addMsgBack(m, "비밀번호가 일치하지 않아요");
		}
		//글삭제처리
		int n = boardService.deleteBoard(num);
		
		ServletContext app=req.getServletContext();
		String upDir=app.getRealPath("/resources/board_upload");
		log.info("delete upDir===>"+upDir);
		
		//서버에 업로드한 첨부파일이 있으면 삭제 처리
		if(n>0 && vo.getFilename()!=null) {
			File f = new File(upDir,vo.getFilename());
			if(f.exists()) {
				boolean b = f.delete(); //삭제성공 true반환 실패 false
				log.info("파일삭제 유무 : "+b);
			}
		}
		String str=(n>0)?"글 삭제 성공":"글 삭제 실패";
		String loc=(n>0)?"list":"javascript:history.back()";
		
		return util.addMsgLoc(m, str, loc);
	}
	
	@PostMapping("/edit")
	public String boardEditform(Model m , @RequestParam(defaultValue="0") int num,
			@RequestParam(defaultValue="") String passwd) {
		//1. 글번호,비번 유효성 체크 ==> list redirect이동
		if(num==0) {
			util.addMsgBack(m, "없는 글 입니다.");
		}
		//2. 글번호로 해당 글 내용 가져오기
		BoardVO vo = boardService.selectBoardByIdx(num);
		if(vo==null) {
			return util.addMsgBack(m,"해당 글은 존재하지 않아요");
		}
		//3. 비번 유효성체크
		if(!vo.getPasswd().equals(passwd)) {
			return util.addMsgBack(m, "비밀번호가 일치하지 않아요");
		}
		//4. Model에 해당글 데이터 저장
		m.addAttribute("board",vo);
		return "board/boardEdit";
	}
	
	@PostMapping("/rewrite")
	public String boardRewrite(Model m , @ModelAttribute BoardVO vo) {
		log.info("rewrirte VO ===>"+vo);
		m.addAttribute("num",vo.getNum());
		m.addAttribute("subject",vo.getSubject());
		return "board/boardRewrite";
	}
}

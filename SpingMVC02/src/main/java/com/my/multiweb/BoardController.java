package com.my.multiweb;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.board.model.BoardVO;
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
			n=this.boardService.insertBoard(board);
			str = "글쓰기 ";
		}else if("rewrite".equals(board.getMode())) {
			
		}else if("edit".equals(board.getMode())) {
			
		}
		str+=(n>0)?"성공":"실패";
		loc=(n>0)?"list":"javascript:histroy.back()";
		
		return util.addMsgLoc(m,str,loc); //msg를 반환
	}
	@GetMapping("/list")
	public String boardList(Model m) {
		//게시판 목록조회
		List<BoardVO> boardArr=this.boardService.selectBoardAll(null);
		m.addAttribute("boardArr",boardArr);
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
}

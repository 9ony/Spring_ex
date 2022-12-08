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
			n=this.boardService.insertBoard(board);
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
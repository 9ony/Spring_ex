package com.my.multiweb;

import java.io.File;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shop.model.CategoryVO;
import com.shop.model.ProductVO;
import com.shop.service.AdminService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/admin") //클래스앞에 붙이면 /admin은 달고들어온다
@Log4j
public class AdminController {
	
	@Inject
	private AdminService adminService;
	
	//상위카테고리 model에 추가
	@GetMapping("/prodForm")
	public String productFrom(Model m) {
		List<CategoryVO> upCgList=adminService.getUpcategory();
		m.addAttribute("upCgList",upCgList);
		return "/admin/prodForm";
	}
	
	//상품수정폼
	@GetMapping("/prodEditForm")
	public String prodEditForm9(Model m,@RequestParam(value= "pnum") int pnum) {
		log.info(pnum);
		//상위카테고리 model에 추가
		List<CategoryVO> upCgList=adminService.getUpcategory();
		m.addAttribute("upCgList",upCgList);
		if(pnum>0) {
			ProductVO prod = adminService.selectByPnum(pnum);
			log.info("prodEdit prod===>"+prod);
			m.addAttribute("flag",false);
			m.addAttribute("prod",prod);
			
			return "/admin/prodForm";
		}else {
			m.addAttribute("message","등록되지않은 상품입니다.");
			m.addAttribute("loc","./prodList");
			return "msg";
		}
	}
	
	//하위카테고리 데이터 요청처리
	@PostMapping(value="/getDownCategory" , produces ="application/json")
	@ResponseBody
	public List<CategoryVO> DownCategory(@RequestParam("upCode") String upCode) {
		//log.info("upcode --->"+upCode);
		List<CategoryVO> downCgList = adminService.getDowncategory(upCode);
		//log.info("dwonCgList --->"+downCgList);
		return downCgList;
	}
	//상품등록
	@PostMapping("/prodInsert")
	public String productRegister(Model m,
			@RequestParam("pimage") List<MultipartFile> pimage,
			@ModelAttribute("product") ProductVO product,
			HttpServletRequest req){
		//log.info("product==="+product);
		//1. 업로드 디렉토리 절대경로 얻기
		ServletContext app = req.getServletContext();
		String upDir = app.getRealPath("/resources/product_images");
		log.info("upDir경로====>"+upDir);
		//폴더 없으면 생성
		File dir=new File(upDir);
		if(!dir.exists()) {
			dir.mkdirs();//업로드 디렉터리 생성
		}
		//2. 업로드 처리
		if(pimage!=null) {
			int index = 0; //setPimage()에 쓰일 index 
			for(int i=0;i<pimage.size();i++) {
				MultipartFile mfile=pimage.get(i);
				if(!mfile.isEmpty()) {
					try {
						//업로드 처리
						mfile.transferTo(new File(upDir,mfile.getOriginalFilename()));
						product.setPimage(index,mfile.getOriginalFilename());
						index++;
					}
					catch (Exception e) {
						log.error("파일 업로드 실패: "+e);
					}
				}
			}//
			//log.info("업로드 이후 product==="+product);
		}
		int n = adminService.productInsert(product);
		String str=(n>0)?"상품등록 성공":"등록 실패";
		String loc=(n>0)?"prodList":"javascript:history.back()";
		
		
		m.addAttribute("message",str);
		m.addAttribute("loc", loc);
		
		return "msg";
	}
	@PostMapping("/prodUpdate/{pnum}")
	public String pordUpdate(Model m ,@PathVariable("pnum") int pnum ,
			@RequestParam("pimage") List<MultipartFile> pimage,
			@ModelAttribute("product") ProductVO prod,
			HttpServletRequest req) 
	{
		ServletContext app = req.getServletContext();
		String upDir = app.getRealPath("/resources/product_images");
		log.info("upDir경로====>"+upDir);
		//수정전 product
		ProductVO prod_before=adminService.selectByPnum(pnum);
		if(pimage!=null) {
			for(int i=0;i<pimage.size();i++) {
				MultipartFile mfile=pimage.get(i);
				if(!mfile.isEmpty()) {
					//log.info("!mfile.isEmpty() i ==>"+ i );
					try {
						//업로드 처리
						mfile.transferTo(new File(upDir,mfile.getOriginalFilename()));
						prod.setPimage(i,mfile.getOriginalFilename());
					}
					catch (Exception e) {
						log.error("파일 업로드 실패: "+e);
					}
				}else {
					//log.info("mfile.isEmpty() i ==>"+ i );
					if(i==0) {
						prod.setPimage1(prod_before.getPimage1());
					}else if(i==1) {
						prod.setPimage2(prod_before.getPimage2());
					}else if(i==2) {
						prod.setPimage3(prod_before.getPimage3());
					}
				}
			}
		}
		int n = adminService.productUpdate(prod);
		
		String str=(n>0)?"상품수정 성공":"수정실패";
		String loc=(n>0)?"../prodList":"javascript:histroy.back()";
		
		m.addAttribute("message",str);
		m.addAttribute("loc",loc);
		return "msg";
	}
	//상품삭제
	@GetMapping("/prodDel")
	public String pordDelete(Model m,@RequestParam("pnum") int pnum) {
		
		int n = adminService.productDelete(pnum);

		String str=(n>0)?"상품삭제 성공":"삭제 실패";
		String loc=(n>0)?"./prodList":"javascript:histroy.back()";
		
		m.addAttribute("message",str);
		m.addAttribute("loc",loc);
		return "msg";
	}
	
	//상품목록
	@GetMapping("/prodList")
	public String productList(Model m) {
		
		List<ProductVO> prodArr=adminService.productList();
		m.addAttribute("prodArr", prodArr);
		
		return "/admin/prodList";
	}
}

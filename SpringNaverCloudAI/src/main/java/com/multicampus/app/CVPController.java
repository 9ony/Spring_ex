package com.multicampus.app;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
public class CVPController {
	
	String clientId="0xok1o8ptm";
	String clientSecret="AbECzqJO1yw9jhtOPJ3Bzu48kIB0oEMUdv2RuPmV";

	
	@GetMapping("/voiceform")
	public ModelAndView voiceForm() {
		
		return new ModelAndView("clova_voice");
	}
	@PostMapping(value="/voiceEnd", produces = "text/plain; charset=UTF-8")
	public String voiceTransform(@RequestParam("text") String text, HttpSession ses) {
		log.info("text===="+text);
		ServletContext app=ses.getServletContext();
		String path=app.getRealPath("/file");
		
		String fileName="mp3파일명";
		try {
			/////////////////////////////
            text = URLEncoder.encode(text, "UTF-8"); // 13자
            ///////////////////////////
            String apiURL = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            // post request
            String postParams = "speaker=nbora&volume=0&speed=0&pitch=0&format=mp3&text=" + text;
            log.info(postParams);
            con.setDoOutput(true);
            con.setDoInput(true);
            
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
            	log.info("===성공============");
                InputStream is = con.getInputStream();
                //네이버에서 바이너리데이터를 받아서==> 우리 웹서버의 file디렉토리에 파일형태로 저장한다
                int read = 0;
                byte[] bytes = new byte[1024];
                // 랜덤한 이름으로 mp3 파일 생성
                String tempname = Long.valueOf(new Date().getTime()).toString();
                File f = new File(path,tempname + ".mp3");
                log.info("mp3파일 경로==="+f.getAbsolutePath());
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read =is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                    outputStream.flush();
                }
                is.close();
                outputStream.close();
                fileName=tempname+".mp3";
            } else {  // 오류 발생
            	log.info("===오류1============");
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
                fileName="error.mp3";
            }
        } catch (Exception e) {
        	log.info("===오류2============");
            System.out.println(e);
            e.printStackTrace();
            fileName="error.mp3";
        }
		
		return fileName;
	}
	
	@PostMapping("/voiceOk")
	@ResponseBody
	public String voiceOk(@RequestParam("text") String text,HttpServletRequest req) {
		System.out.println("text=>"+text);
				ServletContext app=req.getServletContext();
				String dir=app.getRealPath("file");
	        try {
	            text = URLEncoder.encode(text, "UTF-8"); // 13자
	            String apiURL = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setRequestMethod("POST");
	            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
	            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
	            // post request
	            String postParams = "speaker=noyj&volume=0&speed=0&pitch=0&format=mp3&text=" + text;
	            con.setDoOutput(true);
	            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	            wr.writeBytes(postParams);
	            wr.flush();
	            wr.close();
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if(responseCode==200) { // 정상 호출
	            	System.out.println("성공:==========");
	                InputStream is = con.getInputStream();
	                int read = 0;
	                byte[] bytes = new byte[1024];
	                // 랜덤한 이름으로 mp3 파일 생성
	                String tempname = Long.valueOf(new Date().getTime()).toString();
	                File f = new File(dir,tempname + ".mp3"); 
	                System.out.println(f.getAbsolutePath());
	                f.createNewFile();
	                
	                OutputStream outputStream = new FileOutputStream(f);
	               // ServletOutputStream outputStream=res.getOutputStream();
	                //우리 서버로 응답을 보내는 작업을 해보자.
	                while ((read =is.read(bytes)) != -1) {
	                    outputStream.write(bytes, 0, read);
	                    outputStream.flush();
	                }
	                is.close();
	                outputStream.close();
	                return tempname + ".mp3";
	            } else {  // 오류 발생
	            	System.out.println("오류발생:==========");
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	                String inputLine;
	                StringBuffer buf = new StringBuffer();
	                while ((inputLine = br.readLine()) != null) {
	                	buf.append(inputLine);
	                }
	                br.close();
	                System.out.println(buf.toString());
	            }
	           return "";
	        } catch (Exception e) {
	            System.out.println(e);
	            e.printStackTrace();
	            return "";
	        }
		
	}//------------------------------------------

}

package com.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j;

/*
 * 스프링의 예외처리 방법
 * 각각 서비스랑 컨트롤러에 간단한 에러도 가능하지만 하나의 클래스에서 관리하기 편하게하기 위함
 * [1] @ExceptionHandler를 이용
 * [2] @ControllerAdvice를 이용
 * [3] @ResponseStatus를 이용해서 http상태코드로 처리하는 방법
*/


@ControllerAdvice //예외만 처리하는 클래스
@Log4j
public class CommonExceptionAdvice {
	@ExceptionHandler(NumberFormatException.class)
	public String cmmNumberException(Exception e) {
		log.error(e);
		return "/login/errorAlert";
	}
	/* ex)
	@ExceptionHandler(예외클래스 기술)
	public String 메서드명기술(Exception e) {
		log.error(e);
		return "예외처리 보여줄 페이지 jsp나 alert로~";
	}*/
}

package com.edujavaonline.brewer.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.edujavaonline.brewer.service.exception.NomeEstiloJaCadastrado;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {
	
	@ExceptionHandler(NomeEstiloJaCadastrado.class)
	public ResponseEntity<String> handleNomeEstiloJaCadastrado(NomeEstiloJaCadastrado e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}

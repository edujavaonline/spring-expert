package com.edujavaonline.brewer.service.exception;

public class NomeEstiloJaCadastrado extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NomeEstiloJaCadastrado(String message) {
		super(message);
	}

}

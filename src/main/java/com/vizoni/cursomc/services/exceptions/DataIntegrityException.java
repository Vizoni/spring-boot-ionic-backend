package com.vizoni.cursomc.services.exceptions;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/* passa a mensagem para a classe RuntimeException, para reaproveitar a estrutura da classe*/
	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	/* informa a causa do erro também, se houver*/
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}

package com.vizoni.cursomc.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/* passa a mensagem para a classe RuntimeException, para reaproveitar a estrutura da classe*/
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	/* informa a causa do erro também, se houver*/
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}

package com.cursomc.services.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Class<?> clazz;
	
	public ObjectNotFoundException(Integer id, Class<?> clazz, String msg) {
		super(msg);
		this.id = id;
		this.clazz = clazz;
	}

	public Integer getId() {
		return id;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
	
}

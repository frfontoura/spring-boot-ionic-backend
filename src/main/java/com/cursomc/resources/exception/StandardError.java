package com.cursomc.resources.exception;

import org.springframework.http.HttpStatus;

public class StandardError {

	private Integer status;
	private String msg;
	private Long timeStamp;

	public StandardError(HttpStatus status, String msg, Long timeStamp, boolean show) {
		super();
		this.status = status.value();
		this.msg = msg;
		this.timeStamp = timeStamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

}

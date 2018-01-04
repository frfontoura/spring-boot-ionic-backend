package com.cursomc.resources.exception;

public class StandardError {

	private Integer status;
	private String msg;
	private String msgConsole;
	private Long timeStamp;
	private boolean show = false;

	public StandardError(Integer status, String msg, Long timeStamp, boolean show) {
		super();
		this.status = status;
		this.msg = msg;
		this.timeStamp = timeStamp;
		this.show = show;
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

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public String getMsgConsole() {
		return msgConsole;
	}

	public void setMsgConsole(String msgConsole) {
		this.msgConsole = msgConsole;
	} 

	
}

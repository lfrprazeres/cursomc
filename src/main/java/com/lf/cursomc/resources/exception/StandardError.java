package com.lf.cursomc.resources.exception;

import java.io.Serializable;

/* Standanrd = padrão, error = erro, classe padrão com um modelo de erro para poder ser usado pelo Handler */
public class StandardError implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/* O status HTTP que será retornado */
	private Integer status;
	/* A mensagem que será dada*/
	private String msg;
	/* A hora que esse erro aconteceu */
	private Long timeStamp;
	
	public StandardError(Integer status, String msg, Long timeStamp) {
		super();
		this.status = status;
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

package com.google.core.domain;

public class SendSmsToOther {
	private String body;
	private String senderNum;

	public SendSmsToOther(String senderNum, String body) {
		this.senderNum=senderNum;
		this.body=body;
	}

	public String getBody() {
		return body;
	}

	public String getSenderNum() {
		return senderNum;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setSenderNum(String senderNum) {
		this.senderNum = senderNum;
	}

	@Override
	public String toString() {
		return "SendSmsToOther [senderNum=" + senderNum + ", body=" + body
				+ "]";
	}

}

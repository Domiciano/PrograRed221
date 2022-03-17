package model;

public class Message {
	
	public String type = "Message";
	private String body;
	
	public Message() {}
	
	public Message(String body) {
		this.body = body;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}

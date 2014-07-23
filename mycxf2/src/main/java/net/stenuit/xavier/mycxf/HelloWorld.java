package net.stenuit.xavier.mycxf;

public class HelloWorld {
	private String message;
	
	public void greets() {
		System.out.println(message);
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

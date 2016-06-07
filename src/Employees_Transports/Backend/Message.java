package Employees_Transports.Backend;

public class Message {
	private String user;
	private String messageID;
	private String message;
	
	public Message(String _messageID, String _user, String _message){
		setUser(_user);
		setMessageID(_messageID);
		setMessage(_message);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString(){
		String str ="User: "+user;
		str = str +"\nMessageID: " + messageID;
		str = str + "\nMessage: " +message;
		return str;
	}
	
}

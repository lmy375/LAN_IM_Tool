package lit.object.messages;

import java.io.Serializable;

public  class Message implements Serializable{
	/**
	 * Types of messages.	
	 */
	//To server
	public static final int MSG_LOGIN = 1;
	public static final int MSG_ANSWER = 2;
	public static final int MSG_REGISTER = 3;
	//To client
	public static final int MSG_CONTACT_INFO = 6;
	public static final int MSG_USER_LIST = 7;
	//To server or client
	public static final int MSG_REQUEST = 4;
	public static final int MSG_CHAT =10;
	
	
	public static final String ID_SERVER = "server";
	
	private int type = 0;
	private String fromID = null;
	private String toID = null;
	
	public Message(int type, String fromID , String toID){
		this.type = type;
		this.fromID = fromID;
		this.toID = toID;
	}
	//public void setContent(String content){
	//	this.content = content;
	//}
	public void setType(int type){
		this.type = type;
	}
	public int getType(){
		return this.type;
	}
	public void setFromID(String fromID){
		this.fromID = fromID;
	}
	public void setToID(String toID){
		this.toID = toID;
	}
	public String getFromID(){
		return this.fromID;
	}
	public String getToID(){
		return this.toID;
	}
	
}

package lit.object;

import java.io.Serializable;

public  class Message implements Serializable{
		
	public static final int MSG_PASSWORD = 0;
	public static final int MSG_ID = MSG_PASSWORD +1;
	public static final int MSG_TEXT = MSG_ID +1;
	public static final int MSG_SYS = MSG_TEXT+1;
	
	public static final String CNT_TRUE = "true";
	public static final String CNT_FALSE = "false";
	
	public static final String ID_SERVER_CHECK = "server_check";
	public static final String ID_CLIENT = "client";
	
	private String fromID = null;
	private String toID = null;
	private String content = null;
	
	public Message(String fromID , String toID){
		this.fromID = fromID;
		this.toID = toID;
	}
	public Message(String fromID , String toID , String content){
		this(fromID , toID);
		this.content = content;
	}
	public void setContent(String content){
		this.content = content;
	}
	
	public String getFromID(){
		return this.fromID;
	}
	public String getToID(){
		return this.toID;
	}
	public String getContent(){
		return this.content;
	}
}

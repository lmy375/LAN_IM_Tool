package lit.object.messages;

public class ChatMessage extends Message{
	private String time;
	private String content;
	
	public ChatMessage(String fromID, String toID, String time, String content){
		super(Message.MSG_CHAT, fromID, toID);
		this.time = time;
		this.content = content;
	}
	public String getTime(){
		return this.time;
	}
	public String getContent(){
		return this.content;
	}
}

package lit.object.messages;

public class RequestMessage extends Message{
	/**
	 * Request to server.
	 */
	public static final int REQ_CONTACT_INFO = 1;
	public static final int REQ_ADD_FRIEND = 2;
	public static final int REQ_REMOVE_FRIEND = 3;
	public static final int REQ_CONTACT_LIST =4;
	public static final int REQ_LOG_OUT = 5;
	
	public static final int REQ_CHECK_ONLINE = 10;
	/**
	 * Request to client , actually kind of notification.
	 */
	public static final int REQ_ONLINE = 6;
	public static final int REQ_OFFLINE = 7;
	
	
	int requestCode = 0 ;
	String targetID ;
	public RequestMessage(int reqCode , String targetID, String fromID){
		super(Message.MSG_REQUEST, fromID, Message.ID_SERVER);
		this.requestCode = reqCode;
		this.targetID = targetID;
	}
	public int getRequestCode(){
		return this.requestCode;
	}
	public String getTargetID(){
		return this.targetID;
	}
}

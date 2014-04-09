package lit.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lit.object.ContactInfo;
import lit.object.messages.AnswerMessage;
import lit.object.messages.ContactInfoMessage;
import lit.object.messages.ContactListMessage;
import lit.object.messages.LoginMessage;
import lit.object.messages.Message;
import lit.object.messages.RegisterMessage;
import lit.object.messages.RequestMessage;


/**
 * Basic class to handle user client. Use this to send message to User.
 * All message received from user will hand in to server to handle.
 * @author Moon
 *
 */
public class HandleClient extends Thread{
	
	private String ID = null;
	private Socket socket = null;
	private ObjectInputStream input = null;
	private ObjectOutputStream output = null;
	private Server server= null;
	//private MessageQueue messageQueue = null;
	
	/**
	 * Friend of this client. Only used after logging in.
	 */
	
	HashMap<String, ContactInfo> contactMap;
	ArrayList<String> idList;
	/**
	 * User information of this client. Only used after logging in.
	 */
	ContactInfo selfInfo;
	
	
	private boolean isStarted = false;
	private boolean login = false;
	
	public boolean isStarted(){
		return this.isStarted;
	}
	
	public String getID(){
		return this.ID;
	}
	
	//public boolean addToMessageQueue(Message msg){
	//	return this.messageQueue.putMessage(msg);
	//}
	
	public HandleClient(Socket socket, Server server){
		super();
		this.socket  = socket;
		this.server = server;
		//messageQueue = new MessageQueue();
		try {
			// output first
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();			
			input = new ObjectInputStream(socket.getInputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	 * Write socket output stream , which must be synchronized , or race will occur
	 * between clients that send message to same ID. 	
	 * @param msg
	 */
	public synchronized void sendMessage(Message msg){
		try {
			//System.out.println("Message send. TYPE:"+ msg.getType());
			output.writeObject(msg);
		}catch(SocketException e){
			if(login) this.logout();	
			this.close();
			server.log("Client "+this.ID+" closed for Socket exception.");		
		}catch (IOException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Message getMessage() {
		try {
			return (Message) input.readObject();
		} catch(SocketException e){
			//e.printStackTrace();
			/**
			 * When connection resets , this client will log out and socket will be closed and thread ends.
			 */
			if(login) this.logout();	
			this.close();
			server.log("Client "+this.ID+" closed for Socket exception.");
			return null;
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			/**
			 * 
			 */
			if(login) this.logout();	
			this.close();
			server.log("Client "+this.ID+" closed for IO exception.");
			return null;
		}
	}	
	
	private void close(){
		try {
			this.isStarted=false;
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Message handling loop.
	 */
	@Override
	public void run(){
		this.isStarted = true;
		while(isStarted){			
				Message inMsg = this.getMessage();				
				if(inMsg==null) continue;
				switch(inMsg.getType()){
				case(Message.MSG_CHAT): 
					if(!login) break; // Only handle clients that are online.				
					server.sendMessageToID(inMsg, inMsg.getToID());
					server.log("Server deliver message: "
							+ inMsg.getFromID()+" to "+ inMsg.getToID());
					break;	
				case(Message.MSG_LOGIN):
					handleLogin((LoginMessage)inMsg);
					break;
				case(Message.MSG_REGISTER):
					handleRegister((RegisterMessage)inMsg);
					break;
				case(Message.MSG_REQUEST):
					handleRequest((RequestMessage)inMsg);
					break;
				case(Message.MSG_CONTACT_INFO):
					handleChangeInfo((ContactInfoMessage)inMsg);
					break;
				default:
					server.log("Unknow message type: "+ inMsg.getType());
				}				
		}
		this.isStarted = false;
	}
	
	private void handleLogin(LoginMessage msg){
		/**
		 * Get login message and set ID and password.
		 */
		this.ID = msg.getFromID();
		String password = msg.getPassword();
		
		AnswerMessage ansMsg ;
		if(server.checkLogin(this.ID, password)){
			server.addClientToMap(this);
			
			
			/**
			 * Get self information and contact list from database.
			 */
			RequestMessage reqMsg = new RequestMessage(RequestMessage.REQ_ONLINE, "", this.ID);
			
			try {
				this.selfInfo = server.getDatabase().queryUser(this.ID);
				this.idList =server.getDatabase().queryFriList(ID);
				this.contactMap = new HashMap<String,ContactInfo>();				
				for(String id:idList){
					this.contactMap.put(id, server.getDatabase().queryUser(id));
					
					this.server.sendMessageToID(reqMsg, id);
				}	
				ansMsg = new AnswerMessage(true,AnswerMessage.ANS_LOGIN, "OK");		
				this.login=true;
				
				System.out.println("Log in : "+ this.ID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ansMsg = new AnswerMessage(false,AnswerMessage.ANS_LOGIN, e.getMessage());
			}		
									
		}else
			ansMsg = new AnswerMessage(false,AnswerMessage.ANS_LOGIN, "ID and password don't match");	
		this.sendMessage(ansMsg);
		
	}
	
	private void handleRegister(RegisterMessage msg){
		//Database register
		String exception=null;
		String id=null;
		try {
			 id = server.getDatabase().addUser("", msg.getName(), msg.getPassword(), msg.getSex());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exception = e.getMessage();
		}
		AnswerMessage ansMsg;
		
		if(id.equals("")){
			ansMsg= new AnswerMessage(false,AnswerMessage.ANS_REGISTER,exception);
		}			
		else{
		//	System.out.println("Register -- ID: 11061159 Name:"+msg.getName()+" Password:"+msg.getPassword()+" Sex:"+ msg.getSex());
			ansMsg= new AnswerMessage(true,AnswerMessage.ANS_REGISTER,"«Î¿Œº«£∫\nID: "+id+ " Password: "+msg.getPassword());
		}
		this.sendMessage(ansMsg);
	}
	
	private void handleChangeInfo(ContactInfoMessage msg){
		AnswerMessage ansMsg ;
		try {			
			server.getDatabase().updateUser(msg.getInfo());
			if(msg.getInfo().getID().equals(this.ID))
				this.selfInfo = msg.getInfo();
			else
				this.contactMap.put(msg.getInfo().getID(), msg.getInfo());
			ansMsg = new AnswerMessage(true, AnswerMessage.ANS_MODIFY_INFO, msg.getInfo().getID() );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ansMsg = new AnswerMessage(false, AnswerMessage.ANS_MODIFY_INFO,e.getMessage() );
		}
		this.sendMessage(ansMsg);
	}
	private void handleRequest(RequestMessage msg){
		switch (msg.getRequestCode()){
		case(RequestMessage.REQ_LOG_OUT):
			this.logout();
			break;
		case(RequestMessage.REQ_ADD_FRIEND):
			this.addFriend(msg.getTargetID());			
			break;
		case(RequestMessage.REQ_REMOVE_FRIEND):
			this.removeFriend(msg.getTargetID());
			break;
		case(RequestMessage.REQ_CONTACT_INFO):
			this.getContactInfo(msg.getTargetID());
			break;
		case(RequestMessage.REQ_CONTACT_LIST):
			this.getContactList(msg.getTargetID());
			break;
		case(RequestMessage.REQ_CHECK_ONLINE):
			if(server.clientMap.containsKey(msg.getTargetID())){
				RequestMessage reqMsg = new RequestMessage(RequestMessage.REQ_ONLINE,"",msg.getTargetID());
				this.sendMessage(reqMsg);
			}
			break;
		default:
			server.log("Unknown request code");
		}
	}
	
	private void logout(){
		this.login=false;
		RequestMessage msg = new RequestMessage(RequestMessage.REQ_OFFLINE,"",this.getID() );
		for(ContactInfo friend: contactMap.values()){
			server.sendMessageToID(msg,friend.getID());
		}
		server.removeClientFromMap(this);
		server.log("Log out : "+ this.ID);
	}
	private void addFriend(String targetID){
		/**
		 * Database add friend.
		 */
		boolean succ;
		String exception=null;
		try {
			succ= server.getDatabase().addFriend(this.ID,targetID);
			this.idList.add(targetID);
			this.contactMap.put(targetID, server.getDatabase().queryUser(targetID));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			succ=false;
			exception = e.getMessage();
		}
		
		AnswerMessage anMsg ;
		if(succ){
			System.out.println("Add "+this.ID+"'s friend "+targetID);
			anMsg = new AnswerMessage(true, AnswerMessage.ANS_ADD_FRIEND,targetID);
		}
		else
			anMsg = new AnswerMessage(false, AnswerMessage.ANS_ADD_FRIEND,exception);
			this.sendMessage(anMsg);
	}
	private void removeFriend(String targetID){
		/**
		 * Database remove friend.
		 */
		boolean succ;
		String exception=null;
		try {
			succ= server.getDatabase().delFriend(this.ID,targetID);
			this.idList.remove(targetID);
			this.contactMap.remove(targetID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			succ=false;
			exception = e.getMessage();
		}
		
		AnswerMessage anMsg ;
		if(succ){
			System.out.println("Remove "+this.ID+"'s friend "+targetID);
			anMsg = new AnswerMessage(true, AnswerMessage.ANS_REMOVE_FRIEND,targetID);
		}
		else
			anMsg = new AnswerMessage(false, AnswerMessage.ANS_REMOVE_FRIEND,exception);
			this.sendMessage(anMsg);
			
	}
	private void getContactInfo(String targetID){
		/**
		 * Database get contact information when logging in.
		 */
		server.log("Sent "+targetID+"'s information.");
		
		ContactInfoMessage infoMsg ;
		if(targetID.equals(this.ID))
			infoMsg = new ContactInfoMessage(this.selfInfo,this.ID);
		else
			infoMsg = new ContactInfoMessage(contactMap.get(targetID),this.ID);
		this.sendMessage(infoMsg);
	}
	private void getContactList(String ID){
		/**
		 * Database get contact list when logging in.
		 */
		server.log("Sent "+ID+"'s contact list.");
		ContactListMessage listMsg = new ContactListMessage(this.idList, ID);
		this.sendMessage(listMsg);
	}
	
}



package lit.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import lit.client.ui.ChatFrame;
import lit.client.ui.FriendsListUI;
import lit.object.ContactInfo;
import lit.object.MessageQueue;
import lit.object.messages.AnswerMessage;
import lit.object.messages.ChatMessage;
import lit.object.messages.ContactInfoMessage;
import lit.object.messages.ContactListMessage;
import lit.object.messages.LoginMessage;
import lit.object.messages.Message;
import lit.object.messages.RegisterMessage;
import lit.object.messages.RequestMessage;
import lit.util.LITUtil;

/**
 * Class that is basic class for the client, which include basic method to
 * communicate with server. Connecting to server and  user login.
 * @author Moon
 *
 */
public class LITClient {
	
	private String serverIP = "localhost";
	private int serverPort = 8888;
	private boolean isConnected = false;
		
	private Socket socket=null;
	private ObjectInputStream input = null;
	private ObjectOutputStream output = null;
	
	private String ID;
	private ContactInfo selfInfo;
	private ArrayList<String> idList;
	private HashMap<String,ContactInfo> contactMap;
	private HashMap<String, MessageQueue> messageMap;
	
	
	
	private FriendsListUI mainFrame=null;
	/**
	 * This method must be called after logging in. Or online status won't be show
	 * in the MainFrame.
	 * @param mainFrame
	 */
	public void setMainFrame(FriendsListUI mainFrame){
		this.mainFrame= mainFrame;
	}
	/**
	 * Add message to message queue in the map.
	 * @param msg
	 */
	private void addMessageToMap(Message msg){
		String fromID = msg.getFromID();
		if(messageMap==null) return ;
		MessageQueue queue = messageMap.get(fromID);
		if(queue==null) return ;
		queue.putMessage(msg);
	}
	/**
	 * Get message from specified queue.  
	 * @param fromID
	 * @return message gotten or null.
	 */
	public Message getMessage(String fromID){
		if(messageMap==null) return null;
		MessageQueue queue = messageMap.get(fromID);
		if(queue==null) return null;
		return queue.getMessage();
	}
	
	private HashMap<String,ChatFrame> chatMap;
	/**
	 * All chat frame must call this method first. Or they 
	 * won't receive any messages.
	 * @param id
	 * @param chat
	 */
	private void wakeUpUI(String id){
		if(mainFrame==null) return;
		mainFrame.setMessageNumber(id, "("+this.messageMap.get(id).size()+")");
		if(chatMap==null) return;
		ChatFrame chat = chatMap.get(id);
		if(chat==null) return;
		chat.refresh();
		mainFrame.setMessageNumber(id,  "");
	}
	public void addChat(String id, ChatFrame chat){
		chatMap.put(id, chat);
		wakeUpUI(id);
	}
	public void removeChat(String id ){
		chatMap.remove(id);
	}
	
	
	
	
	
	public HashMap<String, ContactInfo> getContactMap(){
		return this.contactMap;
	}
	public ContactInfo getSelfInfo(){
		return this.selfInfo;
	}
	/**
	 * Constructor.
	 * @param serverIP
	 * @param serverPort
	 */
	public LITClient(String serverIP , int serverPort){
		this.serverIP = serverIP;
		this.serverPort  = serverPort;
	}
	
	
	public boolean connect(){
		try {
			socket = new Socket(serverIP , serverPort);			
			//input first
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
			isConnected = true;
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			LITUtil.error("ERROR","连接失败，请重试。",e.getMessage());
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block			
			//e.printStackTrace();
			LITUtil.error("ERROR","连接失败，请重试。",e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * Close is not equal to "Log out". To exit, first log out then close.
	 */
	public void close(){
		this.isConnected = false;
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Send message to socket output stream. Basic communication method.
	 * @param msg
	 */
	
	public synchronized void sendMessage(Message msg){
		try {
			output.writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	/**
	 * Get message from socket input stream. This will block.
	 * @return Message gotten.
	 */
	public Message getMessage() {
		try {
			if(!isConnected) return null;
			return (Message) input.readObject();
		}catch(SocketException e){
			JOptionPane.showMessageDialog(null, "Server disconnected.");
			this.close();
			return null;
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}	
	
	
	
	/**
	 * Login method. Using ID and password to login. Server will check if they 
	 * match and return and answer. This will block. 
	 * 
	 * After sending a login message. If check is asserted , this will request
	 * self-info. Then requests id list and all contact information.
	 * 
	 * This has race against chat message. So never use this method after login.
	 * @param ID
	 * @param password
	 * @return true for success, false for failure.
	 */
	public boolean login(String ID, String password){
		this.ID = ID;
		
		if(!this.isConnected){
			System.out.println("###Error in login(): client is disconnected.");
			return false;
		}
				
		LoginMessage logMsg = new LoginMessage(ID, password);
		this.sendMessage(logMsg);
					
		Message checkMsg = this.getMessage();
		if(checkMsg==null){
			System.out.println("###Error in login(): check message is null.");
			return false;
		}
		
		if(checkMsg.getType()==Message.MSG_ANSWER){
			AnswerMessage ansMsg = (AnswerMessage)checkMsg;
		
			if(ansMsg.getAnswer()){
				
				System.out.println("Getting contact infomation......");
				
				this.requestInfo(this.ID);
				ContactInfoMessage infoMsg =(ContactInfoMessage)this.getMessage();
				this.selfInfo = infoMsg.getInfo();
				//System.err.println("#################"+ (selfInfo==null));
				
				this.requestContactList(this.ID);
				ContactListMessage listMsg = (ContactListMessage)this.getMessage();
				this.idList = listMsg.getContactList();
				
				this.contactMap = new HashMap<String, ContactInfo>();
				this.messageMap = new HashMap<String, MessageQueue>();
				this.chatMap = new HashMap<String, ChatFrame>();
				for(String id:idList){
					this.requestInfo(id);
					ContactInfoMessage msg =(ContactInfoMessage)this.getMessage();
					this.contactMap.put(msg.getInfo().getID(), msg.getInfo());
					
					MessageQueue tempQueue = new MessageQueue();
					this.messageMap.put(id, tempQueue);
				}
				System.out.println("Contact infomation gotten");				
				/**
				 * Only after logging in , the message loop works. 
				 * It runs in another thread.
				 */
				new Thread(){
					public void run(){
						messageLoop();
					}
				}.start();
				
				return true;
			}
			else{
				//JOptionPane.showMessageDialog(null,"Login failure:"+  ansMsg.getContent());
				System.out.println("###Failure: "+ ansMsg.getContent());
				return false;
			}
		}
		else {
			System.out.println("###Failure: wrong format of answer message.");
			return false;
		}
		
	}
	
	
	
	
	/**
	 * Register new ID. Register Message will be sent to Server. If success, 
	 * ID can be received in content of answer message. This method will block. 
	 * This has race against chat message. So never use this method after login.
	 * @param password
	 * @param name
	 * @param sex
	 * @return
	 */
	public void register(String password, String name , String sex){
		RegisterMessage msg = new RegisterMessage(password, name,  sex);
		this.sendMessage(msg);
		
		Message checkMsg = this.getMessage();
		if(checkMsg==null){
			System.out.println("###Error in register(): check message is null.");
			
		}
		
		if(checkMsg.getType()==Message.MSG_ANSWER){
			AnswerMessage ansMsg = (AnswerMessage)checkMsg;
			if (ansMsg.getAnswer())
				LITUtil.infomation("注册成功！", ansMsg.getContent());
			else
				LITUtil.error("注册失败","请稍后重试。", ansMsg.getContent());
			
//			if(ansMsg.getAnswer()){
//				//JOptionPane.showMessageDialog(null, "Register success\n"+ ansMsg.getContent());
//				System.out.println("Success! " +ansMsg.getContent());
//				return ansMsg.getToID();
//			}
//			else{
//				//JOptionPane.showMessageDialog(null,"Login failure:"+  ansMsg.getContent());
//				System.out.println("###Failure: "+ ansMsg.getContent());
//				return null;
//			}
		}
		else{		
			System.out.println("###Failure: wrong format of answer message.");
			
		}
	}
	
	/**
	 * Send chat message to contact. No return value.
	 * @param toID    :to whom
	 * @param time	  :sending time
	 * @param content :message content
	 */
	public void sendChatMessage(String toID,String time, String content){
		ChatMessage msg = new ChatMessage(this.ID, toID, time, content);
		this.sendMessage(msg);
	}
	
	public void changeInfo(ContactInfo info){
		this.selfInfo = info;
		ContactInfoMessage msg = new ContactInfoMessage(info,Message.ID_SERVER);
		this.sendMessage(msg);
	}
	public void addFriend(String targetID){
		RequestMessage msg = new RequestMessage(RequestMessage.REQ_ADD_FRIEND, targetID, this.ID);
		this.sendMessage(msg);
	}
	public void removeFriend(String targetID){
		RequestMessage msg = new RequestMessage(RequestMessage.REQ_REMOVE_FRIEND, targetID, this.ID);
		this.sendMessage(msg);
	}
	public void requestInfo(String targetID){
		RequestMessage msg = new RequestMessage(RequestMessage.REQ_CONTACT_INFO, targetID, this.ID);
		this.sendMessage(msg);
	}
	public void requestContactList(String targetID){
		RequestMessage msg = new RequestMessage(RequestMessage.REQ_CONTACT_LIST, targetID, this.ID);
		this.sendMessage(msg);
	}
	
	public void requestCheckOnline(String targetID){
		RequestMessage msg = new RequestMessage(RequestMessage.REQ_CHECK_ONLINE, targetID, this.ID);
		this.sendMessage(msg);
	}
	public void checkAllOnline(){
		for(String id:this.idList){
			requestCheckOnline(id);
		}
	}
	public void logout(){ 
		RequestMessage msg = new RequestMessage(RequestMessage.REQ_LOG_OUT, this.ID, this.ID);
		this.sendMessage(msg);		
	}
		
	public void messageLoop(){
		while(this.isConnected){
			Message inMsg = this.getMessage();
			if (inMsg==null) continue;
			switch(inMsg.getType()){
				case(Message.MSG_CHAT):
					ChatMessage cMsg = (ChatMessage)inMsg;
					this.addMessageToMap(cMsg);	
					this.wakeUpUI(cMsg.getFromID());
				//System.out.println("["+this.ID+"] "+cMsg.getFromID()+"--"+cMsg.getTime()+": "+ cMsg.getContent());
					break;
				case(Message.MSG_CONTACT_INFO):
					ContactInfoMessage infoMsg = (ContactInfoMessage)inMsg;
					if(contactMap==null) contactMap = new HashMap<String, ContactInfo>();
					ContactInfo info = infoMsg.getInfo();
					if(info!=null){
						this.contactMap.put(info.getID(), info);
						this.newMainFrame();
						System.out.println("["+this.ID+"] "+" Gotten information of"+ infoMsg.getInfo().getID());
					}else{
						System.out.println("Gotten null info");
					}
					break;
				case(Message.MSG_USER_LIST):
					ContactListMessage listMsg = (ContactListMessage)inMsg;
					if(this.idList==null) idList = new ArrayList<String>();
					this.idList = listMsg.getContactList();
					System.out.println("["+this.ID+"] "+" Gotten contact list.");
					break;
				case(Message.MSG_ANSWER):
					AnswerMessage ansMsg =( AnswerMessage)inMsg;
				 //	JOptionPane.showMessageDialog(null, ansMsg.getContent(),
				//			ansMsg.getAnswer()?"Success":"Failure", JOptionPane.WARNING_MESSAGE);
				//	System.out.println("["+this.ID+"] "+(ansMsg.getAnswer()?"Success:":"Failure:")+" "+ ansMsg.getContent() );
					switch(ansMsg.getAnswerCode()){
						case AnswerMessage.ANS_REGISTER:
							if (ansMsg.getAnswer())
								LITUtil.infomation("注册成功！", ansMsg.getContent());
							else
								LITUtil.error("注册失败","请稍后重试。", ansMsg.getContent());
							break;
						case AnswerMessage.ANS_ADD_FRIEND:
							if(ansMsg.getAnswer()){
								LITUtil.infomation("添加成功",ansMsg.getContent());
								this.idList.add(ansMsg.getContent());
								this.requestInfo(ansMsg.getContent());
							}
							else
								LITUtil.error("添加失败", "请稍后重试", ansMsg.getContent());
							break;
						case AnswerMessage.ANS_REMOVE_FRIEND:
							if(ansMsg.getAnswer()){
								LITUtil.infomation("删除成功",ansMsg.getContent());
								this.idList.remove(ansMsg.getContent());
								this.contactMap.remove(ansMsg.getContent());
								this.newMainFrame();
							}
							else
								LITUtil.error("删除失败", "请稍后重试", ansMsg.getContent());
							break;
						case AnswerMessage.ANS_MODIFY_INFO:
							if(ansMsg.getAnswer()){
								LITUtil.infomation("修改成功","个人资料已修改");
								this.newMainFrame();
							}else
								LITUtil.infomation("修改失败",ansMsg.getContent());
							break;
						}
					
				
					break;
				case(Message.MSG_REQUEST):
					RequestMessage reqMsg = (RequestMessage)inMsg;
					handleRequest(reqMsg);
					break;
				default:
					System.out.println("Unknown message type: "+ inMsg.getType());
				
			}
		}
	}
	private void handleRequest(RequestMessage msg){
		switch(msg.getRequestCode()){
		case(RequestMessage.REQ_ONLINE):
			String onID = msg.getFromID();
			this.mainFrame.setOnline(onID);			
			System.out.println("["+this.ID+"]"+onID+" is on line");
			break;
		case(RequestMessage.REQ_OFFLINE):
			String offID = msg.getFromID();
			this.mainFrame.setOffline(offID);
			System.out.println("["+this.ID+"]"+offID+" is off line");
			break;
		default:
			System.err.println("Error in handle request: unknown req code -"+ msg.getRequestCode());
		}
	}
	
	
	public void newMainFrame(){
		if(mainFrame!=null) mainFrame.dispose();
		new FriendsListUI(this);
	}
}

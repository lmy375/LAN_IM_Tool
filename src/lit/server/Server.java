package lit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import lit.object.MessageQueue;
import lit.object.messages.AnswerMessage;
import lit.object.messages.Message;
import lit.server.ui.ServerState;

public class Server {
	
	/**
	 * Boolean variable to mark whether server is started.
	 */
	private boolean isStarted = false;
	public boolean isStarted(){
		return this.isStarted;
	}
	
	/**
	 * Message queue. All message will be put into the message queue waiting to be handled.
	 */
	//MessageQueue messageQueue = null;
	/**
	 * Synchronized method. 
	 * @param msg
	 * @return
	 */
	//public boolean addToMessageQueue(Message msg){
	//	return this.messageQueue.putMessage(msg);
	//}

	/**
	 * HashMap to store <ID, client>. 
	 */
	public HashMap<String, HandleClient > clientMap = null;
	
	
	
	//String IP = "localhost";
	
	private int port = 8888;
	private ServerSocket serverSocket = null;
	private LITDB database = null;
	public LITDB getDatabase(){
		return this.database;
	}
	
	/**
	 * Visit database and check whether ID and password matches.
	 * @param ID
	 * @param password
	 * @return
	 */
	public boolean checkLogin(String ID, String password){
		try {
			
			boolean r =  database.checkPassword(ID, password);
			if (r==false) return false;
			if (clientMap.keySet().contains(ID)) this.clientMap.remove(ID);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 * Visit database and add a new user item.
	 * @param password
	 * @param name
	 * @param sex
	 * @return
	 */
	public String register(String password, String name , String sex){
		try {
			database.addUser("",name, password, sex);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	/**
	 * 
	 * @param port : port of ServerSocket.
	 */
	ServerState state;
	
	public Server(int port, ServerState state){
		this.port = port;
		this.state = state;
		isStarted = false;
		clientMap= new HashMap<String, HandleClient>();
	//	messageQueue = new MessageQueue();
		database = new LITDB("UserDataBase.mdb");
	}
	
	
	/**
	 * Add client to map , which means user log in.
	 * @param client
	 */
	public void addClientToMap(HandleClient client){
			// Client is a new Thread. Start to message loop.
		clientMap.put(client.getID(), client);
		state.changeUserCount(clientMap.size());
		
	}
	/**
	 * Remove client from map , which means user log out.
	 * @param client
	 */
	public void removeClientFromMap(HandleClient client){
		clientMap.remove(client.getID());
		state.changeUserCount(clientMap.size());
	}
	
	
	public void sendMessageToID(Message msg, String ID){
	//	System.out.println("Server sending:"+msg.getFromID()+"to"+ID);
		HandleClient client = clientMap.get(ID);
	//	System.out.println("Client null?"+ client==null);
		if(client!=null) client.sendMessage(msg);
	}
	
	
	/**
	 * Loop accepting new client in a new thread. After checking ID and password
	 */
	public void clientLoop(){
		new Thread(){
			public void run(){
				while(isStarted){
					try {
						//addClientToMap();
						Socket socket = serverSocket.accept();
						HandleClient client = new HandleClient(socket, Server.this);
						client.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						close();
					}
				}
			}
		}.start();		
	}
	
	
	/**
	 * Loop delivering message to target ID in a new thread.
	 */
	/*public void messageLoop(){
		new Thread(){
			public void run(){
				while(isStarted){
					Message msg = messageQueue.getMessage();
					if(msg!=null){
						HandleClient toClient = clientMap.get(msg.getToID());
						if(toClient!=null) toClient.sendMessage(msg);
					}
				}
			}
		}.start();			
	}*/
	
	
	public void start(){
		try {
			 if(serverSocket ==null||serverSocket.isClosed())
				 serverSocket = new ServerSocket(port);
			 isStarted = true;
			 this.log("Server is started.");
		     database.openCon();
			 this.clientLoop();
			// this.messageLoop();			 
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			if(!isStarted) return;
			isStarted = false;
			if(serverSocket!=null&&!serverSocket.isClosed()) serverSocket.close();			
			//System.out.println("Server is closed");
			this.log("Server is closed");
			database.closeCon();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Log to text box in State frame.
	 */
	public void log(String s){
		System.out.println(s);
		this.state.log(s);
		
	}
}

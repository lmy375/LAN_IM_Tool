package lit.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import lit.object.Message;

public class Client extends Thread{
	private String ID = null;
	private Socket socket = null;
	private ObjectInputStream input = null;
	private ObjectOutputStream output = null;
	private Server server= null;
	//private MessageQueue messageQueue = null;
	private String password = null;
	private boolean isStarted = false;
	public boolean isStarted(){
		return this.isStarted;
	}
	
	public String getID(){
		return this.ID;
	}
	public String getPassword(){
		return this.password;
	}
	//public boolean addToMessageQueue(Message msg){
	//	return this.messageQueue.putMessage(msg);
	//}
	
	public Client(Socket socket, Server server){
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
		
		
		Message msg = this.getMessage();
		this.ID = msg.getFromID();
		this.password = msg.getContent();	
		System.out.println("Client "+this.ID+ " "+ this.password);
	}	
	
	public synchronized void sendMessage(Message msg){
		try {
			output.writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Message getMessage() {
		try {
			return (Message) input.readObject();
		} catch(SocketException e){
			e.printStackTrace();
			this.close();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	
	public void close(){
		try {
			this.isStarted=false;
			this.socket.close();
			server.clientMap.remove(ID);
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
				if(inMsg!=null) server.addToMessageQueue(inMsg);			
		}
		this.isStarted = false;
	}
}

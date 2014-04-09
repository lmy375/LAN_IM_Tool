package lit.object;

import java.util.concurrent.LinkedBlockingQueue;

import lit.object.messages.Message;

public class MessageQueue extends LinkedBlockingQueue<Message>{
	public MessageQueue(){
		super();		
	}
	public synchronized boolean putMessage(Message msg){
		return this.add(msg);
	}
	public synchronized Message getMessage(){
		return this.poll();
	}
	
}

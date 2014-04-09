package lit.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;

import lit.object.ContactInfo;

public class testClient {
	public static void main(String [] args){
		LITClient client = new LITClient("localhost", 8888);
		System.out.println("Client connect "+ client.connect());
		client.login("123456", "1");
		
		LITClient client2 = new LITClient("localhost", 8888);
		client2.connect();
		client2.login("123457", "1");
		
		client.sendChatMessage("123457", "2012", "test string");
		client2.sendChatMessage("123456", "2011", "another testString");
		while(true);
			
		
		
		
		
		
		
		
		
		//client.register("123456", "Moon", "Male");
		//client.close();
//		System.out.println("------------");
//		LITClient client1 = new LITClient("localhost" , 8888);
//		System.out.println("Client 1 connect "+ client1.connect());		
//		System.out.println("Client 1 login "+ client1.login("11061159", "xxxxxx"));
//		System.out.println("Client 1 login "+ client1.login("11061159", "123456"));
//		
//		LITClient client2 = new LITClient("localhost" , 8888);
//		System.out.println("Client 2 connect "+ client2.connect());		
//		System.out.println("Client 2 login "+ client2.login("11061174", "123456"));
//		
//		client1.addFriend("11061174");
//		client1.sendChatMessage("11061174",DateFormat.getDateTimeInstance().format(new Date()),"Hello 11061174.");
//		client2.sendChatMessage("11061159",DateFormat.getDateTimeInstance().format(new Date()),"Hello 11061159.");
//		client1.sendChatMessage("11061174",DateFormat.getDateTimeInstance().format(new Date()),"I'm 11061159.");
//		client2.sendChatMessage("11061159",DateFormat.getDateTimeInstance().format(new Date()),"Nice to meet you.");
//		client1.requestContactList("11061159");
//		client1.requestInfo("11061174");
//		client1.removeFriend("11061174");
//		client1.logout();
//		
//		
//		while(true);
		
//		
//	//	System.out.println(client.register("XXXX", "NAME", "MALE"));
//		System.out.println("Client 1 login "+ client.login("11061159", "123456"));
//	//	System.out.println(client.sendMessa));
//	//	for(ContactInfo info:client.getContactMap().values()){
//	//		System.out.println(info.getID()+" "+info.getName()+" "+ info.getSex());
//	//	}
//		
//		
//	 	LITClient client2 = new LITClient("localhost", 8888);
//	 	System.out.println("Client 2 connect "+ client2.connect());
//		System.out.println("Client 2 login "+ client2.login("11061174", "123456"));
//
//		client2.sendChatMessage("11061159", DateFormat.getDateTimeInstance().format(new Date()), "This a test String");
//		client.sendChatMessage("11061159",  DateFormat.getDateTimeInstance().format(new Date()), "another test String");
//		
//		while(true);
	}
	
}

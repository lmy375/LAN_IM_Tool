package lit.object.messages;

import java.util.ArrayList;

public class ContactListMessage extends Message{
	ArrayList<String> idList;
	public ContactListMessage(ArrayList<String> idList, String toID){
		super(Message.MSG_USER_LIST,Message.ID_SERVER,toID);
		this.idList = idList;		
	}
	public ArrayList<String> getContactList(){ 
		return this.idList;
	}
	public void addContact(String contact){
		idList.add(contact);
	}
}

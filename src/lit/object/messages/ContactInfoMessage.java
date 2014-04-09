package lit.object.messages;

import lit.object.ContactInfo;

public class ContactInfoMessage extends Message{
	ContactInfo info;
	public ContactInfoMessage(ContactInfo info , String toID){
		super(Message.MSG_CONTACT_INFO, Message.ID_SERVER, toID );
		this.info = info;
	}
	public ContactInfo getInfo(){
		return this.info;
	}
}

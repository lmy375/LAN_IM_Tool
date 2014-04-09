package lit.object.messages;

public class RegisterMessage extends Message{
	private String password;
	private String name;
	private String sex;
	
	public RegisterMessage(String password, String name, String sex){
		super(Message.MSG_REGISTER,"",Message.ID_SERVER );
		this.password = password;
		this.name  = name;
		this.sex = sex;
	}
	
	public String getPassword(){
		return this.password;
	}
	public String getName(){
		return this.name;
	}
	public String getSex(){
		return this.sex;
	}
}

package lit.object.messages;

public class LoginMessage extends Message{
	String password;
	
	/**
	 * loginID : the ID to login.
	 * loginPassword: password that matches the ID.
	 * @param loginID
	 * @param loginPassword
	 */
	public LoginMessage(String loginID, String loginPassword){
		super(Message.MSG_LOGIN , loginID , Message.ID_SERVER);
		this.password = loginPassword;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return this.password;
	}
}

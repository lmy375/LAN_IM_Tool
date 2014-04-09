package lit.server;

public class Database {
	public Database(){
		
	}
	public boolean checkClient(String ID, String password){
		if(ID==null|password==null) return false;
		if(ID.equals("11061159")&&password.equals("123456"))
			return true;
		else if(ID.equals("11061174")&&password.equals("123456"))
			return true;
		else 
			return false;
	}
}

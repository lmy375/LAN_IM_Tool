package lit.object;

import java.io.Serializable;

public class ContactInfo implements Serializable{
	private String ID;
	private String name;
	private String sex;
	private String birthDay;
	public ContactInfo(String ID, String name , String sex , String birthDay){
		this.ID = ID;
		this.name = name;
		this.sex = sex;
		this.birthDay = birthDay;
	}
	public String getID(){
		return this.ID;
	}
	public String getName(){
		return this.name;
	}
	public String getSex(){
		return this.sex;
	}
	public String getBirthDay(){
		return this.birthDay;
	}
}

package lit.server;

import java.io.IOException;

import java.sql.*;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import lit.object.ContactInfo;


/**
 * 
 * @author V
 *
 */
public class LITDB {
	
	private Connection con = null;
	private Statement stat = null;
	private ResultSet rs = null;
	private String DBName = null;
	private ContactInfo userInfo = null;
	
	/**
	 * 
	 * @param DBName �����ݿ��ļ�����������׺
	 * ���캯�����������ݿ�
	 */
	public LITDB(String DBName)
	{
		this.DBName = DBName;
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			System.out.println("fffff");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * �������ݿ⣬Ĭ��Ϊ64λ��Driver
	 * ����һ��Connection�����������ӳɹ����
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Connection openCon() throws SQLException, IOException
	{
		try
		{
			System.out.println("open connection");
			String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=./" + DBName;
			System.out.println(url);
			con = DriverManager.getConnection(url);
			System.out.println("connection succeed");
//			stat=con.createStatement();
//			rs=stat.executeQuery("Select * FROM UserInfo");
//			System.out.println("sssssss");
		}
		catch(SQLException el)
		{
			el.printStackTrace();
		}
		return con;
	}
	
	/**
	 * �ر�����
	 * @throws SQLException
	 */
	public void closeCon() throws SQLException
	{
		if(con == null)
			System.out.println("con is not open");
			//JOptionPane.showMessageDialog(LITDB.this, "please open con");
		else
		{
			try
			{
				if(con != null)
					if(!con.isClosed())
						con.close();
//				if(stat != null)
//					if(!stat.isClosed())
//						stat.close();
//				if(rs != null)
//					if(!rs.isClosed())
//						rs.close();
				System.out.println("close succeed");
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ����û�Id�������Ƿ�ƥ��
	 * @param userName �û���
	 * @param userPW ����
	 * @return ƥ��ɹ�����true��ʧ�ܷ���false
	 * @throws SQLException
	 */
	public boolean checkPassword(String userId, String userPW) throws SQLException
	{
		String ID = null;
		String PW = null;
		
//		if(con == null)
//		{
//			System.out.println("please open con");
//			return false;
//		}
//		if(con.isClosed())
//			try {
//				this.openCon();
//			} catch (SQLException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
		
		stat = con.createStatement();
		rs = stat.executeQuery("SELECT user_id, password " +
				"FROM UserInfo " +
				"WHERE user_id = '" + userId + "'");
		//����**************************************************************************
		while(rs.next()){
			System.out.println("rrrrrrrr");
			ID = rs.getString(1); //������ݿ��һ��
			PW = rs.getString(2); 
			System.out.println("user id:" + ID); //�����Ϣ
			System.out.println("password:" + PW);
		}
		System.out.println("read table succeed");
		if(userPW.equals(PW))
			return true;
		else
			return false;
	}
	
	/**
	 * ��������
	 * @param userId �û��˺ţ�ID��
	 * @param oldPW ������
	 * @param newPW ������
	 * @return ������ľ����������ݿ��е�������ͬʱ���������ݿ������룬
	 * ������true�����򷵻�false
	 * @throws SQLException
	 */
	public boolean changePassword(String userId, String oldPW, String newPW) throws SQLException
	{
		String id = null;
		String PW = null;
		
//		if(con == null)
//		{
//			System.out.println("please open con");
//			return false;
//		}
//		if(con.isClosed())
//			try {
//				this.openCon();
//			} catch (SQLException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
		stat = con.createStatement();
		rs = stat.executeQuery("SELECT user_id, password " +
				"FROM UserInfo " +
				"WHERE user_id = '" + userId + "'");
		//test**************************************************************
		while(rs.next()){
			System.out.println("rrrrrrrr");
			id = rs.getString(1); //������ݿ��һ��
			PW = rs.getString(2); 
			System.out.println("user id:" + id); //�����Ϣ
			System.out.println("old password:" + PW);
		}
		System.out.println("read table succeed");
		if(oldPW.equals(PW))
		{
			//update password
			stat = con.createStatement();
			stat.executeUpdate("UPDATE UserInfo " +
					"SET password = '" + newPW + "'" +
					"WHERE user_id = '" + userId + "'");
			rs = stat.executeQuery("SELECT user_id, password FROM UserInfo");
			//test*********************************************************************************
			while(rs.next()){
				System.out.println("rrrrrrrr");
				id = rs.getString(1); //������ݿ��һ��
				PW = rs.getString(2); 
				System.out.println("user id:" + id); //�����Ϣ
				System.out.println("new password:" + PW);
			}
			return true;
		}
		else
			return false;
	}
	
	/**
	 * �����û��˺Ų�ѯ�û���Ϣ�����ڷ�������������˵��
	 * @param userId �û��˺�
	 * @return ����һ��ContactInfo���Ǹ��û�����Ϣ
	 * @throws SQLException
	 */
	public ContactInfo queryUser(String userId) throws SQLException
	{
		String id = null;
		String name = null;
		String sex = null;
		String birthDay = null;
		stat = con.createStatement();
		rs = stat.executeQuery("SELECT * " +
				"FROM UserInfo " +
				"WHERE user_id = '" + userId + "'");
		//test*****************************************************************
		while(rs.next()){
			System.out.println("rrrrrrrr");
			id = rs.getString(1); //������ݿ��һ��
			name = rs.getString(2);
			sex = rs.getString(5); 
			birthDay = rs.getString(6);
			System.out.println("user id:" + id); //�����Ϣ
			System.out.println("name:" + name);
			System.out.println("sex:" + sex+"\nbirthday:"+ birthDay);
			
		}
		
		userInfo = new ContactInfo(id, name, sex, birthDay);
		return userInfo;
	}
	
	/**
	 * �����û��˺Ų�ѯ�û��ǳ�
	 * @param userId �û��˺�
	 * @return �û����ǳ�
	 * @throws SQLException
	 */
	public String queryName(String userId) throws SQLException
	{
		String name = null;
		stat = con.createStatement();
		rs = stat.executeQuery("SELECT user_name " +
				"FROM UserInfo " +
				"WHERE user_id = '" + userId + "'");
		//test********************************************************************
		while(rs.next())
		{
			name = rs.getString(1);
			System.out.println("name :" + name);
		}
		return name;
	}
	
	/**
	 * ��ѯ�����б�
	 * @param userId �û��˺�
	 * @return һ��ArrayList��������Ϊ���û������б�
	 * @throws SQLException
	 */
	public ArrayList<String> queryFriList(String userId) throws SQLException
	{
		String friListName = null;
		String friendId = null;
		ArrayList<String> friId = new ArrayList<String>();
		
		stat = con.createStatement();
//			rs = stat.executeQuery("SELECT user_id, friend_list " +
//					"FROM UserInfo " +
//					"WHERE user_id = '" + userId + "'");
//		//test****************************************************************************
//		while(rs.next())
//		{
//			friListName = rs.getString(2);
//			System.out.println("friListName :" + friListName);
//		}
		
		friListName = userId + "_Friends";
		System.out.println("friend List :" + friListName);
		rs = stat.executeQuery("SELECT * FROM " + friListName);
		while(rs.next())
		{
			friendId = rs.getString(1);
			System.out.println("friend ID :" + friendId);
			friId.add(friendId);
		}
		return friId;
	}
	
	/**
	 * ��ѯ�����û���id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> queryAllUserId() throws SQLException
	{
		String userId;
		ArrayList<String> allUser = new ArrayList<String>();
		stat = con.createStatement();
		
		rs = stat.executeQuery("SELECT user_id FROM UserInfo");
		while(rs.next())
		{
			userId = rs.getString(1);
			System.out.println("user ID :" + userId);
			allUser.add(userId);
		}
		return allUser;
	}
	
	/**
	 * ��ѯĳ���Ƿ����
	 * @param tableName �ñ������
	 * @return ���ڷ���true������false
	 * @throws SQLException
	 */
	public boolean hasTable(String tableName) throws SQLException//�����ĳ�private
	{
		DatabaseMetaData meta = con.getMetaData();

		rs = meta.getTables(null , null, tableName, null);

		System.out.println("sssssss");
		if(rs.next())
		{
		    System.out.println("����");
			return true;
		}
		else
		{
			System.out.println("������");
			return false;
		}
	}
	
	/**
	 * ���������б������г�ʼ����
	 * @param friListName �ñ������
	 * @return ����hasTable�������������ɹ�����ñ����ڣ�����true
	 * @throws SQLException
	 */
	public boolean createFriList(String friListName) throws SQLException//�����ĳ�private
	{
		stat = con.createStatement();
		if(!this.hasTable(friListName))
		{
			stat.execute("CREATE TABLE " + friListName + "(user_id varchar(10) primary key)");
			if(this.hasTable(friListName))
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	/**
	 * ɾ�������б�ֻ����ɾ���û�ʱ���õ���
	 * @param friListName �ñ������
	 * @return ����hasTable��������ɾ���ɹ�����ñ����ڣ�����true
	 * @throws SQLException
	 */
	public boolean delFriList(String friListName) throws SQLException//�����ĳ�private
	{
		stat = con.createStatement();
		if(this.hasTable(friListName))
		{
			stat.execute("DROP TABLE " + friListName);
			if(!this.hasTable(friListName))
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	/**
	 * �����û�������Ӧ�ĺ����б�
	 * @param userId ��������������ӷ���������Ա��ӣ�����ôһ��Ϊ�գ�""����ϵͳ���Զ�����
	 * @param userName �û��������ظ�
	 * @param PW ���룬����ǹ���Ա��ӣ���Ĭ��Ϊ000000
	 * @param sex
	 * @return
	 * @throws SQLException
	 */
	public String addUser(String userId, String userName, String PW, String sex) throws SQLException
	{
		int id;
		String birthday = "";
		if(userId.equals(""))
		{
			Random rand = new Random();
			id = rand.nextInt(900000) + 100000;
			userId = new Integer(id).toString();
			while(this.checkId(userId))
			{
				id = rand.nextInt(900000)+100000;
				userId = new Integer(id).toString();
			}
		}
		
		String friList = userId + "_Friends";
		stat = con.createStatement();
		int i = stat.executeUpdate("INSERT INTO UserInfo " +
				"VALUES ('" + userId + "', '" + userName + "', '" + PW 
				+ "', '" + friList + "', '" + sex + "', '" + birthday + "')");
		boolean b = this.createFriList(friList);
		if(i == 1 && b)
			return userId;
		else
			return "";
	}
	
	/**
	 * ɾ���û�������Ϣ����Ӧ�ĺ����б�����Ա����Ȩ��?������������жϣ�
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public boolean delUser(String userId) throws SQLException
	{
		stat = con.createStatement();
		int i = stat.executeUpdate("DELETE FROM UserInfo " +
				"WHERE user_id = '" + userId + "'");
		boolean b = this.delFriList(userId + "_Friends");
		if (i == 1 && b)
			return true;
		else
			return false;
	}
	
	/**
	 * �����û��ǳ�
	 * @param userId
	 * @param newName �µ��ǳ�
	 * @return
	 * @throws SQLException
	 */
	public boolean changeUserName(String userId, String newName) throws SQLException
	{
		stat = con.createStatement();
		rs = stat.executeQuery("SELECT user_id, user_name " +
				"FROM UserInfo " +
				"WHERE user_id = '" + userId + "'");
		while(rs.next()){
			System.out.println("jjjjjjjjjjj");
			String id = rs.getString(1); //������ݿ��һ��
			String oldName = rs.getString(2); 
			System.out.println("user id:" + id); //�����Ϣ
			System.out.println("old name:" + oldName);
		}
		
		//update name
		stat = con.createStatement();
		int i = stat.executeUpdate("UPDATE UserInfo " +
				"SET user_name = '" + newName + "'" +
				"WHERE user_id = '" + userId + "'");
		if (i == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * ���Ӻ���
	 * @param userId �û�id
	 * @param friId ����id
	 * @return
	 * @throws SQLException
	 */
	public boolean addFriend(String userId, String friId) throws SQLException
	{
		String friListName = userId + "_Friends";
		stat = con.createStatement();
		int i = stat.executeUpdate("INSERT INTO " + friListName +
				" VALUES ('" + friId + "')");
		if (i == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * ɾ������
	 * @param userId
	 * @param friId
	 * @return
	 * @throws SQLException
	 */
	public boolean delFriend(String userId, String friId) throws SQLException
	{
		String friListName = userId + "_Friends";
		stat = con.createStatement();
		int i = stat.executeUpdate("DELETE FROM " + friListName +
				" WHERE user_id = '" + friId + "'");
		if (i == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * �����û���Ϣ
	 * @param newCi �����µ��û���Ϣ
	 * @return �����޸ĺ���û���Ϣ
	 * @throws SQLException
	 */
	public ContactInfo updateUser(ContactInfo newCi) throws SQLException
	{
		ContactInfo ci = null;
		stat = con.createStatement();
		
		String userId = newCi.getID();
		String newName = newCi.getName();
		String newSex = newCi.getSex();
		String birth = newCi.getBirthDay();
		
		stat.executeUpdate("UPDATE UserInfo SET user_name = '" + newName + "' , gender = '" +
				newSex + "', birthday ='"+birth+"' WHERE user_id = '" + userId + "'");
		
		ci = this.queryUser(userId);
		return ci;
	}
	
	/**
	 * ���ĳһid�Ƿ����
	 * @param userId ��id
	 * @return �����򷵻�true
	 * @throws SQLException
	 */
	public boolean checkId(String userId) throws SQLException
	{
		ArrayList<String> allUserId = null;
		allUserId = queryAllUserId();
		for(int i=0;i<allUserId.size();i++)
		{
			if(allUserId.get(i).equals(userId))
				return true;
		}
		return false;
	}
	
	/**
	 * �ж�ĳһ�����Ƿ��ں����б���
	 * @param userId �û�id
	 * @param friId ����id
	 * @return ���򷵻�true
	 * @throws SQLException
	 */
	public boolean checkFri(String userId, String friId) throws SQLException
	{
		ArrayList<String> allFriId = null;
		allFriId = queryFriList(userId);
		for(int i=0;i<allFriId.size();i++)
		{
			if(allFriId.get(i).equals(friId))
				return true;
		}
		return false;
	}
	
	/**
	 * �ж��ַ����Ƿ�ȫ�����������
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str)
	{
		for (int i = str.length();--i>=0;)
		{   
			if (!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	
	
	
	
	
	
	
	
}

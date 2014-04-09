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
	 * @param DBName 是数据库文件名，包括后缀
	 * 构造函数不链接数据库
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
	 * 链接数据库，默认为64位的Driver
	 * 返回一个Connection对象，无论链接成功与否
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
	 * 关闭链接
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
	 * 检查用户Id、密码是否匹配
	 * @param userName 用户名
	 * @param userPW 密码
	 * @return 匹配成功返回true，失败返回false
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
		//测试**************************************************************************
		while(rs.next()){
			System.out.println("rrrrrrrr");
			ID = rs.getString(1); //获得数据库第一列
			PW = rs.getString(2); 
			System.out.println("user id:" + ID); //输出信息
			System.out.println("password:" + PW);
		}
		System.out.println("read table succeed");
		if(userPW.equals(PW))
			return true;
		else
			return false;
	}
	
	/**
	 * 更改密码
	 * @param userId 用户账号（ID）
	 * @param oldPW 旧密码
	 * @param newPW 新密码
	 * @return 当输入的旧密码与数据库中的密码相同时，更改数据库中密码，
	 * 并返回true；否则返回false
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
			id = rs.getString(1); //获得数据库第一列
			PW = rs.getString(2); 
			System.out.println("user id:" + id); //输出信息
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
				id = rs.getString(1); //获得数据库第一列
				PW = rs.getString(2); 
				System.out.println("user id:" + id); //输出信息
				System.out.println("new password:" + PW);
			}
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 根据用户账号查询用户信息（至于返不返回密码另说）
	 * @param userId 用户账号
	 * @return 返回一个ContactInfo，是该用户的信息
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
			id = rs.getString(1); //获得数据库第一列
			name = rs.getString(2);
			sex = rs.getString(5); 
			birthDay = rs.getString(6);
			System.out.println("user id:" + id); //输出信息
			System.out.println("name:" + name);
			System.out.println("sex:" + sex+"\nbirthday:"+ birthDay);
			
		}
		
		userInfo = new ContactInfo(id, name, sex, birthDay);
		return userInfo;
	}
	
	/**
	 * 根据用户账号查询用户昵称
	 * @param userId 用户账号
	 * @return 用户的昵称
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
	 * 查询好友列表
	 * @param userId 用户账号
	 * @return 一个ArrayList，其内容为该用户好友列表
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
	 * 查询所有用户的id
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
	 * 查询某表是否存在
	 * @param tableName 该表的名字
	 * @return 存在返回true，否则false
	 * @throws SQLException
	 */
	public boolean hasTable(String tableName) throws SQLException//将来改成private
	{
		DatabaseMetaData meta = con.getMetaData();

		rs = meta.getTables(null , null, tableName, null);

		System.out.println("sssssss");
		if(rs.next())
		{
		    System.out.println("存在");
			return true;
		}
		else
		{
			System.out.println("不存在");
			return false;
		}
	}
	
	/**
	 * 创建好友列表（不进行初始化）
	 * @param friListName 该表的名字
	 * @return 调用hasTable函数，若创建成功，则该表会存在，返回true
	 * @throws SQLException
	 */
	public boolean createFriList(String friListName) throws SQLException//将来改成private
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
	 * 删除好友列表（只有在删除用户时才用到）
	 * @param friListName 该表的名称
	 * @return 调用hasTable函数，若删除成功，则该表不存在，返回true
	 * @throws SQLException
	 */
	public boolean delFriList(String friListName) throws SQLException//将来改成private
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
	 * 增加用户，和相应的好友列表
	 * @param userId 如果不是特殊的添加方法（管理员添加），那么一律为空（""），系统会自动生成
	 * @param userName 用户名，可重复
	 * @param PW 密码，如果是管理员添加，则默认为000000
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
	 * 删除用户所有信息和相应的好友列表，管理员才有权限?（后续会加上判断）
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
	 * 更改用户昵称
	 * @param userId
	 * @param newName 新的昵称
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
			String id = rs.getString(1); //获得数据库第一列
			String oldName = rs.getString(2); 
			System.out.println("user id:" + id); //输出信息
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
	 * 增加好友
	 * @param userId 用户id
	 * @param friId 好友id
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
	 * 删除好友
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
	 * 更新用户信息
	 * @param newCi 输入新的用户信息
	 * @return 返回修改后的用户信息
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
	 * 检查某一id是否存在
	 * @param userId 该id
	 * @return 存在则返回true
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
	 * 判断某一好友是否在好友列表中
	 * @param userId 用户id
	 * @param friId 好友id
	 * @return 在则返回true
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
	 * 判断字符串是否全部由数字组成
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

package lit.server.ui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JList;


import lit.object.ContactInfo;
import lit.server.LITDB;


public class TestFrame extends JFrame {

	private JPanel contentPane;
	
	private LITDB litdb = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//具体何时new一个LITDB，再说
		JButton conButton = new JButton("openCon");
		conButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				litdb = new LITDB("UserDataBase.mdb");
				try {
					litdb.openCon();
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		conButton.setBounds(43, 10, 93, 23);
		contentPane.add(conButton);
		
		JButton closeButton = new JButton("closeCon");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(litdb != null)
				{
					try {
						litdb.closeCon();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					System.out.println("please create litdb(open con)");
				}
			}
		});
		closeButton.setBounds(43, 54, 93, 23);
		contentPane.add(closeButton);
		
		JButton checkPWButton = new JButton("checkPassword");
		checkPWButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(litdb != null)
				{
					try {
						System.out.println(litdb.checkPassword("事实上", "123ab"));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
					System.out.println("please create litdb(open con)");
			}
		});
		checkPWButton.setBounds(43, 92, 154, 23);
		contentPane.add(checkPWButton);
		
		JButton changePWButton = new JButton("changePassword");
		changePWButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println(litdb.changePassword("123456", "123ab", "1212a"));
					System.out.println("change pw succeed");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		changePWButton.setBounds(43, 136, 154, 23);
		contentPane.add(changePWButton);
		
		JButton qUserButton = new JButton("queryUser");
		qUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContactInfo ci = null;
				try {
					ci = litdb.queryUser("123456");
					System.out.println("id: " + ci.getID() + " name: " + ci.getName() + " sex:" + ci.getSex());
					System.out.println("query user succeed");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		qUserButton.setBounds(43, 176, 93, 23);
		contentPane.add(qUserButton);
		
		JButton queryFriListButton = new JButton("queryFriList");
		queryFriListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<String> a = litdb.queryFriList("123456");
					System.out.println("fri1: " + a.get(0) + " fri2: " + a.get(1));
					System.out.println("query friend list succeed");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		queryFriListButton.setBounds(43, 216, 130, 23);
		contentPane.add(queryFriListButton);
		
		JButton queryNameButton = new JButton("queryName");
		queryNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("name is: " + litdb.queryName("123456"));
					System.out.println("query name succeed");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		queryNameButton.setBounds(189, 10, 122, 23);
		contentPane.add(queryNameButton);
		
		JButton addUserButton = new JButton("addUser");
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("add user succeed? " + litdb.addUser("", "ewtwee", "369", "F"));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		addUserButton.setBounds(218, 54, 93, 23);
		contentPane.add(addUserButton);
		
		JButton delUserButton = new JButton("delUser");
		delUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("delete user succeed? " + litdb.delUser("123459"));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		delUserButton.setBounds(218, 92, 93, 23);
		contentPane.add(delUserButton);
		
		JButton btnHastable = new JButton("hasTable");
		btnHastable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("has Table?" + litdb.hasTable("12346_Friends"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnHastable.setBounds(218, 136, 93, 23);
		contentPane.add(btnHastable);
		
		JButton btnCreatefrilist = new JButton("createFriList");
		btnCreatefrilist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("creatFriendList " + litdb.createFriList("123423_fri"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnCreatefrilist.setBounds(218, 176, 111, 23);
		contentPane.add(btnCreatefrilist);
		
		JButton btnDelfrilist = new JButton("delFriList");
		btnDelfrilist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("deleteFriendList " + litdb.delFriList("123423_fri"));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDelfrilist.setBounds(218, 216, 93, 23);
		contentPane.add(btnDelfrilist);
		
		JButton btnChangeusername = new JButton("changeUserName");
		btnChangeusername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.print("changeUserName? " + litdb.changeUserName("123458", "的光和热"));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnChangeusername.setBounds(345, 10, 134, 23);
		contentPane.add(btnChangeusername);
		
		JButton btnAddfriend = new JButton("addFriend");
		btnAddfriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.print("addFriend? " + litdb.addFriend("123456", "123458"));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAddfriend.setBounds(345, 54, 93, 23);
		contentPane.add(btnAddfriend);
		
		JButton btnDelfriend = new JButton("delFriend");
		btnDelfriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.print("delFriend? " + litdb.delFriend("123456", "123458"));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDelfriend.setBounds(345, 92, 93, 23);
		contentPane.add(btnDelfriend);
		
		JButton btnShowdbframe = new JButton("ShowDbFrame");
		btnShowdbframe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ShowDbFrame sdf = new ShowDbFrame(litdb);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnShowdbframe.setBounds(345, 136, 111, 23);
		contentPane.add(btnShowdbframe);
		
		JButton btnUpdateuser = new JButton("updateUser");
		btnUpdateuser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContactInfo ci = null;
				try {
					ci = litdb.updateUser(new ContactInfo("123457", "色鬼", "F",""));
					System.out.println("id: " + ci.getID() + " name: " + ci.getName() + " sex:" + ci.getSex());
					System.out.println("update user succeed");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUpdateuser.setBounds(345, 176, 93, 23);
		contentPane.add(btnUpdateuser);
	}
}

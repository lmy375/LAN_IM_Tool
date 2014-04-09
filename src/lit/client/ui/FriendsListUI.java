package lit.client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import javax.swing.*;

import lit.client.LITClient;
import lit.object.ContactInfo;

public class FriendsListUI extends JFrame implements MouseListener {
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		FriendsListUI friendlistUI = new FriendsListUI();
//	}
//	
	LITClient client=null;
	
	
	
	ArrayList<ContactInfo> contactList;
	
	/* 定义组件 */

	private JPanel jp, jpList, jpTop;
	private JLabel jl1, jl2;
	private JLabel jl[] = new JLabel[100];  //好友
	private JScrollPane jsp1;
	
	private String nickName = "aaaaaa";  //昵称
	
	
	public FriendsListUI(LITClient client) {
		this.client = client;
		client.setMainFrame(this);
		
		this.nickName = client.getSelfInfo().getName();
	
		contactList = new ArrayList<ContactInfo>();
		for(ContactInfo info:client.getContactMap().values())
			contactList.add(info);
		jl1 = new JLabel(new ImageIcon("image/001.jpg"));  //头像
		jp = new JPanel(new BorderLayout());
		jpList = new JPanel(new GridLayout(contactList.size(), 1, 4, 4));
		jpTop = new JPanel(new GridLayout(2, 1));
		
		jl2 = new JLabel("我的好友");	
		jl2.setForeground(Color.BLUE);
		jl1.setText(nickName);
		jl1.setHorizontalAlignment(SwingConstants.LEFT);
		
		jpTop.add(jl1);
		jpTop.add(jl2);
		
		// 初始化好友	
		for (int i = 0; i < contactList.size(); i++) {
			jl[i] = new JLabel(contactList.get(i).getName(), new ImageIcon("image/005.jpg"),
					JLabel.LEFT);
			//jl[i].setForeground(Color.gray);    //灰色表示不在线
			jl[i].setEnabled(false);
			jl[i].addMouseListener(this);
			jpList.add(jl[i]);
		}

		jsp1 = new JScrollPane(jpList);// 把fjpList放到可以滚动的JScrollPane里
		jp.add(jpTop, "North");
		jp.add(jsp1, "Center");
		
		jl1.addMouseListener(this);

		
		//this.add(jp);
		this.setContentPane(jp);
		this.setTitle("LIT");
		this.setSize(300, 590);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		client.checkAllOnline();
	}
	
	
	
	/**
	 * Set message that received from a contact.
	 * @param ID
	 * @param num
	 */
	public void setMessageNumber(String ID, String num){
		for(int i = 0;i<contactList.size();i++){
			if (contactList.get(i).getID().equals(ID)){
				jl[i].setEnabled(true);
				jl[i].setText(contactList.get(i).getName()+ num  );
			}
		}
	}
	/**
	 * 
	 * Set contact as online. 
	 * @param ID
	 */
	public void setOnline(String ID){
		for(int i = 0;i<contactList.size();i++){
			if (contactList.get(i).getID().equals(ID))
				jl[i].setEnabled(true);
		}
	}
	/**
	 * Set contact as offline.
	 * @param ID
	 */
	public void setOffline(String ID){
		for(int i = 0;i<contactList.size();i++){
			if (contactList.get(i).getID().equals(ID))
				jl[i].setEnabled(false);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == jl1 && e.getButton() == MouseEvent.BUTTON3) {
			MyPopupMenu1 popup = new MyPopupMenu1(this);
			popup.show((Component) jl1, e.getX(), e.getY());
		} 
		
		else if(e.getButton() == MouseEvent.BUTTON3) {
			for(int i = 0; i < contactList.size(); i++)
				if(e.getSource() == jl[i]) {
					MyPopupMenu2 popup = new MyPopupMenu2(this, i, contactList);
					popup.show((Component) jl[i], e.getX(), e.getY());
				}
		}
	}
	
	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}
	
	public void setNickname(String name) {
		this.nickName = name;
	}
	
	public String getNickname() {
		return nickName;
	}
	
	//右键自己的头像时
	class MyPopupMenu1 extends JPopupMenu implements ActionListener {
		JMenuItem myInfo, addContact, refresh;
		FriendsListUI frdlist;

		public MyPopupMenu1(FriendsListUI friendlistUI) {
			frdlist = friendlistUI;
			myInfo = new JMenuItem("我的资料");
			addContact = new JMenuItem("添加好友");
			refresh = new JMenuItem("刷新");
			myInfo.addActionListener(this);
			addContact.addActionListener(this);
			refresh.addActionListener(this);
			add(myInfo);
			this.addSeparator();
			add(addContact);
			this.addSeparator();
			add(refresh);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == myInfo)
				new MyInfo(client);
			else if (e.getSource() == addContact) {
				new AddContact(client);
				
			}else if(e.getSource()==refresh){
				client.newMainFrame();
			}

		}
	}
	
	//右键好友的头像
	class MyPopupMenu2 extends JPopupMenu implements ActionListener {
		JMenuItem chat , seeInfo, delete;
		FriendsListUI frdlist;
		int friendNO = 0;  //具体对哪个好友进行操作

		public MyPopupMenu2(FriendsListUI friendlistUI, int i, ArrayList<ContactInfo> contactList) {
			friendNO = i;
			frdlist = friendlistUI;  //暂时好像没什么用，先摆着吧。
			chat = new JMenuItem("发起会话");
			seeInfo = new JMenuItem("查看资料");
			delete = new JMenuItem("删除该好友");
			chat.addActionListener(this);
			seeInfo.addActionListener(this);
			delete.addActionListener(this);
			add(chat);
			this.addSeparator();
			add(seeInfo);
			this.addSeparator();
			add(delete);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == chat) {
				System.out.println("发起与" + contactList.get(friendNO).getName() +  "会话!");
				
				new ChatFrame(contactList.get(friendNO).getID(),contactList.get(friendNO).getName(), client);    //把联系人列表和第几个联系人发过去。
				//frdlist.display.setText("Hello!");
				
			}
			else if (e.getSource() == seeInfo) {
				System.out.println("查看"+ contactList.get(friendNO).getName() + "的资料!");
				
				ContactInfo info = client.getContactMap().get(contactList.get(friendNO).getID());
				new ContactInfoUI(info);
				
			}
			else if (e.getSource() == delete) {
				System.out.println("删除该好友!");
				int select = JOptionPane.showOptionDialog(null, "是否删除账号为" + contactList.get(friendNO).getID() +
						"的好友？", "WARNING", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE, null, null, null);
				if(select==JOptionPane.YES_OPTION)
				{
					client.removeFriend(contactList.get(friendNO).getID());
				}
				
				
			}
		}
	}
}



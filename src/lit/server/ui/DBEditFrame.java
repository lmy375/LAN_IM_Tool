package lit.server.ui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import lit.object.ContactInfo;
import lit.server.LITDB;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;


public class DBEditFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	
	private ContactInfo ci;
	private ShowDbFrame sdf;
	private LITDB litdb;
	
	private String userId;
	private String userName;
	private String sex;

	private String newUserId;
	private String newUserName;
	private String newSex;
	private JTextField idField;
	private JTextField nameField;
	private JTextField sexField;
//	private JTextField idField;
//	private JTextField nameField;
//	private JTextField sexField;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					dbEditFrame frame = new dbEditFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public DBEditFrame(ContactInfo ci, LITDB litdb, ShowDbFrame sdf) {
		getContentPane().setLayout(null);
		
//		JLabel label = new JLabel("\u7528\u6237\u8D26\u53F7");
//		label.setBounds(34, 13, 54, 15);
//		getContentPane().add(label);
//		
//		JLabel label_1 = new JLabel("\u7528\u6237\u540D");
//		label_1.setBounds(34, 63, 54, 18);
//		getContentPane().add(label_1);
//		
//		JLabel label_2 = new JLabel("\u6027\u522B");
//		label_2.setBounds(34, 112, 54, 15);
//		getContentPane().add(label_2);
//		
//		idField = new JTextField();
//		idField.setBounds(117, 10, 127, 21);
//		getContentPane().add(idField);
//		idField.setColumns(10);
//		
//		nameField = new JTextField();
//		nameField.setBounds(117, 62, 127, 21);
//		getContentPane().add(nameField);
//		nameField.setColumns(10);
//		
//		sexField = new JTextField();
//		sexField.setBounds(117, 109, 127, 21);
//		getContentPane().add(sexField);
//		sexField.setColumns(10);
//		
//		JButton button = new JButton("\u4FDD\u5B58");
//		button.setBounds(34, 159, 93, 23);
//		getContentPane().add(button);
//		
//		JButton button_1 = new JButton("\u9000\u51FA");
//		button_1.setBounds(151, 159, 93, 23);
//		getContentPane().add(button_1);
//		getContentPane().setLayout(null);
//		
		this.ci = ci;
		this.litdb = litdb;
		this.sdf = sdf;
		userId = ci.getID();
		userName = ci.getName();
		sex = ci.getSex();
		
		
		setTitle("编辑用户信息");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 291, 236);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("用户账号");
		label.setBounds(30, 29, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("用户名");
		label_1.setBounds(30, 72, 54, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("性别");
		label_2.setBounds(30, 122, 54, 15);
		contentPane.add(label_2);
		
		idField = new JTextField();
		idField.setBounds(131, 26, 108, 21);
		contentPane.add(idField);
		idField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setBounds(131, 69, 108, 21);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		sexField = new JTextField();
		sexField.setBounds(131, 119, 108, 21);
		contentPane.add(sexField);
		sexField.setColumns(10);
		
		JButton button = new JButton("保存");
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//				if(idField.getText()!=null)
//					newUserId = idField.getText();
//				else
//					newUserId = userId;
//				if(nameField.getText()!=null)
//					newUserName = nameField.getText();
//				else
//					newUserName = userName;
//				if(sexField.getText()!=null)
//					newSex = sexField.getText();
//				else
//					newSex = sex;
//				
//				litdb.updateUser(ci);
//				
//				dispose();
//			}
//		});
		
		button.addActionListener(this);
		
		
		button.setBounds(22, 164, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("退出");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_1.setBounds(157, 164, 93, 23);
		contentPane.add(button_1);
//		
//		textField = new JTextField();
//		textField.setBounds(147, 10, 66, 21);
//		contentPane.add(textField);
//		textField.setColumns(10);
		this.setVisible(true);
		
		
		
	}
	
	public ContactInfo getNewContactInfo()
	{
		ci = new ContactInfo(newUserId, newUserName, newSex,"");
		return ci;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("ci.getID()" + ci.getID());
		if(!idField.getText().equals(""))
			newUserId = idField.getText();
		else
			newUserId = userId;
		if(!nameField.getText().equals(""))
			newUserName = nameField.getText();
		else
			newUserName = userName;
		if(!sexField.getText().equals(""))
			newSex = sexField.getText();
		else
			newSex = sex;
		System.out.println("newUserId" + newUserId);
		System.out.println(newUserId + newUserName + newSex);
		
		ci = new ContactInfo(newUserId, newUserName, newSex,"");
		
		System.out.println("new ci " + ci.getID() + ci.getName() + ci.getSex());
		
		try {
			litdb.updateUser(ci);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sdf.refresh();
		dispose();
	}
	
	
}

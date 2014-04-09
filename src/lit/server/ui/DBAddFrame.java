package lit.server.ui;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import lit.object.ContactInfo;
import lit.server.LITDB;


public class DBAddFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField idField;
	private JTextField nameField;
	private JTextField sexField;
	
	private LITDB litdb;
	private ShowDbFrame sdf;
	
//	private ContactInfo ci;
	private String userId;
	private String userName;
	private String sex;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					dbAddFrame frame = new dbAddFrame();
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
	public DBAddFrame(LITDB litdb, ShowDbFrame sdf) {
		this.litdb = litdb;
		this.sdf = sdf;
		
		setTitle("添加新用户");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 276, 242);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		
		JLabel label = new JLabel("\u7528\u6237\u8D26\u53F7");
		label.setBounds(29, 23, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u7528\u6237\u540D");
		label_1.setBounds(29, 67, 54, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u6027\u522B");
		label_2.setBounds(29, 119, 54, 15);
		contentPane.add(label_2);
		
		idField = new JTextField();
		idField.setBounds(131, 20, 100, 21);
		contentPane.add(idField);
		idField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setBounds(131, 64, 100, 21);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		sexField = new JTextField();
		sexField.setBounds(131, 116, 100, 21);
		contentPane.add(sexField);
		sexField.setColumns(10);
		
		userId = idField.getText();
		userName = nameField.getText();
		sex = sexField.getText();
		
		JButton saveButton = new JButton("保存");
		saveButton.setBounds(29, 170, 93, 23);
		contentPane.add(saveButton);
		saveButton.addActionListener(this);
		
		JButton exitButton = new JButton("退出");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exitButton.setBounds(131, 170, 93, 23);
		contentPane.add(exitButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try
		{
			userId = idField.getText();
			userName = nameField.getText();
			sex = sexField.getText();
			
			System.out.println("add userid, name, sex: " + userId + userName + sex);
			if(userId.equals("")||userName.equals("")||sex.equals(""))
			{
				JOptionPane.showMessageDialog(null, "请将信息填写完整");
			}
			else if(litdb.checkId(userId))
			{
				JOptionPane.showMessageDialog(null, "该用户账号已经存在，请重新输入");
			}
			else if((userId.length()!=6)||(!litdb.isNumeric(userId)))
			{
				JOptionPane.showMessageDialog(null, "用户账号必须是六位数字");
			}
			else
			{
//				ci = new ContactInfo(userId, userName, sex);
				litdb.addUser(userId, userName, "000000", sex);
				sdf.refresh();
				dispose();
			}
		} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		}
	}

}

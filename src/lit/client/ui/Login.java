package lit.client.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import lit.client.LITClient;




public class Login extends JFrame {
	LITClient client;
	
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new Login();
//
//	}
	
	private JLabel title = new JLabel("登录窗口");
	private JLabel name = new JLabel("LIT帐号: ");
	private JLabel password = new JLabel("密码: ");
	private JTextField jtfNameInput = new JTextField(15);
	private JPasswordField jtfPasswordInput = new JPasswordField(15);
	private JButton jbtReg = new JButton("注册");
	private JButton jbtOK = new JButton("登录");
	private JButton jbtCancel = new JButton("取消");

	JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));	
	JPanel p2 = new JPanel(new GridBagLayout());
	JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));

	
	public Login(LITClient client) {
		this.client = client;
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.weightx = 100;
		constraints.weighty = 100;
		
		p1.add(title);
		
		addComponent(name, constraints, 1, 1, 1, 1);
		addComponent(password, constraints, 1, 2, 1, 1);
		addComponent(jtfNameInput, constraints, 2, 1, 1, 1);
		addComponent(jtfPasswordInput, constraints, 2, 2, 1, 1);
		
		p3.add(jbtReg);
		p3.add(jbtOK);
		p3.add(jbtCancel);

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);
		
		
		jtfNameInput.addActionListener(new Action());
		jtfPasswordInput.addActionListener(new Action());


		jbtReg.addActionListener(new Action());
		jbtOK.addActionListener(new Action());
		jbtCancel.addActionListener(new Action());
		
		
		this.pack();
		this.setTitle("Login");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
				
	}
	
	
	public boolean checkLogin(String ID, String passWord) {
		
		return client.login(ID, passWord);
		
		
	}
	
	
	private class Action implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == jtfNameInput)
				jtfPasswordInput.requestFocus();
			
			else if(e.getSource() == jbtReg) {
				
				Register registerFrame = new Register(client);
			}
			
			else if(e.getSource() == jtfPasswordInput || e.getSource() == jbtOK) {
				
				String nameText = jtfNameInput.getText();
				
				char[] passwordText = jtfPasswordInput.getPassword();
				
				String str = new String(passwordText);
				

				
				if (checkLogin(nameText, str)) {
					client.newMainFrame();
					//FriendsListUI friendList = new FriendsListUI(client);					
					dispose();
				}
				
				else {
					JOptionPane.showMessageDialog(null, "帐号或密码错误！", "警告", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			else if(e.getSource() == jbtCancel)
				dispose();
		}
	}
	
	
	
	public void addComponent(Component c, GridBagConstraints constraints, int x, int y, int w, int h) {
		
		constraints.gridx = x;      // 设置控件位于第几列
		constraints.gridy = y;      // 设置控件位于第几行
		constraints.gridwidth = w;  // 设置控件需要占几列
		constraints.gridheight = h; // 设置控件需要占几行
		
		p2.add(c, constraints);
	}
		
}
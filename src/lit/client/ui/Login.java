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
	
	private JLabel title = new JLabel("��¼����");
	private JLabel name = new JLabel("LIT�ʺ�: ");
	private JLabel password = new JLabel("����: ");
	private JTextField jtfNameInput = new JTextField(15);
	private JPasswordField jtfPasswordInput = new JPasswordField(15);
	private JButton jbtReg = new JButton("ע��");
	private JButton jbtOK = new JButton("��¼");
	private JButton jbtCancel = new JButton("ȡ��");

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
					JOptionPane.showMessageDialog(null, "�ʺŻ��������", "����", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			else if(e.getSource() == jbtCancel)
				dispose();
		}
	}
	
	
	
	public void addComponent(Component c, GridBagConstraints constraints, int x, int y, int w, int h) {
		
		constraints.gridx = x;      // ���ÿؼ�λ�ڵڼ���
		constraints.gridy = y;      // ���ÿؼ�λ�ڵڼ���
		constraints.gridwidth = w;  // ���ÿؼ���Ҫռ����
		constraints.gridheight = h; // ���ÿؼ���Ҫռ����
		
		p2.add(c, constraints);
	}
		
}
package lit.client.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import lit.client.LITClient;
import lit.client.ui.MessagePanel;
import lit.util.LITUtil;

public class Register extends JFrame {
	LITClient client=null;
	
	private MessagePanel welcome;
	
	private JLabel jlbSetPassword = new JLabel("设置密码：");
	private JTextField jtfSetPassword = new JTextField(15);

	private JLabel jlbResetPassword = new JLabel("重新输入密码：");
	private JTextField jtfResetPassword = new JTextField(15);
	
	private JLabel jlbNickname = new JLabel("昵称：");
	private JTextField jtfNickname = new JTextField(15);
	
	private JLabel jlbSex = new JLabel("性别：");
	private Object[] sex = {"男", "女"};
	private JComboBox jcbSex = new JComboBox(sex);
	
	JButton jbtSubmit = new JButton("提交");

	JPanel p = new JPanel(new GridBagLayout()); //给p设置布局管理器
	
	Timer timer = new Timer(200, new TimerListener());
	
	
	public Register(LITClient client) {
		this.client = client;
		/* 欢迎字幕 */
		welcome = new MessagePanel("欢迎注册使用LIT~");
		welcome.setFont(new Font("Times", Font.BOLD, 20));
		welcome.setBackground(Color.pink);

		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 100;
		constraints.weighty = 100;

		addComponent(jlbSetPassword, constraints, 1, 1, 1, 1);
		addComponent(jtfSetPassword, constraints, 2, 1, 1, 1);
		addComponent(jlbResetPassword, constraints, 1, 2, 1, 1);
		addComponent(jtfResetPassword, constraints, 2, 2, 1, 1);
		addComponent(jlbNickname, constraints, 1, 3, 1, 1);
		addComponent(jtfNickname, constraints, 2, 3, 1, 1);
		
		addComponent(jlbSex, constraints, 1, 4, 1, 1);
		addComponent(jcbSex, constraints, 2, 4, 1, 1);


		addComponent(jbtSubmit, constraints, 2, 9, 1, 1);		

		add(welcome, BorderLayout.NORTH);
		add(p, BorderLayout.CENTER);

		timer.start();
		
		jtfSetPassword.addActionListener(new Action());
		jtfResetPassword.addActionListener(new Action());
		jtfNickname.addActionListener(new Action());
		
		jbtSubmit.addActionListener(new Action());
		
		this.setSize(300, 260);
		this.setTitle("Register");
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
	}
	
	/* 将组件添加到网格组里 */
	public void addComponent(Component c, GridBagConstraints constraints, int x, int y, int w, int h) {
		
		constraints.gridx = x;      // 设置控件位于第几列
		constraints.gridy = y;      // 设置控件位于第几行
		constraints.gridwidth = w;  // 设置控件需要占几列
		constraints.gridheight = h; // 设置控件需要占几行
		
		p.add(c, constraints);
	}
	
	
	
	private class Action implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == jtfSetPassword) {
				jtfResetPassword.requestFocus();
			}
			
			else if(e.getSource() == jtfResetPassword)
				jtfNickname.requestFocus();
			
			else if(e.getSource() == jbtSubmit) {
				String password = jtfSetPassword.getText();
				String rePassword = jtfResetPassword.getText();
				String nickname = jtfNickname.getText();
				String sex = "" + jcbSex.getSelectedItem();
				
				if(!password.equals(rePassword)) {
					LITUtil.error("注册失败", "请重新填写信息", "两次密码不一致！");
					return;
				//	JOptionPane.showMessageDialog(null, "两次密码不一致！", "警告", JOptionPane.INFORMATION_MESSAGE);
				}
				if(password==null||password.equals("")
						||rePassword==null||rePassword.equals("")
						||nickname ==null||nickname.equals(""))
					LITUtil.error("注册失败", "请重新填写信息", "信息不能为空");
				
				else {
					// 一个狗方法。
					client.register(rePassword, nickname, sex);
				}
				
				
				
			}
		}
	}
	
	private class TimerListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			/* 让字幕动起来 */
			if(welcome.getXCoordinate() < -120) {
				welcome.setXCoordinate(getWidth());
			}
			welcome.moveLeft();
		}
	}
}

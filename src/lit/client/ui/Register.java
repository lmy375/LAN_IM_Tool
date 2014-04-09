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
	
	private JLabel jlbSetPassword = new JLabel("�������룺");
	private JTextField jtfSetPassword = new JTextField(15);

	private JLabel jlbResetPassword = new JLabel("�����������룺");
	private JTextField jtfResetPassword = new JTextField(15);
	
	private JLabel jlbNickname = new JLabel("�ǳƣ�");
	private JTextField jtfNickname = new JTextField(15);
	
	private JLabel jlbSex = new JLabel("�Ա�");
	private Object[] sex = {"��", "Ů"};
	private JComboBox jcbSex = new JComboBox(sex);
	
	JButton jbtSubmit = new JButton("�ύ");

	JPanel p = new JPanel(new GridBagLayout()); //��p���ò��ֹ�����
	
	Timer timer = new Timer(200, new TimerListener());
	
	
	public Register(LITClient client) {
		this.client = client;
		/* ��ӭ��Ļ */
		welcome = new MessagePanel("��ӭע��ʹ��LIT~");
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
	
	/* �������ӵ��������� */
	public void addComponent(Component c, GridBagConstraints constraints, int x, int y, int w, int h) {
		
		constraints.gridx = x;      // ���ÿؼ�λ�ڵڼ���
		constraints.gridy = y;      // ���ÿؼ�λ�ڵڼ���
		constraints.gridwidth = w;  // ���ÿؼ���Ҫռ����
		constraints.gridheight = h; // ���ÿؼ���Ҫռ����
		
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
					LITUtil.error("ע��ʧ��", "��������д��Ϣ", "�������벻һ�£�");
					return;
				//	JOptionPane.showMessageDialog(null, "�������벻һ�£�", "����", JOptionPane.INFORMATION_MESSAGE);
				}
				if(password==null||password.equals("")
						||rePassword==null||rePassword.equals("")
						||nickname ==null||nickname.equals(""))
					LITUtil.error("ע��ʧ��", "��������д��Ϣ", "��Ϣ����Ϊ��");
				
				else {
					// һ����������
					client.register(rePassword, nickname, sex);
				}
				
				
				
			}
		}
	}
	
	private class TimerListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			/* ����Ļ������ */
			if(welcome.getXCoordinate() < -120) {
				welcome.setXCoordinate(getWidth());
			}
			welcome.moveLeft();
		}
	}
}

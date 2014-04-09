package lit.client.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

import lit.client.LITClient;
import lit.server.Server;
import lit.util.LITUtil;


public class AddContact extends JFrame {
	Server server=null;
	
	
	private JLabel title = new JLabel("��Ӻ���");
	private JLabel cID = new JLabel("������ID:");
	private JTextField jtfCID = new JTextField();


	private JButton jbtAdd = new JButton("���");
	private JButton jbtCancle = new JButton("ȡ��");

	JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));	
	JPanel p2 = new JPanel(new GridLayout(1, 2));
	JPanel p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	LITClient client;
	public AddContact(LITClient client) {
		this.client = client;
		cID.setHorizontalAlignment(SwingConstants.CENTER);
		
		title.setFont(new Font("Times", Font.BOLD, 20));
		p1.add(title);
		p2.add(cID);
		p2.add(jtfCID);	
		p3.add(jbtAdd);
		p3.add(jbtCancle);

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);

		jbtAdd.addActionListener(new Action());
		jbtCancle.addActionListener(new Action());
			
		this.setSize(300, 140);
		this.setTitle("Server");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	private class Action implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == jbtAdd) {
				String cID = jtfCID.getText();				
				client.addFriend(cID);
				dispose();
//				//���ID����
//				if(true) { 
//					JOptionPane.showMessageDialog(null, "��ӳɹ�~", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
//				}
//				else {
//					JOptionPane.showMessageDialog(null, "���ʧ�ܣ�����ӵ��û������ڡ�", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
//				}
				
			}
						
			else if(e.getSource() == jbtCancle) {
				dispose();
			}
		}
	}
}


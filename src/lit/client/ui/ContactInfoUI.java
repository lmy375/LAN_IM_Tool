package lit.client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lit.object.ContactInfo;


public class ContactInfoUI extends JFrame {

	private JLabel top = new JLabel(new ImageIcon("image/005.jpg"));
	//private JLabel jlb = new JLabel("��֪������Щ��Ϣ");

	private JButton jbtClose = new JButton("�ر�");

	JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));	
	JPanel p2 = new JPanel(new GridLayout(4, 2));
	JPanel p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	public ContactInfoUI(ContactInfo info) {
		super(info.getName()+"������");

		top.setText(info.getName());
		p1.add(top);
//		p2.add(jlb);
		p2.add(new JLabel("ID��"));
		p2.add(new JLabel(info.getID()));
		p2.add(new JLabel("�ǳƣ�"));
		p2.add(new JLabel(info.getName()));
		p2.add(new JLabel("�Ա�"));
		p2.add(new JLabel(info.getSex().equals("Male")?"��":"Ů"));
		p2.add(new JLabel("���գ�"));
		p2.add(new JLabel("2013-7-4"));
		
		
		p3.add(jbtClose);

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);

		jbtClose.addActionListener(new Action());
			
		this.setSize(300, 210);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);			
	}
	

	private class Action implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
				dispose();			
		}
	}
	
}

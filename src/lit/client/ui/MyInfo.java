package lit.client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import lit.client.LITClient;
import lit.object.ContactInfo;


public class MyInfo extends JFrame {
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyInfo();

	}*/
	
	private JLabel title = new JLabel("我的资料");
	private JLabel jlbID = new JLabel("ID：");
	private JLabel jlbName = new JLabel("昵称：");
	private JLabel jlbSex = new JLabel("性别：");
	private JLabel jlbBirth = new JLabel("生日：");

	private JButton jbtSave = new JButton("保存修改");
	private JButton jbtClose = new JButton("关闭");
	
	private JTextField jtfID = new JTextField();
	private JTextField jtfName = new JTextField();
	private JTextField jtfSex = new JTextField();
	private JTextField jtfBirth = new JTextField();
	
	
	

	JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));	
	JPanel p2 = new JPanel(new GridLayout(4, 2));
	JPanel p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	LITClient client;
	public MyInfo(LITClient client) {
		this.client = client;
		ContactInfo info = client.getSelfInfo();
		title.setFont(new Font("Times", Font.BOLD, 20));
		p1.add(title);
		
		jtfID.setText(info.getID());
		jtfID.setEditable(false);
		jtfName.setText(info.getName());
		jtfSex.setText(info.getSex());
		jtfBirth.setText("haimeiyou");

		jlbID.setHorizontalAlignment(SwingConstants.CENTER);
		jlbName.setHorizontalAlignment(SwingConstants.CENTER);
		jlbSex.setHorizontalAlignment(SwingConstants.CENTER);
		jlbBirth.setHorizontalAlignment(SwingConstants.CENTER);
		
		p2.add(jlbID);
		p2.add(jtfID);
		p2.add(jlbName);
		p2.add(jtfName);
		p2.add(jlbSex);
		p2.add(jtfSex);
		p2.add(jlbBirth);
		p2.add(jtfBirth);
		
		
		p3.add(jbtSave);
		p3.add(jbtClose);

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);

		jbtSave.addActionListener(new Action());
		jbtClose.addActionListener(new Action());
			
		this.setSize(300, 210);
		this.setTitle("My Information");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);			
	}
	

	private class Action implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == jbtSave) {
				System.out.println("保存修改");
				String ID = jtfID.getText();
				String name = jtfName.getText();
				String sex = jtfSex.getText();
				String birth = jtfBirth.getText();
				ContactInfo info = new ContactInfo(ID, name, sex, birth);
				client.changeInfo(info);
			}
						
			else if(e.getSource() == jbtClose) {
				dispose();
			}
			
		}
	}
	
}

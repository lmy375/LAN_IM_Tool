package lit.client.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import lit.client.LITClient;
import lit.server.Server;
import lit.util.LITUtil;



public class ConnectServer extends JFrame {
	
	LITClient client=null;
	
	private JLabel title = new JLabel("连接服务器");
	private JLabel ip = new JLabel("IP: ");
	private JLabel port = new JLabel("Port: ");
	private JTextField jtfIPInput = new JTextField(15);
	private JTextField jtfPortInput = new JTextField(15);
	private JButton jbtOK = new JButton("连接");
	private JButton jbtCancel = new JButton("取消");

	JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));	
	JPanel p2 = new JPanel(new GridBagLayout());
	JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));

	
	public ConnectServer() {
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.weightx = 100;
		constraints.weighty = 100;
		
		p1.add(title);
		
		addComponent(ip, constraints, 1, 1, 1, 1);
		addComponent(port, constraints, 1, 2, 1, 1);
		addComponent(jtfIPInput, constraints, 2, 1, 1, 1);
		addComponent(jtfPortInput, constraints, 2, 2, 1, 1);
		
		p3.add(jbtOK);
		p3.add(jbtCancel);

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);
		
		
		jtfIPInput.addActionListener(new Action());
		jtfIPInput.setText("localhost");
		jtfPortInput.addActionListener(new Action());
		jtfPortInput.setText("8888");

		jbtOK.addActionListener(new Action());
		jbtCancel.addActionListener(new Action());
		
		
		this.pack();
		this.setTitle("ConnectServer");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
				
	}
	
	
	public boolean checkConnect(String ip, String portNumber) {
		if(portNumber==null||portNumber.equals("")){
			LITUtil.error("ERROR", "请正确填写IP和端口号","Port number is null.");
			//JOptionPane.showMessageDialog(null, "请正确填写端口号", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try{
			int port= Integer.parseInt(portNumber);	
			client = new LITClient(ip, port);			
			return client.connect();
		}catch(Exception ex){
			LITUtil.error("ERROR", "请正确填写IP和端口号", ex.getMessage());
			return false;
			//JOptionPane.showMessageDialog(null, "请正确填写端口号", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	
	private class Action implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == jtfIPInput)
				jtfPortInput.requestFocus();
			
			else if(e.getSource() == jtfPortInput || e.getSource() == jbtOK) {
				
				String ipText = jtfIPInput.getText();
				
				String portText = jtfPortInput.getText();
				
				
				if (checkConnect(ipText, portText)) {
					
					Login LoginFrame = new Login(client);
					
					dispose();
				}
				
				else {
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

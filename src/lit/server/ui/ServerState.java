package lit.server.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.*;

import lit.server.Server;
import lit.util.LITUtil;


public class ServerState extends JFrame {
		Server server=null;
		
		
		private JLabel title = new JLabel("服务器");
		private JLabel ip = new JLabel("IP:");
		private JLabel ipContent = new JLabel("192.168.1.1");
		String strIP;
		private JLabel port = new JLabel("PORT: ");
		private JTextField jtfport = new JTextField("8888");
		private JLabel state = new JLabel("状态: ");
		private JLabel stateContent = new JLabel("关闭");
		private JLabel online = new JLabel("在线人数: ");
		private JLabel userCount = new JLabel("0人");

		private JButton jbtStart = new JButton("开启");
		private JButton jbtClose = new JButton("关闭");
		private JButton jbtManage = new JButton("管理");
		
		private JTextArea jtaRecord = new JTextArea(8, 20);    
		
		JPanel p = new JPanel(new BorderLayout());
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));	
		JPanel p2 = new JPanel(new GridLayout(4, 2));
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));

		public ServerState() {
		
			ip.setHorizontalAlignment(SwingConstants.CENTER);
			port.setHorizontalAlignment(SwingConstants.CENTER);
			state.setHorizontalAlignment(SwingConstants.CENTER);
			online.setHorizontalAlignment(SwingConstants.CENTER);
			
			jtaRecord.setLineWrap(true);
			jtaRecord.setEditable(false);//将记录设为不可编辑
			jtaRecord.setFont(new Font("Times", Font.ITALIC, 11));
			JScrollPane jspRecord = new JScrollPane(jtaRecord);
			
			//获取本机IP
			try	{ 
				strIP = "" + InetAddress.getLocalHost();
			} catch (UnknownHostException e){ 
				e.printStackTrace();
			}
			
			ipContent.setText(strIP);
			
			title.setFont(new Font("Times", Font.BOLD, 20));
			p1.add(title);
			p2.add(ip);
			p2.add(ipContent);
			p2.add(port);
			p2.add(jtfport);
			p2.add(state);
			p2.add(stateContent);
			p2.add(online);
			p2.add(userCount);
			
			p3.add(jbtStart);
			p3.add(jbtClose);
			jbtClose.setEnabled(false);
			p3.add(jbtManage);
			
			p.add(p1, BorderLayout.NORTH);
			p.add(p2, BorderLayout.CENTER);
			p.add(p3, BorderLayout.SOUTH);

			add(p, BorderLayout.CENTER);
			add(jspRecord, BorderLayout.SOUTH);

			jbtStart.addActionListener(new Action());
			jbtClose.addActionListener(new Action());
			jbtManage.addActionListener(new Action());
				
			this.setSize(300, 360);
			this.setTitle("Server");
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);			
			
			
			
			this.addWindowListener(new WindowAdapter()
			{
			   public void windowClosing(WindowEvent e)
			   {
			         if (server!=null) server.close();
			    }
			});
			
		}
	
	public void changeUserCount(int count) {   //改变在线人数
		
		userCount.setText(count+ "");
	}
	
	public void log(String s){//显示日志
		this.jtaRecord.append(s+"\n");
	}
	
	private class Action implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == jbtStart) {
				String portNumber = jtfport.getText();
				if(portNumber==null||portNumber.equals("")){
					LITUtil.error("ERROR", "请正确填写端口号","Port number is null.");
					//JOptionPane.showMessageDialog(null, "请正确填写端口号", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try{
					int port= Integer.parseInt(portNumber);	
					server = new Server(port,ServerState.this);
					server.start();
					stateContent.setText("开启");
					jbtStart.setEnabled(false);
					jbtClose.setEnabled(true);
					return;
				}catch(Exception ex){
					LITUtil.error("ERROR", "请正确填写端口号", ex.getMessage());
					//JOptionPane.showMessageDialog(null, "请正确填写端口号", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
						
			else if(e.getSource() == jbtClose) {
				if(server!=null) server.close();
				jbtStart.setEnabled(true);
				jbtClose.setEnabled(false);
				stateContent.setText("关闭");
			}
			
			else if(e.getSource() == jbtManage) {
				try {
					if(server.getDatabase()!=null)
						new ShowDbFrame(server.getDatabase());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}

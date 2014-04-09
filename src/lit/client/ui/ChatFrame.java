package lit.client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import lit.client.LITClient;
import lit.object.ContactInfo;
import lit.object.messages.ChatMessage;
import lit.object.messages.Message;


public class ChatFrame extends JFrame {
	LITClient client=null;
	String toID=null;
	String toName= null;
	String myName=null;
	private JTextArea jtaInteract = new JTextArea(8, 20);    //互发的信息
	private JTextArea jtaSend = new JTextArea(4, 20);     //我要发的信息

	private JButton jbtSend = new JButton("发送");
	private JButton jbtClose = new JButton("关闭");

	JPanel p2 = new JPanel(new BorderLayout());
	JPanel p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	
	public ChatFrame(String toID,String toName, LITClient client) {
		super("与 "+toName+" 会话中");
		
		this.client = client;
		this.toID = toID;
		this.toName = toName;
		this.myName= client.getSelfInfo().getName();
		client.addChat(toID, this);
		jtaInteract.setLineWrap(true);
		jtaInteract.setEditable(false);   //将互动的信息设为不可编辑
		
		jtaSend.setLineWrap(true);
		
		JScrollPane scrollPaneInteract = new JScrollPane(jtaInteract);
		JScrollPane scrollPaneSend = new JScrollPane(jtaSend);
		
		p2.add(scrollPaneInteract, BorderLayout.CENTER);
		p2.add(scrollPaneSend, BorderLayout.SOUTH);

		p3.add(jbtSend);
		p3.add(jbtClose);

		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);

		jbtSend.addActionListener(new Action());
		jbtClose.addActionListener(new Action());
			
		this.setSize(400, 410);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);	
		
		
		this.refresh();
		this.addWindowListener(new WindowAdapter()
		{
		   public void windowClosing(WindowEvent e)
		   {
		         ChatFrame.this.client.removeChat(ChatFrame.this.toID);
		    }
		});
		
	}
	
	public void appendMessage(String name, String time, String content){
		this.jtaInteract.append(name+"  "+time+"\n  "+content+"\n");
	}

	private class Action implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == jbtSend) {
				String content = jtaSend.getText();
				String time = DateFormat.getDateTimeInstance().format(new Date());
				client.sendChatMessage(toID, time, content);
				appendMessage(myName, time, content);
				jtaSend.setText("");
				//System.out.println("发送");
			}
						
			else if(e.getSource() == jbtClose) {
				dispose();
			}
			
		}
	}
	
	public void refresh(){
		ChatMessage msg ;
		while((msg= (ChatMessage) client.getMessage(toID))!=null){
			this.appendMessage(toName, msg.getTime(), msg.getContent());
		}
	}
	
}


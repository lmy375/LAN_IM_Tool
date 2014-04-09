package lit.server.ui;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;

import lit.object.ContactInfo;
import lit.server.LITDB;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class DBFriList extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	private ContactInfo ci;
	private LITDB litdb;
	
	private DefaultTableModel tableModel;
	
	private ArrayList<String> allFriId = null;
	private ContactInfo friInfo;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					dbFriList frame = new dbFriList();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public DBFriList(ContactInfo ci, LITDB litdb) throws SQLException {
		
		this.ci = ci;
		this.litdb = litdb;
		
		setTitle("好友列表");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 263, 329);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 31, 227, 220);
		contentPane.add(scrollPane);
		
		table = new JTable();
		
		String[] columnNames = {"好友账号", "好友用户名"};
		allFriId = new ArrayList<String>();
		
		
		
		allFriId = litdb.queryFriList(ci.getID());
		
		if(allFriId.size()<=0)
		{
			setVisible(false);
			JOptionPane.showMessageDialog(null, "该用户没有好友");
			dispose();
		}
		else
		{
			setVisible(true);
			friInfo = litdb.queryUser(allFriId.get(0));
		
			Object[][] cellData = {{friInfo.getID(), friInfo.getName()}};
		
		
			tableModel = new DefaultTableModel(cellData, columnNames);
		
			table = new JTable(tableModel);

			// 填充数据
			for(int i=1;i<allFriId.size();i++)
			{
				friInfo = litdb.queryUser(allFriId.get(i));
				String[] arr = new String[2];
				arr[0] = friInfo.getID();
				arr[1] = friInfo.getName();
				    
				// 添加数据到表格
				tableModel.addRow(arr);
			}
		}
		
		
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		JLabel label = new JLabel("用户账号：" + ci.getID());
		label.setBounds(64, 10, 126, 15);
		contentPane.add(label);
		
		JButton button = new JButton("返回");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button.setBounds(72, 257, 93, 23);
		contentPane.add(button);
	}
}

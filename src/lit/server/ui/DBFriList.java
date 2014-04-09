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
		
		setTitle("�����б�");
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
		
		String[] columnNames = {"�����˺�", "�����û���"};
		allFriId = new ArrayList<String>();
		
		
		
		allFriId = litdb.queryFriList(ci.getID());
		
		if(allFriId.size()<=0)
		{
			setVisible(false);
			JOptionPane.showMessageDialog(null, "���û�û�к���");
			dispose();
		}
		else
		{
			setVisible(true);
			friInfo = litdb.queryUser(allFriId.get(0));
		
			Object[][] cellData = {{friInfo.getID(), friInfo.getName()}};
		
		
			tableModel = new DefaultTableModel(cellData, columnNames);
		
			table = new JTable(tableModel);

			// �������
			for(int i=1;i<allFriId.size();i++)
			{
				friInfo = litdb.queryUser(allFriId.get(i));
				String[] arr = new String[2];
				arr[0] = friInfo.getID();
				arr[1] = friInfo.getName();
				    
				// ������ݵ����
				tableModel.addRow(arr);
			}
		}
		
		
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		JLabel label = new JLabel("�û��˺ţ�" + ci.getID());
		label.setBounds(64, 10, 126, 15);
		contentPane.add(label);
		
		JButton button = new JButton("����");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button.setBounds(72, 257, 93, 23);
		contentPane.add(button);
	}
}

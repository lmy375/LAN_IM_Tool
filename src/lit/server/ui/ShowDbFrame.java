package lit.server.ui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.MenuItem;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import javax.swing.*;

import lit.object.ContactInfo;
import lit.server.LITDB;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowDbFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTable table;
	private JButton exitButton;
	private JButton refreshButton;

	private DefaultTableModel tableModel;
	private JButton addButton;
	private LITDB litdb;
	private ContactInfo userInfo = null;
	
	private JMenuItem editItem;
	private JMenuItem delItem ;
	private JMenuItem friItem;
	private ArrayList<String> allUserId;
	
	private int selectedRowIndex;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ShowDbFrame frame = new ShowDbFrame();
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
	public ShowDbFrame(LITDB litdb) throws SQLException {
		this.litdb = litdb;
		setTitle("ShowAllUser");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 661, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 625, 308);
		contentPane.add(scrollPane);
		
		String[] columnNames = {"�û��˺�", "�û���", "�Ա�","����"};
		allUserId = new ArrayList<String>();
		
		
		
		allUserId = litdb.queryAllUserId();
		
		userInfo = litdb.queryUser(allUserId.get(0));
		
		Object[][] cellData = {{userInfo.getID(), userInfo.getName(), userInfo.getSex(),userInfo.getBirthDay()}};
		
		
		tableModel = new DefaultTableModel(cellData, columnNames);
		
		table = new JTable(tableModel);

		// �������
		for(int i=1;i<allUserId.size();i++)
		{
			userInfo = litdb.queryUser(allUserId.get(i));
			String[] arr = new String[4];
			arr[0] = userInfo.getID();
			arr[1] = userInfo.getName();
			arr[2] = userInfo.getSex();
			arr[3] = userInfo.getBirthDay();
				    
			// ������ݵ����
			tableModel.addRow(arr);
		}
		
		//�����п�
		TableColumn idColumn = table.getColumnModel().getColumn(0);
		idColumn.setPreferredWidth(100);
		idColumn.setMaxWidth(100);
		idColumn.setMaxWidth(100);
		
		TableColumn nameColumn = table.getColumnModel().getColumn(1);
		nameColumn.setWidth(40);
		
		TableColumn sexColumn = table.getColumnModel().getColumn(2);
		sexColumn.setWidth(10);
		
//		firsetColumn.setMaxWidth(30);
//		firsetColumn.setMinWidth(30);
		

		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
//		//��ȡ��ǰ�к�
//		selectRows=table.getSelectedRows().length;// ȡ���û���ѡ�е�����
//		System.out.println(selectRows);
//		selectedRowIndex = 0;
//		if(selectRows==1)
//			selectedRowIndex = table.getSelectedRow(); // ȡ���û���ѡ���� 
//		System.out.println(selectedRowIndex);
//

		
		
		//�����Ҽ��˵�
		final JPopupMenu deposePopupMenu = new JPopupMenu();

		editItem = new JMenuItem("�༭�û���Ϣ");
		delItem = new JMenuItem("ɾ�����û�");
		friItem = new JMenuItem("��ʾ���û������б�");
		
		
		//Ϊ�Ҽ��˵�����ѡ��
		deposePopupMenu.add(editItem);
		deposePopupMenu.add(delItem);
		deposePopupMenu.add(friItem);
		

		//ΪTable�����Ҽ��˵�
		addPopup(table, deposePopupMenu);
		
		exitButton = new JButton("�˳�");
		exitButton.setBounds(514, 339, 93, 23);
		contentPane.add(exitButton);
		
		refreshButton = new JButton("ˢ������");
		refreshButton.setBounds(112, 339, 93, 23);
		contentPane.add(refreshButton);
		
		addButton = new JButton("�����û�");
		addButton.setBounds(255, 339, 93, 23);
		contentPane.add(addButton);
		
		editItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
//				JOptionPane.showMessageDialog(null,  "V��������ȫ��\n" +
//						"���ߣ����΄� 11061151\n�������պ����ѧ\n�����ѧԺ\n" +
//						"Email: vforpmj0420@gmail.com","����V��������ȫ",
//						JOptionPane.INFORMATION_MESSAGE,
//						null);
			}
		});
		
		
		
		delItem.addActionListener(this);
		exitButton.addActionListener(this);
		editItem.addActionListener(this);
		refreshButton.addActionListener(this);
		friItem.addActionListener(this);
		addButton.addActionListener(this);
		
//		delItem.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent event)
//			{
//				if(selectedRowIndex!=-1)
//				{
//					JOptionPane.showOptionDialog(null, "�Ƿ�ɾ���˺�Ϊ" + (String)tableModel.getValueAt(selectedRowIndex, 0) +
//							"���û���ȫ����Ϣ��", "WARNING", JOptionPane.YES_NO_CANCEL_OPTION,
//							JOptionPane.WARNING_MESSAGE, null, null, null);
//					try {
//						litdb.delUser((String)tableModel.getValueAt(selectedRowIndex, 0));
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		});
	}
	
	//�Ҽ��˵�����ʵ�ַ���
	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger())
				
				showMenu(e);
		}
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger())
				
				showMenu(e);
			}
		private void showMenu(MouseEvent e) {
			selectedRowIndex = table.getSelectedRow();
			if(selectedRowIndex!=-1)
				System.out.println((String)tableModel.getValueAt(selectedRowIndex, 0));
			popup.show(e.getComponent(), e.getX(), e.getY());
		}
		});
	}
//	
//	public void delListener() ActionListener
//	{
//		public void actionPerformed(ActionEvent event)
//		{
//			
//		}
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==delItem)
		{
			if(selectedRowIndex!=-1)
			{
				int select = JOptionPane.showOptionDialog(null, "�Ƿ�ɾ���˺�Ϊ" + (String)tableModel.getValueAt(selectedRowIndex, 0) +
						"���û���ȫ����Ϣ��", "WARNING", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE, null, null, null);
				if(select==JOptionPane.YES_OPTION)
				{
					try {
						litdb.delUser((String)tableModel.getValueAt(selectedRowIndex, 0));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					this.refresh();
				}
			}
			else
				JOptionPane.showMessageDialog(null, "��ѡ��һ���û�");
		}
		
		else if(e.getSource()==editItem)
		{
			ContactInfo ci = null;
			if(selectedRowIndex!=-1)
			{
				try {
					ci = litdb.queryUser((String)tableModel.getValueAt(selectedRowIndex, 0));
					DBEditFrame ef = new DBEditFrame(ci, litdb, this);
					System.out.println("ef.getNewContactInfo()");
//					litdb.updateUser(ef.getNewContactInfo());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("refresh");
				this.refresh();
			}
			else
				JOptionPane.showMessageDialog(null, "��ѡ��һ���û�");
		}
		
		else if(e.getSource()==exitButton)
		{
			dispose();
		}
		
		else if(e.getSource()==refreshButton)
		{
			this.refresh();
			
		}
		
		else if(e.getSource()==addButton)
		{
			DBAddFrame daf = new DBAddFrame(litdb, this);
		}
		
		else if(e.getSource()==friItem)
		{
			ContactInfo ci = null;
			if(selectedRowIndex!=-1)
			{
				try {
					ci = litdb.queryUser((String)tableModel.getValueAt(selectedRowIndex, 0));
					DBFriList dfl = new DBFriList(ci, litdb);
//					System.out.println("ef.getNewContactInfo()");
//					litdb.updateUser(ef.getNewContactInfo());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				System.out.println("refresh");
//				this.refresh();
			}
			else
				JOptionPane.showMessageDialog(null, "��ѡ��һ���û�");
		}
	}
	
	
	public void refresh()
	{
		try {
			tableModel.setRowCount(0);// ���ԭ����
			allUserId = litdb.queryAllUserId();
//			System.out.println("dddadfadsgasdgasdgasdgfasdgreadg" + allUserId.get(0));
			for(int i=0;i<allUserId.size();i++)
			{
				userInfo = litdb.queryUser(allUserId.get(i));
				String[] arr = new String[4];
				arr[0] = userInfo.getID();
				arr[1] = userInfo.getName();
				arr[2] = userInfo.getSex();
				arr[3] = userInfo.getBirthDay();
					    
				// ������ݵ����
				tableModel.addRow(arr);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

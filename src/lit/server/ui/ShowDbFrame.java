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
		
		String[] columnNames = {"用户账号", "用户名", "性别","生日"};
		allUserId = new ArrayList<String>();
		
		
		
		allUserId = litdb.queryAllUserId();
		
		userInfo = litdb.queryUser(allUserId.get(0));
		
		Object[][] cellData = {{userInfo.getID(), userInfo.getName(), userInfo.getSex(),userInfo.getBirthDay()}};
		
		
		tableModel = new DefaultTableModel(cellData, columnNames);
		
		table = new JTable(tableModel);

		// 填充数据
		for(int i=1;i<allUserId.size();i++)
		{
			userInfo = litdb.queryUser(allUserId.get(i));
			String[] arr = new String[4];
			arr[0] = userInfo.getID();
			arr[1] = userInfo.getName();
			arr[2] = userInfo.getSex();
			arr[3] = userInfo.getBirthDay();
				    
			// 添加数据到表格
			tableModel.addRow(arr);
		}
		
		//设置列宽
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
		
//		//获取当前行号
//		selectRows=table.getSelectedRows().length;// 取得用户所选行的行数
//		System.out.println(selectRows);
//		selectedRowIndex = 0;
//		if(selectRows==1)
//			selectedRowIndex = table.getSelectedRow(); // 取得用户所选单行 
//		System.out.println(selectedRowIndex);
//

		
		
		//定义右键菜单
		final JPopupMenu deposePopupMenu = new JPopupMenu();

		editItem = new JMenuItem("编辑用户信息");
		delItem = new JMenuItem("删除该用户");
		friItem = new JMenuItem("显示该用户好友列表");
		
		
		//为右键菜单增加选项
		deposePopupMenu.add(editItem);
		deposePopupMenu.add(delItem);
		deposePopupMenu.add(friItem);
		

		//为Table增加右键菜单
		addPopup(table, deposePopupMenu);
		
		exitButton = new JButton("退出");
		exitButton.setBounds(514, 339, 93, 23);
		contentPane.add(exitButton);
		
		refreshButton = new JButton("刷新数据");
		refreshButton.setBounds(112, 339, 93, 23);
		contentPane.add(refreshButton);
		
		addButton = new JButton("增加用户");
		addButton.setBounds(255, 339, 93, 23);
		contentPane.add(addButton);
		
		editItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
//				JOptionPane.showMessageDialog(null,  "V的星座大全：\n" +
//						"作者：庞梦劼 11061151\n北京航空航天大学\n计算机学院\n" +
//						"Email: vforpmj0420@gmail.com","关于V的星座大全",
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
//					JOptionPane.showOptionDialog(null, "是否删除账号为" + (String)tableModel.getValueAt(selectedRowIndex, 0) +
//							"的用户的全部信息？", "WARNING", JOptionPane.YES_NO_CANCEL_OPTION,
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
	
	//右键菜单具体实现方法
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
				int select = JOptionPane.showOptionDialog(null, "是否删除账号为" + (String)tableModel.getValueAt(selectedRowIndex, 0) +
						"的用户的全部信息？", "WARNING", JOptionPane.YES_NO_CANCEL_OPTION,
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
				JOptionPane.showMessageDialog(null, "请选择一个用户");
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
				JOptionPane.showMessageDialog(null, "请选择一个用户");
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
				JOptionPane.showMessageDialog(null, "请选择一个用户");
		}
	}
	
	
	public void refresh()
	{
		try {
			tableModel.setRowCount(0);// 清除原有行
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
					    
				// 添加数据到表格
				tableModel.addRow(arr);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

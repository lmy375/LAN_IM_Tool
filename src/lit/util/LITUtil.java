package lit.util;

import javax.swing.JOptionPane;

public class LITUtil {
	public static void error(String title, String content, String exception){
		JOptionPane.showMessageDialog(null, content+"\n¥ÌŒÛ–≈œ¢£∫"+exception, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void infomation(String title, String content){
		JOptionPane.showMessageDialog(null, content, title, JOptionPane.INFORMATION_MESSAGE);
	}
}

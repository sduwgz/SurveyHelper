import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JFrame;

public class ViewPage extends JFrame{
	public ArrayList<Page> pageList;
	public String title;
	JButton confirm = new JButton("确认");
	JButton back = new JButton("返回修改");
	public ViewPage(ArrayList<Page> pageList, String title){
		this.pageList = pageList;
		this.title = title;
		setupUI();
		setupListener();
	}
	private void setupUI(){
		
	}
	private void setupListener(){
		
	}
	
}

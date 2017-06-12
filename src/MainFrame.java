import javax.swing.*;  
  
import java.awt.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame{
	JButton newSheet, loadSheet;
	JPanel jp;
	public MainFrame(){
		setupUI();
		setupListener();
	}
	public void setupUI(){
		newSheet = new JButton("新建问卷");
		loadSheet = new JButton("载入问卷");
		jp = new JPanel();
		jp.add(newSheet);
	  	jp.add(loadSheet);
	  	this.add(jp);
	  	this.setLayout(new GridLayout(2,1));
	  	this.setTitle("问卷调查系统");
	  	this.setSize(300,200);
	  	this.setLocation(200, 150);
	  	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	this.setVisible(true);
	  	this.setResizable(true);
	}
	public void setupListener(){
		newSheet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateSheet cs = new CreateSheet();
			}
		});
		loadSheet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					AnswerSheet as = new AnswerSheet();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
	}
	public static void setFont(){
		Font font = new Font("宋体",Font.PLAIN,22);
		UIManager.put("Button.font",font);
		UIManager.put("ToggleButton.font",font);
		UIManager.put("RadioButton.font",font);
		UIManager.put("CheckBox.font",font);
		UIManager.put("ColorChooser.font",font);
		UIManager.put("ToggleButton.font",font);
		UIManager.put("ComboBox.font",font);
		UIManager.put("ComboBoxItem.font",font);
		UIManager.put("InternalFrame.titleFont",font);
		UIManager.put("Label.font",font);
		UIManager.put("List.font",font);
		UIManager.put("MenuBar.font",font);
		UIManager.put("Menu.font",font);
		UIManager.put("MenuItem.font",font);
		UIManager.put("RadioButtonMenuItem.font",font);
		UIManager.put("CheckBoxMenuItem.font",font);
		UIManager.put("PopupMenu.font",font);
		UIManager.put("OptionPane.font",font);
		UIManager.put("Panel.font",font);
		UIManager.put("ProgressBar.font",font);
		UIManager.put("ScrollPane.font",font);
		UIManager.put("Viewport",font);
		UIManager.put("TabbedPane.font",font);
		UIManager.put("TableHeader.font",font);
		UIManager.put("TextField.font",font);
		UIManager.put("PasswordFiled.font",font);
		UIManager.put("TextArea.font",font);
		UIManager.put("TextPane.font",font);
		UIManager.put("EditorPane.font",font);
		UIManager.put("TitledBorder.font",font);
		UIManager.put("ToolBar.font",font);
		UIManager.put("ToolTip.font",font);
		UIManager.put("Tree.font",font);
	}
	public static void main(String[] args){
		setFont();
		MainFrame mf = new MainFrame();
	}
}
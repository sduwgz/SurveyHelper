import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Group extends JLabel{
	public String name;
	public int startPage;
	public int endPage;
	public Group(String name, int s, int e){
		this.name = name;
		this.startPage = s;
		this.endPage = e;
		setupUI();
	}
	public Group(String group){
		//group: Name-start-end
		this.name = group.split("-")[0];
		this.startPage = Integer.parseInt(group.split("-")[1]);
		this.endPage = Integer.parseInt(group.split("-")[2]);
		setupUI();
	}
	public String getName(){
		return this.name;
	}
	public void setupUI(){
		this.setText(name);
		this.setVisible(true);
	}
	public void active(){
		this.setBackground(Color.YELLOW);
	}
	public void done(){
		this.setBackground(Color.GREEN);
	}
	public void unreach(){
		this.setBackground(Color.lightGray);
	}
}

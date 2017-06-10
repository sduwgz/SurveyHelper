import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JFrame;

public class ViewPage extends JFrame{
	public Group curGroup;
	public ArrayList<Page> pageList;
	JPanel controlPanel = new JPanel();
	JLabel info = new JLabel();
	JButton confirm = new JButton("确认");
	JButton back = new JButton("返回修改");
	public ViewPage(ArrayList<Page> pageList, Group curGroup){
		this.pageList = pageList;
		this.curGroup = curGroup;
		setupUI();
		setupListener();
	}
	private void setupUI(){
		this.setLayout(new GridBagLayout());
		this.setSize(200, 500);
		info.setText(curGroup.getName());
		this.add(info, new GBC(0,0,1,1).setFill(GBC.BOTH).setWeight(0.1, 0));
		int pos = 1;
		for(int i = 0; i < pageList.size(); ++ i){
			if(i + 1 < curGroup.startPage || i + 1 > curGroup.endPage) continue;
			ArrayList<Question> ql = pageList.get(i).questionList;
			for(int j = 0; j < ql.size(); ++ j){
				this.add(ql.get(j), new GBC(0,pos++,1,1).setFill(GBC.BOTH).setIpad(200, 20).setWeight(0.1, 0));
			}
		}
		controlPanel.setLayout(new GridLayout(1, 2));
		controlPanel.add(back);
		controlPanel.add(confirm);
		this.add(controlPanel, new GBC(0,pos,1,1).setFill(GBC.BOTH).setIpad(200, 20).setWeight(0.1, 0));
		this.setVisible(true);
	}
	private void setupListener(){
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
	}
	public void cancel(){
		this.dispose();
	}
	
}

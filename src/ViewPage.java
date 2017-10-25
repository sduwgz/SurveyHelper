import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JFrame;

public class ViewPage extends JFrame{
	public Group curGroup;
	public ArrayList<Page> pageList;
	JPanel controlPanel = new JPanel();
	JPanel contentPanel = new JPanel();
	JLabel info = new JLabel();
	JButton confirm = new JButton("确认");
	JButton back = new JButton("返回修改");
	JScrollPane scrollPanel = new JScrollPane(contentPanel);
	public ViewPage(ArrayList<Page> pageList, Group curGroup){
		this.pageList = pageList;
		this.curGroup = curGroup;
		setupUI();
		setupListener();
	}
	private void setupUI(){
		this.setLayout(new GridBagLayout());
		this.setSize(800, 600);
		contentPanel.setLayout(new GridBagLayout());
		scrollPanel.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		info.setText(curGroup.getName());
		Font font = new Font("宋体",Font.PLAIN,24);
		info.setFont(font);
		info.setBackground(Color.GRAY);
		this.add(info, new GBC(0,0,1,1).setFill(GBC.BOTH).setWeight(0.1, 0.2));
		int pos = 1;
		int i = 0;
		//for(int i = 0; i < pageList.size(); ++ i){
		while(i < pageList.size()){
			if(i + 1 < curGroup.startPage || i + 1 > curGroup.endPage) {
				i = pageList.get(i).nextPage - 1;
				continue;
			}
			font = new Font("宋体",Font.PLAIN,24);
			JLabel pageTitle = new JLabel("第" + (i + 1) + "页：" + pageList.get(i).title.getText());
			pageTitle.setFont(font);
			contentPanel.add(pageTitle, new GBC(0,pos++,1,1).setFill(GBC.BOTH).setAnchor(GBC.WEST).setInsets(10, 40, 0, 0).setWeight(0.1, 0));
			ArrayList<Question> ql = pageList.get(i).questionList;
			for(int j = 0; j < ql.size(); ++ j){
				font = new Font("宋体",Font.PLAIN,20);
				JLabel que = new JLabel((ql.get(j).getID() + 1)  + ". " + ql.get(j).quesDescribe);
				que.setFont(font);
				JLabel ans = new JLabel("  答案：" + ql.get(j).getAnswer());
				ans.setFont(font);
				ans.setForeground(Color.RED);
				System.out.println(ans.getText());
				contentPanel.add(que, new GBC(0,pos++,1,1).setFill(GBC.BOTH).setAnchor(GBC.WEST).setInsets(10, 50, 0, 0).setWeight(0.1, 0));
				contentPanel.add(ans, new GBC(0,pos++,1,1).setFill(GBC.BOTH).setAnchor(GBC.WEST).setInsets(10, 50, 0, 0).setWeight(0.1, 0));
			}
			i = pageList.get(i).nextPage - 1;
			System.out.println(i);
		}
		controlPanel.setLayout(new GridLayout(1, 2));
		controlPanel.add(back);
		controlPanel.add(confirm);
		
		this.add(scrollPanel, new GBC(0,1,9,1).setFill(GBC.BOTH).setIpad(200, 20).setWeight(0.1, 1));
		this.add(controlPanel, new GBC(0,10,1,1).setFill(GBC.BOTH).setIpad(200, 20).setWeight(0.1, 0));
		
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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateGroup extends JFrame{
	public JLabel title = new JLabel("输入模块信息（名称-起始页-结束页,名称-起始页-结束页）");
	public JTextField jtf = new JTextField();
	JButton cancel = new JButton("取消");
	JButton confirm = new JButton("确认");
	JPanel controlPanel = new JPanel();
	CreateSheet belongedSheet;
	public CreateGroup(CreateSheet belongedSheet){
		this.belongedSheet = belongedSheet;
		setupUI();
		setupListener();
	}
	public void setupUI(){
		this.setLayout(new BorderLayout());
		controlPanel.add(cancel);
		controlPanel.add(confirm);
		this.add(title, BorderLayout.NORTH);
		this.add(jtf, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	public void setupListener(){
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String groupInfo = jtf.getText();
				if(groupInfo.split(",").length > 1){
					for(String s : groupInfo.split(",")){
						Group g = new Group(s);
						belongedSheet.groupList.add(g);
					}
				}
				belongedSheet.groupInfo = groupInfo;
				cancel();
			}
		});
	}
	public void cancel(){
		this.dispose();
	}
}

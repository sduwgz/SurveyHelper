import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CreateSheet extends JFrame{
	JLabel breaks = new JLabel("#################################");
	int pageNumber = 0;
	public int questionNumber = 0;
	JButton createPage = new JButton("新建问题页");
	JButton undo = new JButton("撤销当前页");
	JButton finish = new JButton("完成");
	JPanel controlPanel = new JPanel();
	JPanel contentPanel = new JPanel();
	JScrollPane scrollPanel = new JScrollPane(contentPanel);
	ArrayList<Page> pageList = new ArrayList<Page>();
	ArrayList<Group> groupList = new ArrayList<Group>();
	String groupInfo;
	JButton divideGroup = new JButton("模块");
	public CreateSheet(){
		setupUI();
		setupListener();
	}
	private void setupUI(){
		this.setSize(800, 400);
		this.setLocation(400, 200);
		//this.setLayout(new GridLayout(1, 2));
		controlPanel.setLayout(new GridLayout(10, 1));
		controlPanel.add(createPage);
		controlPanel.add(undo);
		controlPanel.add(divideGroup);
		controlPanel.add(finish);
		contentPanel.setLayout(new GridBagLayout());
		//contentPanel.add(breaks);
		scrollPanel.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		this.add(controlPanel, BorderLayout.WEST);
		this.add(scrollPanel, BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	private void setupListener(){
		createPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newPage();
			}
		});
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int size = pageList.size();
				contentPanel.remove(size - 1);
				pageList.remove(size - 1);
				pageNumber --;
			}
		});
		divideGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createGroup();
			}
		});
		finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				XMLOperator xmlo = new XMLOperator(pageList, groupInfo);
				try {
					JFileChooser jfc = new JFileChooser();
				    jfc.setCurrentDirectory(new File("d:/"));//设置默认打开路径
				    jfc.setDialogType(JFileChooser.SAVE_DIALOG);//设置保存对话框
				    int index = jfc.showDialog(null, "保存文件");
				    if (index == JFileChooser.APPROVE_OPTION) {
				    	File f = jfc.getSelectedFile();
				    	String fileName = jfc.getName(f) + ".xml";
				    	String writePath = jfc.getCurrentDirectory().getAbsolutePath() + fileName;
				    	xmlo.writer(writePath);
				    }
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					AnswerSheet as = new AnswerSheet(pageList, groupList);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				cancel();
			}
		});
	}
	public void createGroup(){
		CreateGroup cg = new CreateGroup(this);
	}
	private void newPage(){
		CreatePage cp = new CreatePage(this);
	}
	public void addPage(Page newPage){
		pageList.add(newPage);
		this.contentPanel.add(newPage, new GBC(0,pageNumber ++,1,1).setFill(GBC.BOTH).setWeight(100, 0));
		this.contentPanel.revalidate();
		this.repaint();
	}
	public void cancel(){
		this.dispose();
	}

	public static void main(String args[]){
		CreateSheet cs = new CreateSheet();
	}
}

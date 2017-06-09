import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreatePage extends JFrame{
	public int questionNumber = 0;
	private static int createCount = 0;
	JButton addFillin = new JButton("填空题");
	JButton addChoise = new JButton("选择题");
	JButton undo = new JButton("撤销上一步");
	JButton finish = new JButton("完成");
	JLabel pageTitle = new JLabel("页面标题");
	JLabel pageDescribe = new JLabel("页面描述");
	JTextField titleField = new JTextField();
	JTextField describeField = new JTextField();
	JPanel controlPanel = new JPanel();
	JPanel contentPanel = new JPanel();
	ArrayList<Question> questionList = new ArrayList<Question>();
	Page newPage;
	CreateSheet belogedCS;
		
	public CreatePage(CreateSheet cs){
		setupUI();
		setupListener();
		this.belogedCS = cs;
	}
	private void setupUI(){
		this.setSize(600, 500);
		this.setLocation(400, 200);
		controlPanel.setLayout(new GridLayout(10, 1));
		controlPanel.add(addFillin);
		controlPanel.add(addChoise);
		controlPanel.add(undo);
		controlPanel.add(finish);
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.add(pageTitle, new GBC(0,0,1,1).setFill(GBC.BOTH).setIpad(20, 20).setWeight(100, 0));
		contentPanel.add(titleField, new GBC(1,0,1,1).setFill(GBC.BOTH).setIpad(300, 20).setWeight(100, 0));
		contentPanel.add(pageDescribe, new GBC(0,1,1,1).setFill(GBC.BOTH).setIpad(20, 20).setWeight(100, 0));
		contentPanel.add(describeField, new GBC(1,1,1,1).setFill(GBC.BOTH).setIpad(300, 20).setWeight(100, 0));
		this.add(controlPanel, BorderLayout.WEST);
		this.add(contentPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	public void setupListener(){
		addFillin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createFillin();
			}
		});
		addChoise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createChoise();
			}
		});
		finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newPage = new Page(questionList, ++ createCount, titleField.getText(), describeField.getText());
				addPagetoSheet();
				cancel();
			}
		});
	}
	public void createFillin(){
		CreateQuestion cf = new CreateFillinQues(this);
	}
	public void createChoise(){
		CreateQuestion cf = new CreateChoiseQues(this);
	}
	public void addPagetoSheet(){
		System.out.println(newPage.questionList.size());
		this.belogedCS.addPage(newPage);
	}
	public void addTitile(){
		
	}
	public void cancel(){
		this.dispose();
	}
	public void addQuestion(Question newQues){
		//System.out.println(questionNumber);
		//System.out.println(newQues.quesDescribe);
		questionList.add(newQues);
		this.contentPanel.add(newQues, new GBC(0,questionNumber + 1,2,1).setFill(GBC.BOTH).setIpad(300, 20).setWeight(100, 0));
		this.contentPanel.revalidate();
		this.repaint();
	}
}

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class CreateFillinQues extends CreateQuestion{
	Question question;
	JTextField fill = new JTextField();
	JLabel answerSet;
	JTextField setField;
	JLabel minNumber;
	JTextField minField;
	JLabel maxNumber;
	JTextField maxField;
	public CreateFillinQues(CreatePage belogedPage) {
		super(belogedPage);
		
		
		setupUI();
		setupListener();
	}
	public void setupUI(){
		controlPanel.setLayout(new GridLayout(1, 3));
		controlPanel.add(cancel);
		controlPanel.add(clear);
		controlPanel.add(confirm);
		answerSet = new JLabel("答案集合(英文逗号隔开)");
		setField = new JTextField();
		minNumber = new JLabel("最小值(数字)");
		minField = new JTextField();
		maxNumber = new JLabel("最大值(数字)");
		maxField = new JTextField();
		
		this.setSize(300, 500);
		this.setLocation(600, 200);
		this.setLayout(new GridBagLayout());
		this.add(jl, new GBC(0,0,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.add(fill, new GBC(0,1,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.add(answerSet, new GBC(0,2,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.add(setField, new GBC(0,3,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.add(minNumber, new GBC(0,4,1,1).setFill(GBC.BOTH).setIpad(30, 50).setWeight(100, 0));
		this.add(minField, new GBC(1,4,1,1).setFill(GBC.BOTH).setIpad(170, 50).setWeight(100, 0));
		this.add(maxNumber, new GBC(0,5,1,1).setFill(GBC.BOTH).setIpad(30, 50).setWeight(100, 0));
		this.add(maxField, new GBC(1,5,1,1).setFill(GBC.BOTH).setIpad(170, 50).setWeight(100, 0));
		this.add(controlPanel, new GBC(0,6,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.setVisible(true);
	}
	
	public void setupListener(){
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fill.setText("");
				setField.setText("");
				minField.setText("");
				maxField.setText("");
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				question = new FillinQuestion(0, fill.getText(), setField.getText(), minField.getText(), maxField.getText());
				question.ID = belogedPage.questionNumber;
				addQuestiontoPage();
				cancel();
			}
		});
	}
	public void addQuestiontoPage(){
		this.belogedPage.questionNumber ++;
		this.belogedPage.addQuestion(this.question);
		this.belogedPage.repaint();
	}
}

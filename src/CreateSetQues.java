import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class CreateSetQues extends CreateQuestion{
	Question question;
	JTextField fill = new JTextField();
	JLabel answerSet;
	JTextField setField;
	JLabel minNumber;
	JTextField minField;
	JLabel maxNumber;
	JTextField maxField;
	public CreateSetQues(CreatePage belogedPage) {
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
		
		this.setSize(300, 600);
		this.setLocation(600, 200);
		this.setLayout(new GridBagLayout());
		this.add(jl, new GBC(0,0,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.add(fill, new GBC(0,1,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.add(answerSet, new GBC(0,2,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.add(setField, new GBC(0,3,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.add(controlPanel, new GBC(0,6,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.setVisible(true);
	}
	
	public void setupListener(){
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fill.setText("");
				setField.setText("");
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
				question = new SetQuestion(belogedPage.questionNumber, fill.getText(), setField.getText());
				
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
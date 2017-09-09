import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class CreateDateQues extends CreateQuestion{
	Question question;
	JTextField fill = new JTextField();
	public CreateDateQues(CreatePage belogedPage) {
		super(belogedPage);
		setupUI();
		setupListener();
	}
	public void setupUI(){
		controlPanel.setLayout(new GridLayout(1, 3));
		controlPanel.add(cancel);
		controlPanel.add(clear);
		controlPanel.add(confirm);
		
		this.setSize(300, 600);
		this.setLocation(600, 200);
		this.setLayout(new GridBagLayout());
		this.add(jl, new GBC(0,0,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.add(fill, new GBC(0,1,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.add(controlPanel, new GBC(0,6,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
		this.setVisible(true);
	}
	
	public void setupListener(){
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fill.setText("");
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
				question = new DateQuestion(belogedPage.questionNumber, fill.getText());
				
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
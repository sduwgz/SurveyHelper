import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class CreateChoiseQues extends CreateQuestion{
	int choisesCount = 6;
	Question question;
	JTextField fill = new JTextField();
	JLabel[] choisesNumber = new JLabel[choisesCount];
	JTextField[] choisesContent = new JTextField[choisesCount];
	public CreateChoiseQues(CreatePage belogedPage) {
		super(belogedPage);
		setupUI();
		setupListener();
	}

	@Override
	public void setupUI() {	
		controlPanel.setLayout(new GridLayout(1, 3));
		controlPanel.add(cancel);
		controlPanel.add(clear);
		controlPanel.add(confirm);
		
		this.setSize(200, 500);
		this.setLocation(600, 200);
		this.setLayout(new GridBagLayout());
		this.add(jl, new GBC(0,0,2,1).setFill(GBC.BOTH).setIpad(200, 30).setWeight(100, 0));
		this.add(fill, new GBC(0,1,2,1).setFill(GBC.BOTH).setIpad(200, 30).setWeight(100, 0));
		for(int i = 0; i < choisesCount; ++ i){
			choisesNumber[i] = new JLabel("СЎПо " + i);
			choisesContent[i] = new JTextField();
			this.add(choisesNumber[i], new GBC(0,i + 2,1,1).setFill(GBC.BOTH).setIpad(30, 30).setWeight(100, 0));
			this.add(choisesContent[i], new GBC(1,i + 2,1,1).setFill(GBC.BOTH).setIpad(170, 30).setWeight(100, 0));
		}
		this.add(controlPanel, new GBC(0,choisesCount + 2,2,1).setFill(GBC.BOTH).setIpad(200, 30).setWeight(100, 0));
		this.setVisible(true);
	}

	@Override
	public void setupListener() {
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fill.setText("");
				for(int i = 0; i < choisesCount; ++ i){
					choisesContent[i].setText("");
				}
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
				ArrayList<String> choises = new ArrayList<String>();
				for(int i = 0; i < choisesCount; ++ i){
					if(choisesContent[i].getText().length() != 0)
						choises.add(choisesContent[i].getText());
				}
				String choiseString = String.join(",", choises);
				question = new ChoiseQuestion(0, fill.getText(), choiseString);
				question.ID = belogedPage.questionNumber;
				addQuestiontoPage();
				cancel();
			}
		});
	}

	@Override
	public void addQuestiontoPage() {
		this.belogedPage.questionNumber ++;
		this.belogedPage.addQuestion(this.question);
		this.belogedPage.repaint();
	}
	

}

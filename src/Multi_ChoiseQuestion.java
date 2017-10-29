import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Multi_ChoiseQuestion extends Question{
	
	int choisesNumber = 0;
	JTextArea jta = new JTextArea();
	public Multi_ChoiseQuestion(int ID, String quesDescribe, String ans) {
		super(ID, quesDescribe, ans);
		this.quesType = 3;
		setupUI();
	}
	public Multi_ChoiseQuestion(int ID, String quesDescribe, String ans, String choisesString){
		super(ID, quesDescribe, ans);
		this.quesType = 3;
		if(choisesString.split(",").length > 1)
			for(String s : choisesString.split(",")){
				choises.add(s);
			}
		this.choisesNumber = choises.size();
		jcbs = new JCheckBox[choisesNumber];
		remarks = new JTextField[choisesNumber];
		setupUI();
	}
	public void setupUI(){
		Font font = new Font("ËÎÌו",Font.PLAIN,20);
		this.setLayout(new GridBagLayout());
		if(quesDescribe.split("_").length == 2)
			jl = new JLabel(" " + (ID+1) +". " + quesDescribe.split("_")[1], JLabel.LEFT);
		else
			jl = new JLabel(" " + (ID+1) +". " + quesDescribe, JLabel.LEFT);
		jl.setFont(font);
		jta.setText(jl.getText());
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		jta.setFont(font);
		this.add(jta, new GBC(0,0,2,1).setFill(GBC.BOTH).setIpad(10, 20).setWeight(0.1, 0));
		for(int i = 0; i < choisesNumber; ++ i){
			//System.out.println(answers[i].getText());
			jcbs[i] = new JCheckBox("" + ((char)('A' + i)) + ": " + choises.get(i));
			jcbs[i].setFont(font);
			remarks[i] = new JTextField();
			if(choises.get(i).endsWith("#")){
				this.add(jcbs[i], new GBC(0,i+1,1,1).setIpad(30, 0).setAnchor(GBC.WEST).setWeight(0, 0));
				this.add(remarks[i], new GBC(1,i+1,1,1).setIpad(100, 0).setWeight(0.1, 0));
			} else {
				this.add(jcbs[i], new GBC(0,i+1,2,1).setIpad(30, 0).setAnchor(GBC.WEST).setWeight(0, 0));
			}
		}
		if(answer != "") {
			String[] a = answer.split("\n\r");
			for(String s : a) {
				String ch = s.split(":")[0];
				int anw = ch.charAt(0) - 'A';
				jcbs[anw].setSelected(true);
				if(s.split("_").length == 2) {
				String statement = answer.split("_")[1];
				remarks[anw].setText(statement);
				}
			}
		}
		this.setVisible(true);
	}
	public boolean checkAnswer(){
		for(int i = 0; i < choisesNumber; ++ i){
			if(jcbs[i].isSelected() && choises.get(i).endsWith("#") && remarks[i].getText().length() == 0) return false;
		}
		return true;
	}
	public boolean submit(){
		if(!checkAnswer()) return false;
		String allAnswer = "";
		for(int i = 0; i < choisesNumber; ++ i){
			if(jcbs[i].isSelected()){
				if(choises.get(i).endsWith("#"))
					allAnswer += jcbs[i].getText() + "_" + remarks[i].getText() + "\n\r";
				else
					allAnswer += jcbs[i].getText() + "\n\r";
			}
		}
		this.setAnswer(allAnswer);
		return true;
	}
}

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Multi_ChoiseQuestion extends Question{
	//TODO
	//Class Question is a JPanel. Add JLabel, JButton, JTextField into this Class.
	//Finish setupUI().
	//Finish submit(). 
	int choisesNumber = 0;
	
	public Multi_ChoiseQuestion(int ID, String quesDescribe) {
		super(ID, quesDescribe);
		this.quesType = 3;
		setupUI();
	}
	public Multi_ChoiseQuestion(int ID, String quesDescribe, String choisesString){
		super(ID, quesDescribe);
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
		this.add(jl, new GBC(0,0,2,1).setFill(GBC.BOTH).setIpad(10, 20).setWeight(0.1, 0));
		for(int i = 0; i < choisesNumber; ++ i){
			//System.out.println(answers[i].getText());
			jcbs[i] = new JCheckBox("" + ((char)('A' + i)) + ": " + choises.get(i));
			jcbs[i].setFont(font);
			remarks[i] = new JTextField();
			if(choises.get(i).endsWith("#")){
				this.add(jcbs[i], new GBC(0,i+1,1,1).setIpad(30, 10).setAnchor(GBC.WEST).setWeight(0, 0));
				this.add(remarks[i], new GBC(1,i+1,1,1).setIpad(100, 10).setWeight(0.1, 0));
			} else {
				this.add(jcbs[i], new GBC(0,i+1,2,1).setIpad(30, 10).setAnchor(GBC.WEST).setWeight(0, 0));
			}
		}
		this.setVisible(true);
	}
	public boolean checkAnswer(){
		for(int i = 0; i < choisesNumber; ++ i){
			if(choises.get(i).endsWith("#") && remarks[i].getText().length() == 0) return false;
		}
		return true;
	}
	public boolean submit(){
		if(!checkAnswer()) return false;
		String allAnswer = "";
		for(int i = 0; i < choisesNumber; ++ i){
			if(jcbs[i].isSelected()){
				if(choises.get(i).endsWith("#"))
					allAnswer += (char)('A' + i) + remarks[i].getText();
				else
					allAnswer += (char)('A' + i) + jcbs[i].getText();
			}
		}
		this.setAnswer(allAnswer);
		return true;
	}
}

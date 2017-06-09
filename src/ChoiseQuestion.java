import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ChoiseQuestion extends Question{
	//TODO
	//Class Question is a JPanel. Add JLabel, JButton, JTextField into this Class.
	//Finish setupUI().
	//Finish submit(). 
	int choisesNumber = 0;
	JLabel jl;
	ButtonGroup bg = new ButtonGroup();
	JRadioButton[] jrbs;
	JTextField[] remarks;
	
	public ChoiseQuestion(String quesDescribe) {
		super(quesDescribe);
		this.quesType = 2;
		setupUI();
	}
	public ChoiseQuestion(String quesDescribe, String choisesString){
		super(quesDescribe);
		this.quesType = 2;
		if(choisesString.split(",").length > 1)
			for(String s : choisesString.split(",")){
				choises.add(s);
			}
		this.choisesNumber = choises.size();
		jrbs = new JRadioButton[choisesNumber];
		remarks = new JTextField[choisesNumber];
		setupUI();
	}
	public void setupUI(){
		this.setLayout(new GridBagLayout());
		jl = new JLabel(quesDescribe);
		this.add(jl, new GBC(0,0,2,1).setFill(GBC.BOTH).setIpad(200, 30).setWeight(100, 0));
		for(int i = 0; i < choisesNumber; ++ i){
			//System.out.println(answers[i].getText());
			jrbs[i] = new JRadioButton("" + i + ": " + choises.get(i));
			remarks[i] = new JTextField();
			bg.add(jrbs[i]);
			if(choises.get(i).endsWith("#")){
				this.add(jrbs[i], new GBC(0,i+1,1,1).setFill(GBC.BOTH).setIpad(100, 30).setWeight(100, 0));
				this.add(remarks[i], new GBC(1,i+1,1,1).setFill(GBC.BOTH).setIpad(100, 30).setWeight(100, 0));
			} else {
				this.add(jrbs[i], new GBC(0,i+1,2,1).setFill(GBC.BOTH).setIpad(100, 30).setWeight(100, 0));
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
		for(int i = 0; i < choisesNumber; ++ i){
			if(jrbs[i].isSelected()){
				this.setAnswer("" + i + "_" + remarks[i].getText());
			}
		}
		return true;
	}
}

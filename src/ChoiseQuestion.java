import java.awt.Color;
import java.awt.Font;
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
	
	public ChoiseQuestion(int ID, String quesDescribe) {
		super(ID, quesDescribe);
		this.quesType = 2;
		//setupUI();
	}
	public ChoiseQuestion(int ID, String quesDescribe, String choisesString){
		this(ID, quesDescribe);		
		if(choisesString.split(",").length > 1)
			for(String s : choisesString.split(",")){
				choises.add(s);
			}
		this.choisesNumber = choises.size();
		
		jrbs = new JRadioButton[choisesNumber];
		remarks = new JTextField[choisesNumber];
		setupUI();
	}
	public ChoiseQuestion(int ID, String quesDescribe, String choisesString, String jumpsString){
		this(ID, quesDescribe, choisesString);
		if(jumpsString.split(",").length > 1) {
			for(String s : jumpsString.split(",")) {
				jumps.add(s);
			}
			jumpornot = true;
		}
	}
	public void setupUI(){
		this.setLayout(new GridBagLayout());
		//this.setBackground(Color.RED);
		Font font = new Font("ËÎÌו",Font.PLAIN,20);
		if(quesDescribe.split("_").length == 2)
			jl = new JLabel(" " + (ID+1) +". " + quesDescribe.split("_")[1], JLabel.LEFT);
		else
			jl = new JLabel(" " + (ID+1) +". " + quesDescribe, JLabel.LEFT);
		jl.setFont(font);
		this.add(jl, new GBC(0,0,2,1).setWeight(0, 0).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		for(int i = 0; i < choisesNumber; ++ i){
			//System.out.println(answers[i].getText());
			jrbs[i] = new JRadioButton("" + ((char)('A' + i)) + ": " + choises.get(i));
			jrbs[i].setFont(font);
			remarks[i] = new JTextField();
			bg.add(jrbs[i]);
			if(choises.get(i).endsWith("#")){
				this.add(jrbs[i], new GBC(0,i+1,1,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeight(0, 0));
				this.add(remarks[i], new GBC(1,i+1,1,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setIpad(100, 0).setWeight(0, 0));
			} else {
				this.add(jrbs[i], new GBC(0,i+1,2,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeight(0, 0));
			}
		}
		this.setVisible(true);
	}
	public boolean checkAnswer(){
		for(int i = 0; i < choisesNumber; ++ i){
			if(jrbs[i].isSelected() && choises.get(i).endsWith("#") && remarks[i].getText().length() == 0) return false;
		}
		for(int i = 0; i < choisesNumber; ++ i){
			if(jrbs[i].isSelected()) {
				return true;
			}
		}
		return false;
	}
	public int getJump(){
		if(!jumpornot) return -1;
		for(int i = 0; i < choisesNumber; ++ i){
			if(jrbs[i].isSelected()){
				return Integer.parseInt(jumps.get(i));
			}
		}
		return -1;
	}
	public boolean submit(){
		if(!checkAnswer()) return false;
		for(int i = 0; i < choisesNumber; ++ i){
			if(jrbs[i].isSelected()){
				if(choises.get(i).endsWith("#"))
					this.setAnswer("" + jrbs[i].getText() + "_" + remarks[i].getText());
				else
					this.setAnswer("" + jrbs[i].getText());
			}
		}
		return true;
	}
}

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;
public class SetQuestion extends Question{
	JComboBox jcb;
	public SetQuestion(int ID, String quesDescribe) {
		super(ID, quesDescribe);
		this.quesType = 4;
		setupUI();
	}
	public SetQuestion(int ID, String quesDescribe, String setString) {
		super(ID, quesDescribe);
		this.quesType = 4;
		if(setString.split(",").length > 1)
			for(String s : setString.split(",")){
				answerSet.add(s);
			}
		jcb = new JComboBox(answerSet.toArray());
		jcb.setAutoscrolls(true);
		setupUI();
	}
	
	public void setupUI(){
		Font font = new Font("ËÎÌו",Font.PLAIN,20);
		if(quesDescribe.split("_").length == 2)
			jl = new JLabel(" " + (ID+1) +". " + quesDescribe.split("_")[1], JLabel.LEFT);
		else
			jl = new JLabel(" " + (ID+1) +". " + quesDescribe, JLabel.LEFT);
		jl.setFont(font);
		
		this.setLayout(new GridLayout(1, 2));
		this.add(jl);
		this.add(jcb);
		
		this.setVisible(true);
	}
	public void setAnswerSet(String answerSet){
		
	}
	public boolean submit(){
		this.setAnswer(answerSet.get(jcb.getSelectedIndex()));
		return true;
	}
	public void getFocus(){
		System.out.println("Focus");
		this.jcb.requestFocus(true);
	}
	public void clear(){
		this.setAnswer("");
	}
	public boolean checkAnswer(String tempAnswer){
		return true;
	}
}

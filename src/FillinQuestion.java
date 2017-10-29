import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
public class FillinQuestion extends Question{
	
	public FillinQuestion(int ID, String quesDescribe, String ans) {
		super(ID, quesDescribe, ans);
		this.quesType = 1;
		setupUI();
	}
	public FillinQuestion(int ID, String quesDescribe, String ans, String setString, String minRange, String maxRange) {
		this(ID, quesDescribe, ans);
		if(setString.split(",").length > 1)
			for(String s : setString.split(",")){
				answerSet.add(s);
			}
		if(isNumeric(minRange)){
			minNumber = Double.parseDouble(minRange);
		}
		if(isNumeric(maxRange)){
			maxNumber = Double.parseDouble(maxRange);
		}
	}
	public void setupUI(){
		Font font = new Font("ËÎÌו",Font.PLAIN,20);
		if(quesDescribe.split("_").length == 2)
			jl = new JLabel(" " + (ID+1) +". " + quesDescribe.split("_")[1], JLabel.LEFT);
		else
			jl = new JLabel(" " + (ID+1) +". " + quesDescribe, JLabel.LEFT);
		jl.setFont(font);
		jtf = new JTextField();
		jtf.setText(answer);
		this.setLayout(new GridLayout(1, 2));
		this.add(jl);
		this.add(jtf);
		
		this.setVisible(true);
	}
	public void setAnswerSet(String answerSet){
		
	}
	public boolean submit(){
		String tempAnswer = jtf.getText().trim();
		if(checkAnswer(tempAnswer)){
			this.setAnswer(tempAnswer);
			return true;
		} else {
			return false;
		}
	}
	public void getFocus(){
		System.out.println("Focus");
		this.jtf.requestFocus(true);
	}
	public void clear(){
		jtf.setText("");
		this.setAnswer("");
	}
	public boolean isNumeric(String str){ 
		//Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
		    return false; 
		} 
		return true; 
	}
	public boolean checkAnswer(String tempAnswer){
		if(answerSet.size() != 0){
			if(answerSet.contains(tempAnswer))
				return true;
			else 
				return false;
		} else if(minNumber != maxNumber){
			if(isNumeric(tempAnswer) && Double.parseDouble(tempAnswer) >= minNumber && Double.parseDouble(tempAnswer) <= maxNumber)
				return true;
			else
				return false;
		} else {
			return true;
		}
	}
}

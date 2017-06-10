import java.util.ArrayList;

import javax.swing.*;

public class Question extends JPanel{
	public String quesDescribe;
	public String answer = "", statement = "";
	public int quesType = 0;
	public int ID;
	ArrayList<String> answerSet = new ArrayList<String>();
	ArrayList<String> choises = new ArrayList<String>();
	double minNumber = 0, maxNumber = 0;
	public Question(int ID, String quesDescribe){
		this.ID = ID;
		if(quesDescribe != null)
			this.quesDescribe = quesDescribe;
		else
			this.quesDescribe = "";
	}
	public void setAnswer(String ans){
		this.answer = ans;
	}
	public String getQuesDescribe(){
		return quesDescribe;
	}
	public String getAnswer(){
		return answer;
	}
	public String getStatement(){
		return statement;
	}
	public int getType(){
		return quesType;
	}
	public String getAnswerSet(){
		String set = String.join(",", answerSet);
		return set;
	}
	public String getMinRange(){
		return "" + minNumber;
	}
	public String getMaxRange(){
		return "" + maxNumber;
	}
	public int getID(){
		return ID;
	}
	public String getChoises(){
		String set = String.join(",", choises);
		return set;
	}
	public void getFocus(){
		
	}
	public boolean submit(){
		return true;
	}
	public void clear(){
		
	}
}

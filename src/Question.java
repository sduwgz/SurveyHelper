import java.util.ArrayList;

import javax.swing.*;

public class Question extends JPanel{
	public String quesDescribe;
	public String answer = "", statement = "";
	public int quesType = 0;
	public boolean jumpornot = false;
	public int ID;
	public int ref = -1;
	public JLabel jl;
	public JTextField jtf;
	public ButtonGroup bg = new ButtonGroup();
	public JRadioButton[] jrbs;
	public JCheckBox[] jcbs;
	public JTextField[] remarks;
	ArrayList<String> answerSet = new ArrayList<String>();
	ArrayList<String> choises = new ArrayList<String>();
	ArrayList<String> jumps = new ArrayList<String>();
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
	public int getREF(){
		return ref;
	}
	public String getChoises(){
		String set = String.join(",", choises);
		return set;
	}
	public String getJumps(){
		String set = String.join(",", jumps);
		return set;
	}
	public void getFocus(){
		
	}
	public boolean submit(){
		return true;
	}
	public int getJump(){
		return 0;
	}
	public void clear(){
		
	}
}

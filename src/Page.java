import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class Page extends JPanel{
	int pageID = 0;
	int curQuestion = 0;
	int nextPage = 0;
	int prePage = 0;
	boolean finished = false;
	String refQues = "";
	JLabel pageNumber = new JLabel();
	JLabel title = new JLabel();
	JTextArea describe = new JTextArea();
	JLabel remark = new JLabel("备注");
	JTextField remarkContent = new JTextField();
	String remarkString = "";
	JPanel remarkPanel = new JPanel();
	JPanel quesPanel = new JPanel();
	ArrayList<Question> questionList;
	public Page(){
		questionList = new ArrayList<Question>();
		//setupUI();
	}
	public Page(ArrayList<Question> questionList){
		this.questionList = questionList;
		title = new JLabel("init", JLabel.CENTER);
		pageNumber = new JLabel();
		setupUI();
		setFocusListener();
	}
	public Page(ArrayList<Question> questionList, int n){
		this(questionList);
		pageID = n;
		nextPage = n + 1;
		prePage = n - 1;
		pageNumber.setText("第 " + pageID + " 页");
		pageNumber.setHorizontalAlignment(JTextField.CENTER);
	}
	public Page(ArrayList<Question> questionList, int n, String s, String t){
		this(questionList, n);
		pageID = n;
		nextPage = n + 1;
		prePage = n - 1;
		title.setText(s);
		describe.setText(t);
		describe.setLineWrap(true);
		describe.setEditable(false);
	}
	public Page(ArrayList<Question> questionList, int n, String s, String t, String pr, String ne, String r){
		this(questionList, n, s, t);
		
		prePage = Integer.parseInt(pr);
		nextPage = Integer.parseInt(ne);
		refQues = r;
	}
	public void setupUI(){
		this.setLayout(new GridBagLayout());
		//this.setSize(200, 500);
		Font font = new Font("宋体",Font.PLAIN,30);
		this.add(title, new GBC(0,0,1,1).setFill(GBC.BOTH).setWeight(0, 0).setIpad(50, 50));
		title.setFont(font);
		font = new Font("宋体",Font.PLAIN,24);
		describe.setFont(font);
		font = new Font("宋体",Font.PLAIN,22);
		remark.setFont(font);
		font = new Font("宋体",Font.PLAIN,20);
		pageNumber.setFont(font);
		//describe.setBackground(Color.RED);
		this.add(describe, new GBC(0,1,1,1).setFill(GBC.BOTH).setWeight(0, 0));
		int size = questionList.size();
		quesPanel.setLayout(new GridBagLayout());
		for(int i = 0; i < size; ++ i){
			int qtype = questionList.get(i).getType();
			if(i % 2 == 1 && (qtype == 1 || qtype == 4)) questionList.get(i).setBackground(Color.LIGHT_GRAY);
			quesPanel.add(questionList.get(i), new GBC(0,i,1,1).setFill(GBC.HORIZONTAL).setWeight(0.1, 0).setAnchor(GBC.WEST));
		}
		remarkPanel.setLayout(new GridBagLayout());
		remarkPanel.add(remark, new GBC(0,0,1,1).setFill(GBC.BOTH).setWeight(0.1, 0));
		remarkPanel.add(remarkContent, new GBC(1,0,6,1).setFill(GBC.BOTH).setWeight(1, 0));
		//quesPanel.setBackground(Color.YELLOW);
		this.add(quesPanel, new GBC(0,2,1,6).setFill(GBC.BOTH).setIpad(50, 50).setWeight(0.1, 0));
		this.add(remarkPanel, new GBC(0,12,1,1).setFill(GBC.BOTH).setWeight(0.1, 0));
		this.add(pageNumber, new GBC(0,13,1,1).setFill(GBC.BOTH).setWeight(0, 0));
		this.setVisible(true);
	}
	public void setFocusListener(){
		int size = questionList.size();
		for(int i = 0; i < size; ++ i){
			if(questionList.get(i).getType() == 1){
				questionList.get(i).jtf.addFocusListener(new txtFocus());
			}
		}
		
	}
	
	class txtFocus implements FocusListener{
        public void focusGained(FocusEvent e)
        {
        	int size = questionList.size();
    		for(int i = 0; i < size; ++ i)
    			if(e.getSource() == questionList.get(i).jtf){
    				curQuestion = i;
    				System.out.println("CurQuestion: " + curQuestion);
    			}
        }
        public void focusLost(FocusEvent e)
        {
        	int size = questionList.size();
        	for(int i = 0; i < size; ++ i)
    			if(e.getSource() == questionList.get(i).jtf) {
    				System.out.println("CurQuestion: " + curQuestion);
    			}
        }
    }
	public String getRef(){
		return refQues;
	}
	public boolean submit(){
		int size = questionList.size();
		for(int i = 0; i < size; ++ i) {
			if(!questionList.get(i).submit()) {
				String errorInfo = "";
				Question q = questionList.get(i);
				if(q.getType() == 1) {
					if(q.getMinRange() != q.getMaxRange()) {
						errorInfo = "答案为数字，且范围为" + q.getMinRange() + "~" + q.getMaxRange();
					}
					else if(q.getAnswerSet() != "") {
						errorInfo = "答案必须为下面集合中的一个： \n" + q.getAnswerSet();
					}
				} else if(q.getType() == 2) {
					errorInfo = "必须选择一项，如果选择需要注明的选项必须填写选项后的说明框";
				} else if(q.getType() == 3) {
					errorInfo = "至少选择一项，如果选择需要注明的选项必须填写选项后的说明框";
				}
				JOptionPane.showConfirmDialog(this, errorInfo, "请检查第" + (questionList.get(i).getID() + 1) +"题", JOptionPane.OK_CANCEL_OPTION);
				return false;
			}
		}
		for(int i = 0; i < size; ++ i) {
			if(questionList.get(i).getType() == 2 && questionList.get(i).jumpornot) {
				this.nextPage = questionList.get(i).getJump();
			}
		}
		this.setRemark();
		this.finished = true;
		return true;
	}
	public void addQuestion(Question newQuestion){
		questionList.add(newQuestion);
		int size = questionList.size();
		this.quesPanel.add(newQuestion, new GBC(0,size - 1,1,1).setFill(GBC.BOTH).setIpad(200, 20).setWeight(0.1, 0));
		this.quesPanel.revalidate();
		this.repaint();
	}
	public void clear(){
		int size = questionList.size();
		this.remarkContent.setText("");
		for(int i = 0; i < size; ++ i){
			questionList.get(i).clear();
		}
	}
	public String getRemark(){
		return remarkString;
	}
	private void setRemark(){
		this.remarkString = remarkContent.getText();
	}
	public boolean isFinished(){
		return finished;
	}
	
}

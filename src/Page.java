import java.awt.BorderLayout;
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
		this.setSize(200, 500);
		Font font = new Font("宋体",Font.PLAIN,36);
		this.add(title, new GBC(0,0,1,1).setFill(GBC.BOTH).setWeight(0.1, 0));
		title.setFont(font);
		font = new Font("宋体",Font.PLAIN,24);
		describe.setFont(font);
		this.add(describe, new GBC(0,1,1,1).setFill(GBC.BOTH).setWeight(0.1, 0));
		int size = questionList.size();
		quesPanel.setLayout(new GridBagLayout());
		for(int i = 0; i < size; ++ i){
			quesPanel.add(questionList.get(i), new GBC(0,i,1,1).setFill(GBC.BOTH).setIpad(200, 20).setWeight(0.1, 0));
		}
		remarkPanel.setLayout(new GridLayout(2, 1));
		remarkPanel.add(remark);
		remarkPanel.add(remarkContent);
		this.add(quesPanel, new GBC(0,2,1,10).setFill(GBC.BOTH).setIpad(200, 400).setWeight(1, 0));
		this.add(remarkPanel, new GBC(0,12,1,1).setFill(GBC.BOTH).setWeight(0.1, 0));
		this.add(pageNumber, new GBC(0,13,1,1).setFill(GBC.BOTH).setWeight(0.1, 0));
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
				JOptionPane.showConfirmDialog(this, "请检查第" + (questionList.get(i).getID() + 1) +"题", "填写不规范提示", JOptionPane.OK_CANCEL_OPTION);
				return false;
			}
		}
		for(int i = 0; i < size; ++ i) {
			if(questionList.get(i).getType() == 2 && questionList.get(i).jumpornot) {
				this.nextPage = questionList.get(i).getJump();
			}
		}
		this.setRemark();
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
	
}

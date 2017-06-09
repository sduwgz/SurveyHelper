import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;

public class Page extends JPanel{
	int pageID = 0;
	JLabel pageNumber = new JLabel();
	JLabel title = new JLabel();
	JLabel describe = new JLabel();
	JLabel remark = new JLabel("备注");
	JTextField remarkContent = new JTextField();
	String remarkString = "";
	JPanel remarkPanel = new JPanel();
	JPanel quesPanel = new JPanel();
	ArrayList<Question> questionList;
	public Page(){
		questionList = new ArrayList<Question>();
		setupUI();
	}
	public Page(ArrayList<Question> questionList){
		this.questionList = questionList;
		title = new JLabel();
		pageNumber = new JLabel();
		setupUI();
	}
	public Page(ArrayList<Question> questionList, int n){
		this(questionList);
		pageID = n;
		pageNumber.setText("第 " + pageID + " 页");
		pageNumber.setHorizontalAlignment(JTextField.CENTER);
	}
	public Page(ArrayList<Question> questionList, int n, String s, String t){
		this(questionList, n);
		title.setText(s);
		describe.setText(t);
	}
	public void setupUI(){
		this.setLayout(new GridBagLayout());
		this.setSize(200, 500);
		this.add(title, new GBC(0,0,1,1).setFill(GBC.BOTH).setIpad(200, 20).setWeight(1, 0));
		this.add(describe, new GBC(0,1,1,1).setFill(GBC.BOTH).setIpad(200, 20).setWeight(1, 0));
		int size = questionList.size();
		quesPanel.setLayout(new GridBagLayout());
		for(int i = 0; i < size; ++ i){
			quesPanel.add(questionList.get(i), new GBC(0,i,1,1).setFill(GBC.BOTH).setIpad(200, 20).setWeight(100, 0));
		}
		remarkPanel.setLayout(new GridLayout(2, 1));
		remarkPanel.add(remark);
		remarkPanel.add(remarkContent);
		this.add(quesPanel, new GBC(0,2,1,1).setFill(GBC.BOTH).setIpad(200, 200).setWeight(100, 0));
		this.add(remarkPanel, new GBC(0,3,1,1).setFill(GBC.BOTH).setIpad(200, 20).setWeight(100, 0));
		this.add(pageNumber, new GBC(0,4,1,1).setFill(GBC.BOTH).setIpad(200, 20).setWeight(100, 0));
		this.setVisible(true);
	}
	public void submit(){
		int size = questionList.size();
		for(int i = 0; i < size; ++ i)
			if(!questionList.get(i).submit()){
				JOptionPane.showConfirmDialog(this, "请检查第" + (i + 1) +"题", "填写不规范提示", JOptionPane.OK_CANCEL_OPTION);
			}
		this.setRemark();
	}
	public void addQuestion(Question newQuestion){
		questionList.add(newQuestion);
		int size = questionList.size();
		this.quesPanel.add(newQuestion, new GBC(0,size - 1,1,1).setFill(GBC.BOTH).setIpad(200, 20).setWeight(100, 0));
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

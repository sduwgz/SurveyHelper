import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AnswerSheet extends JFrame{
	int curPage = 0;
	int curGroup = 0;
	JButton nextPage = new JButton("��һҳ");
	JButton prePage = new JButton("��һҳ");
	JButton confirm = new JButton("ȷ��");
	JPanel pagePanel = new JPanel();
	JPanel groupPanel = new JPanel();
	JPanel refPanel = new JPanel();
	JPanel controlPanel = new JPanel();
	CardLayout card = new CardLayout();
	ArrayList<Page> pageList;
	ArrayList<Group> groupList;
	Page startPage = new Page();
	Page endPage = new Page();
	JButton startButton, endButton;
	ExcelWriter writer;
	public AnswerSheet(ArrayList<Page> pageList, ArrayList<Group> groupList) throws IOException {
		this.pageList = pageList;
		this.groupList = groupList;
		initWriter();
		setupUI();
		setupListener();
	}
	public AnswerSheet(String fileName) {
		XMLOperator xmlo = new XMLOperator();
		try {
			AnswerSheet as = xmlo.reader(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showConfirmDialog(this, "�ʾ��ļ����Ϸ�", "�ļ�������ʾ", JOptionPane.OK_CANCEL_OPTION);
			e.printStackTrace();
		}
	}
	public AnswerSheet(){
		JFileChooser jfc=new JFileChooser();
		jfc.setCurrentDirectory(new File("d:/"));
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int value = jfc.showDialog(new JLabel(), "ѡ��");
		if(value == JFileChooser.APPROVE_OPTION) {
			File file=jfc.getSelectedFile();
			if(file.getAbsolutePath().endsWith(".xml")) {
				try {
					AnswerSheet as = new AnswerSheet("" + file.getAbsolutePath());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("�ļ�:"+file.getAbsolutePath());
				JOptionPane.showConfirmDialog(this, "�ļ� "+ file.getAbsolutePath() + " �����ʾ�", "�ļ�������ʾ", JOptionPane.OK_CANCEL_OPTION);
			}
		}
		
	}
	public void setupUI(){
		this.setSize(1000, 800);
		initStartPage();
		initEndPage();
		this.setTitle("�����ʾ�");
		pagePanel.setLayout(card);
		controlPanel.setLayout(new GridLayout(1, 3));
		pagePanel.add(startPage);
		int psize = pageList.size();
		for(int i = 0; i < psize; ++ i){
			pagePanel.add(pageList.get(i));
		}
		pagePanel.add(endPage);
		
		groupPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		groupPanel.setLayout(new GridLayout(1, groupList.size()));
		int gsize = groupList.size();
		for(int i = 0; i < gsize; ++ i){
			groupList.get(i).setOpaque(true);
			groupList.get(i).setBorder(BorderFactory.createLineBorder(Color.GRAY));
			groupPanel.add(groupList.get(i));
		}
		
			
		controlPanel.add(prePage);
		controlPanel.add(confirm);
		controlPanel.add(nextPage);
		
		this.setLayout(new GridBagLayout());
		this.add(groupPanel, new GBC(0, 0, 10, 1).setFill(GBC.BOTH).setWeight(0.1, 0.1));
		this.add(refPanel, new GBC(0, 1, 10, 2).setFill(GBC.BOTH).setWeight(0.1, 0.1));
		this.add(pagePanel, new GBC(0, 3, 10, 9).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 200, 0, 200));
		this.add(controlPanel, new GBC(0, 12, 10, 1).setFill(GBC.BOTH).setWeight(0.1, 0.1));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		 
		this.setVisible(true);
	}
	public void setupListener(){
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int size = pageList.size();
				curPage = 1;
				pageList.get(curPage - 1).questionList.get(0).getFocus();
				for(int i = 0; i < groupList.size(); ++ i){
					if(curPage > groupList.get(i).endPage)
						groupList.get(i).done();
					if(curPage >= groupList.get(i).startPage && curPage <= groupList.get(i).endPage)
						groupList.get(i).active();
					if(curPage < groupList.get(i).startPage)
						groupList.get(i).unreach();
				}
				if(curPage <= size - 1){
					card.next(pagePanel);
				}
				System.out.println(curPage);
			}
		});
		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					submitAll();
				} catch (IOException e1) {
					// TODO Auto-generated catch block(done)
					e1.printStackTrace();
				}
				clearAll();
				restart();
			}
		});
		nextPage.addActionListener(new ActionListener() {
			@Override
			//TODO: the groupList will out of index. 
			public void actionPerformed(ActionEvent e) {
				int size = pageList.size();
				if(curPage <= size){
					card.next(pagePanel);
					
					curPage ++;
					setRefPanel();
					for(int i = 0; i < groupList.size(); ++ i){
						if(curPage - 1 == groupList.get(i).endPage){
							//continue;
							ViewPage vp = new ViewPage(pageList, groupList.get(i));
						}
						if(curPage > groupList.get(i).endPage)
							groupList.get(i).done();
						if(curPage >= groupList.get(i).startPage && curPage <= groupList.get(i).endPage)
							groupList.get(i).active();
						if(curPage < groupList.get(i).startPage)
							groupList.get(i).unreach();
					}
				} else {
					curPage = 0;
					groupList.get(groupList.size() - 1).done();
					card.next(pagePanel);
				}
				if(curPage <= size)
					pageList.get(curPage - 1).questionList.get(0).getFocus();
				System.out.println(curPage);
				
			}
		});
		prePage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(curPage > 0){
					card.previous(pagePanel);
					setRefPanel();
					curPage --;
					
					for(int i = 0; i < groupList.size(); ++ i){
						if(curPage > groupList.get(i).endPage)
							groupList.get(i).done();
						if(curPage >= groupList.get(i).startPage && curPage <= groupList.get(i).endPage)
							groupList.get(i).active();
						if(curPage < groupList.get(i).startPage)
							groupList.get(i).unreach();
					}
				}
				System.out.println(curPage);
			}
		});
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pageList.get(curPage - 1).submit();
			}
		});
	}
	public void initStartPage() {
		startPage.setLayout(new GridBagLayout());
		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridBagLayout());
		jp1.setBackground(Color.PINK);
		JLabel statement = new JLabel();
		String s = "���ʾ�����" + groupList.size() + "������,����Ҫ����������¼�������:";
		statement.setText(s);
		jp1.add(statement, new GBC(0, 0, 5, 1).setInsets(20, 30, 0, 0).setWeight(0.1, 0.1));
		for(int i = 0; i < groupList.size(); ++ i){
			JLabel g = new JLabel(groupList.get(i).getName());
			jp1.add(g, new GBC(1, i + 1, 4, 1).setInsets(20, 50, 0, 0).setWeight(0.1, 0.1));
		}
		JLabel login = new JLabel("������ı�ţ�");
		JTextField loginJTF = new JTextField();
		loginJTF.requestFocus();
		startButton = new JButton("��ʼ����");
		startButton.setBackground(Color.darkGray);
		startPage.add(jp1, new GBC(0, 0, 10, 10).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 0, 0, 0));
		startPage.add(login, new GBC(0, 10, 5, 1).setWeight(0.1, 0.1).setInsets(10, 50, 0, 0));
		startPage.add(loginJTF, new GBC(5, 10, 5, 1).setAnchor(GBC.WEST).setFill(GBC.BOTH).setWeight(1, 0.1).setInsets(10, 0, 0, 100));
		startPage.add(startButton, new GBC(0, 11, 10, 1).setFill(GBC.BOTH).setWeight(1, 0.1).setInsets(10, 100, 0, 150));
	}
	public void initEndPage(){
		endPage.setLayout(new BorderLayout());
		endButton = new JButton("���");
		
		endPage.add(endButton, new BorderLayout().CENTER);
	}
	public void setRefPanel(){
		refPanel.removeAll();
		refPanel.setLayout(new GridBagLayout());
		JLabel info = new JLabel("�ο���Ŀ");
		if(curPage > pageList.size()) return;
		ArrayList<Question> ql = pageList.get(curPage - 1).questionList;
		int pos = 1;
		for(int i = 0; i < ql.size(); ++ i){
			if(ql.get(i).getREF() != -1){
				refPanel.add(info, new GBC(0,0,1,1).setFill(GBC.BOTH).setWeight(0.1, 0).setInsets(0, 200, 0, 200));
				break;
			}
		}
		for(int i = 0; i < ql.size(); ++ i){
			for(int j = 0; j < curPage - 1; ++ j){
				for(int k = 0; k < pageList.get(j).questionList.size(); ++ k){
					if(ql.get(i).getREF() == pageList.get(j).questionList.get(k).getID()){
						JLabel que = new JLabel(pageList.get(j).questionList.get(k).getID()  +". " + pageList.get(j).questionList.get(k).getQuesDescribe());
						JLabel ans = new JLabel(pageList.get(j).questionList.get(k).getAnswer());
						
						refPanel.add(que, new GBC(0,pos++,1,1).setFill(GBC.BOTH).setAnchor(GBC.WEST).setInsets(10, 100, 0, 0).setWeight(0.1, 0));
						refPanel.add(ans, new GBC(0,pos++,1,1).setFill(GBC.BOTH).setAnchor(GBC.WEST).setInsets(10, 100, 0, 0).setWeight(0.1, 0));
					}
				}
			}
		}
		refPanel.updateUI();
	}
	public void initWriter() {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//�������ڸ�ʽ
			String date = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
			String outFileName = "output_" + date + ".xls";
			writer = new ExcelWriter(outFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> table = new ArrayList<String>();
		for(Page page : pageList){
			for(Question q : page.questionList){
				table.add(q.getQuesDescribe());
			}
		}
		//A bad transform from ArrayList to String[]
		int tableSize = table.size();
		try {
			writer.newTable((String[])table.toArray(new String[tableSize]));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addPage(Page newPage){
		pageList.add(newPage);
		this.pagePanel.add(newPage);
		this.repaint();
	}
	public void clearAll(){
		int size = pageList.size();
		for(int i = 0; i < size; ++ i){
			pageList.get(i).clear();
		}
	}
	public void submitAll() throws IOException{
		ArrayList<String> answerList = new ArrayList<String>();
		ArrayList<String> remarkList = new ArrayList<String>();
		int size = pageList.size();
		for(int i = 0; i < size; ++ i){
			int quesNumber = pageList.get(i).questionList.size();
			remarkList.add(pageList.get(i).getRemark());
				for(int j = 0; j < quesNumber; ++ j){
					answerList.add(pageList.get(i).questionList.get(j).getAnswer());
				}
		}
		int shellSize = answerList.size();
		String[] row = new String[shellSize];
		for(int i = 0; i < shellSize; ++ i){
			row[i] = answerList.get(i);
		}
		writer.writeRows(row);
		int remarkSize = remarkList.size();
		for(int i = 0; i < remarkSize; ++ i){
			System.out.println(remarkList.get(i));
		}
		curPage = 0;
	}
	public void restart(){
		card.first(pagePanel);
	}
	public static void main(String[] args) throws Exception{
		AnswerSheet as = new AnswerSheet();
	}
}
